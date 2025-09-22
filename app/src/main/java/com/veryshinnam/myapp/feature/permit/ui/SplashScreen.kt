@file:OptIn(UnstableApi::class)

package com.veryshinnam.myapp.feature.permit.ui

import android.net.Uri
import android.view.SurfaceView
import android.view.View
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeText
import com.veryshinnam.myapp.common.component.VideoPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    Column(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.background_yellow)),
        verticalArrangement = Arrangement.SpaceBetween,
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

        VideoPlayer(
            videoUrl = splashVideo,
            modifier = Modifier
                .weight(7f)
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

        Spacer(modifier = Modifier.weight(3f))

    }
}