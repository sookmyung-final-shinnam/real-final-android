package com.veryshinnam.myapp.feature.permit.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.KakaoLoginWebView
import com.veryshinnam.myapp.common.component.LogoText

// 로그인 스크린

/**
 * 카카오 웹뷰로 로그인 진행
 * onSignup: 신규 유저 > 회원가입 스크린으로 이동
 * onHome: 기존 유저 > 홈 스크린으로 이동
 */
@Composable
fun LoginScreen(
    onSignup: (String) -> Unit,
    onHome: () -> Unit,
    vm: PermitViewModel = hiltViewModel()
) {
    val state by vm.permitUiState.collectAsStateWithLifecycle()
    var isKakaoLogin by remember { mutableStateOf(false) } // 카카오 로그인 웹뷰 띄우기

    LaunchedEffect(state) {
        when (state) {
            is PermitUiState.Success -> onHome()
            is PermitUiState.Error -> {
                isKakaoLogin = false // 로그인 재시도 유도
            }
            else -> Unit
        }
    }

    // 카카오 웹뷰 UI
    if (isKakaoLogin) { // 로그인 버튼 클릭 시 활성화
        BackHandler { isKakaoLogin = false } // 뒤로가기

        KakaoLoginWebView(
            modifier = Modifier.fillMaxSize(),
            onTempCodeReceived = { tempCode, isNewUser ->
                if (isNewUser) {
                    onSignup(tempCode) // 신규 유저 > 회원가입
                } else {
                    vm.login(tempCode) // 기존 유저 > 로그인
                }
            }
        )
    } else { 
        // 웹뷰 이전 로그인 UI
        Column(
            modifier = Modifier.fillMaxSize()
                .background(colorResource(R.color.background_yellow)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1.5f))

            // 로고
            LogoText(modifier = Modifier.weight(1.5f))
            Spacer(modifier = Modifier.weight(.5f))

            // 이미지
            Box(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.img_splash),
                    contentDescription = "스플래시 이미지",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Inside
                )
            }
            Spacer(modifier = Modifier.weight(.5f))

            // 로그인 버튼
            // TODO: 크게
            Box(
                modifier = Modifier
                    .clickable(
                        indication = null,  // ripple 제거로 충돌 방지
                        interactionSource = remember { MutableInteractionSource() }
                    ) { isKakaoLogin = true } // 로그인 버튼 클릭시 활성화
                    .weight(.5f)
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.kakao_yellow)),
                contentAlignment = Alignment.Center // 중앙 정렬
            ) {
                Image(
                    painter = painterResource(R.drawable.img_kakao_login),
                    contentDescription = "카카오 로그인",
                    modifier = Modifier.wrapContentSize(),
                    contentScale = ContentScale.None
                )
            }
            
            Spacer(modifier = Modifier.weight(1.5f))
        }
    }
}