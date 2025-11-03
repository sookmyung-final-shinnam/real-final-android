package com.veryshinnam.myapp.feature.permit.ui

import android.content.pm.ActivityInfo
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoTitle
import com.veryshinnam.myapp.common.component.ScreenOrientation

/**
 * 로그인 화면
 * : 로그인 초기 화면으로, 하단 카카오 로그인 버튼 누르면 카카오 로그인(LoginKakaoContent) 진행
 *
 * - onHome: 기존 유저 > 홈 화면(HomeScreen)으로 이동
 * - onSignup: 신규 유저 > 회원가입 화면(SignUpScreen)으로 이동
 */
@Composable
fun LoginScreen(
    onHome: () -> Unit,
    onSignup: (String) -> Unit, // 로그인 임시 코드 전달
    vm: PermitViewModel = hiltViewModel()
) {
    val state by vm.permitUiState.collectAsStateWithLifecycle()
    var isKakaoLogin by remember { mutableStateOf(false) } // 카카오 로그인 웹뷰 띄우기

    // 세로 모드
    ScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    LaunchedEffect(state) {
        when (state) {
            is PermitUiState.Success -> onHome()
            is PermitUiState.Error -> {
                isKakaoLogin = false // 로그인 재시도 유도
            }
            else -> Unit
        }
    }

    // 카카오 로그인 UI
    if (isKakaoLogin) { // 로그인 버튼 클릭 시 활성화
        BackHandler { isKakaoLogin = false } // 뒤로가기

        LoginKakaoContent(
            modifier = Modifier.fillMaxSize(),
            onTempCodeReceived = { tempCode, isNewUser, isAgreedToTerms ->
                // 신규 유저 + 약관 미동의
                if (isNewUser || !isAgreedToTerms) {
                    onSignup(tempCode)
                } else {
                    // 약관 동의한 기존 유저
                    vm.login(tempCode)
                }
            }
        )
    } else {
        // 카카오 로그인 진행 이전 UI
        Column(
            modifier = Modifier.fillMaxSize()
                .background(colorResource(R.color.background_yellow)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1.5f))

            // 로고
            LogoTitle(modifier = Modifier.weight(1.5f))
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
                    .fillMaxWidth(0.8f)
                    .background(color = colorResource(R.color.kakao_yellow)),
                contentAlignment = Alignment.Center // 중앙 정렬
            ) {
                Image(
                    painter = painterResource(R.drawable.img_kakao_login),
                    contentDescription = "카카오 로그인",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.weight(1.5f))
        }
    }
}