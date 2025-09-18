@file:OptIn(UnstableApi::class)
package com.veryshinnam.myapp.component.common

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(
    videoUrl: String,
    isAutoPlay: Boolean = true, // 자동 재생 여부 (기본 자동 재생)
    modifier: Modifier
) {
    val context = LocalContext.current

    // ExoPlayer 생성
    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
            prepare()
            this.playWhenReady = isAutoPlay
        }
    }

    // 생명주기 관리
    DisposableEffect(exoPlayer) {
        onDispose { exoPlayer.release() }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                useController = false // 기본 ui 컨트롤러 숨김
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM // Crop

                exoPlayer.addListener(object : Player.Listener {
                    override fun onRenderedFirstFrame() {
                        // 첫 프레임 렌더링 후 강제로 다시 레이아웃 (검정 여백 제거)
                        this@apply.post { this@apply.requestLayout() }
                    }
                })
            }
        }
    )
}