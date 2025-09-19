package com.veryshinnam.myapp.feature.permit.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.KakaoLoginWebView
import com.veryshinnam.myapp.common.component.StrokeText

@Composable
fun LoginScreen(
    onSignup: () -> Unit,
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

    if (isKakaoLogin) { // 카카오 로그인 버튼 누르면 웹뷰
        BackHandler { isKakaoLogin = false } // 뒤로가기

        KakaoLoginWebView(
            onTempCodeReceived = { tempCode, isNewUser ->
                if (isNewUser) {
                    onSignup() // 신규 유저 > 회원가입
                } else {
                    vm.login(tempCode) // 기존 유저 > 로그인
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(colorResource(R.color.background_yellow)),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(2f))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 문구 텍스트
                Text(
                    text = "대화하며 나만의 캐릭터 만들기",
                    color = colorResource(R.color.brand_orange),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                // 로고
                StrokeText(
                    text = "Storictor",
                    tColor = colorResource(R.color.brand_orange),
                    oColor = Color.White,
                    oWidth = 8f,
                    fStyle = MaterialTheme.typography.displayLarge,
                    fWeight = FontWeight.Bold,
                    modifier = Modifier
                )
            }

            // 이미지
            Image(
                painter = painterResource(R.drawable.img_splash),
                contentDescription = "스플래시 이미지",
                modifier = Modifier
                    .weight(7f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            // 로그인 버튼
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center // 중앙 정렬
            ) {
                Image(
                    painter = painterResource(R.drawable.img_kakao_login),
                    contentDescription = "카카오 로그인",
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable { isKakaoLogin = true },
                    contentScale = ContentScale.None
                )
            }
            Spacer(modifier = Modifier.weight(2f))
        }
    }
}