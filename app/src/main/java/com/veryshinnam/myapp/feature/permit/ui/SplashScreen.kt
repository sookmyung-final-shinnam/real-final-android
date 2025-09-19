package com.veryshinnam.myapp.feature.permit.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.veryshinnam.myapp.common.component.StrokeText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onHome: () -> Unit,
    onLogin: () -> Unit,
    vm: PermitViewModel = hiltViewModel()
) {
    val state by vm.permitUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        val splashJob = launch { delay(3000) } // 3초 스플래시
        val tokenJob = launch { vm.checkAccessToken() }  // 동시 토큰 검사

        splashJob.join()
        tokenJob.join()
    }

    LaunchedEffect(state) {
        when (state) {
            is PermitUiState.Success -> {
                onHome()
                vm.resetState()
            }
            is PermitUiState.Error -> {
                onLogin()
                vm.resetState()
            }
            else -> Unit
        }
    }

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

        Spacer(modifier = Modifier.weight(3f))
    }
}