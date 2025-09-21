package com.veryshinnam.myapp.feature.permit.ui

import android.net.Uri
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
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
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
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoUri = Uri.parse("android.resource://${context.packageName}/${R.raw.splash}")
            setMediaItem(MediaItem.fromUri(videoUri))
            repeatMode = ExoPlayer.REPEAT_MODE_OFF // 자동 반복 끄기
            prepare()
            playWhenReady = true
        }
    }

    LaunchedEffect(Unit) {
        delay(4000) // 스플래시 2초 보여주기
        vm.checkAccessToken() // 스플래시 끝나고 토큰 검사 실행
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
//        Image(
//            painter = painterResource(R.drawable.img_splash),
//            contentDescription = "스플래시 이미지",
//            modifier = Modifier
//                .weight(7f)
//                .fillMaxWidth(),
//            contentScale = ContentScale.Fit
//        )

        DisposableEffect(
            AndroidView(
                modifier = Modifier
                    .weight(7f)
                    .fillMaxWidth(),
                factory = {
                    PlayerView(it).apply {
                        player = exoPlayer
                        useController = false

                        exoPlayer.addListener(object : Player.Listener {
                            override fun onRenderedFirstFrame() {
                                // 첫 프레임 렌더링 후 강제로 다시 레이아웃 → 검정 여백 제거
                                this@apply.post { this@apply.requestLayout() }
                            }
                        })
                    }
                }
            )
        ) {
            onDispose { exoPlayer.release() }
        }

        Spacer(modifier = Modifier.weight(3f))
    }
}