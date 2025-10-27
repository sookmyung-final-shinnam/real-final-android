@file:OptIn(UnstableApi::class)

package com.veryshinnam.myapp.feature.permit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoText
import com.veryshinnam.myapp.common.component.StrokeText
import com.veryshinnam.myapp.common.component.VideoPlayer

// 스플래시 스크린

/**
 * 앱 시작 후, mp4 재생 & 토큰 만료 체크 진행
 * onHome: 토큰  유효 > 홈 스크린으로 이동
 * onLogin: 토큰 만료 > 로그인 스크린으로 이동
 */
@Composable
fun SplashScreen(
    onHome: () -> Unit,
    onLogin: () -> Unit,
    vm: PermitViewModel = hiltViewModel()
) {
    val state by vm.permitUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val splashVideo = "android.resource://${context.packageName}/${R.raw.splash}"

    LaunchedEffect(Unit) {
        vm.checkAccessToken()
    }

    //  스플래시 UI
    Column(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.background_yellow)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1.5f))

        // 로고
        LogoText(modifier = Modifier.weight(1.5f))

        // mp4
        VideoPlayer(
            videoUrl = splashVideo,
            modifier = Modifier
                .weight(6f)
                .fillMaxWidth(),
            isRepeated = false,
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT, // 원본 비율 유지
            onVideoEnd = {
                // 상태가 결정된 후 이동
                if (state is PermitUiState.Success) {
                    onHome()
                } else if (state is PermitUiState.Error) {
                    onLogin()
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}