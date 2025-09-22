@file:OptIn(UnstableApi::class)

package com.veryshinnam.myapp.common.component

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
    modifier: Modifier,
    isRepeated: Boolean = true,                  // 반복 여부
    resizeMode: Int = AspectRatioFrameLayout.RESIZE_MODE_ZOOM, // 기본 Crop
    onVideoEnd: () -> Unit = {  }
) {
    val context = LocalContext.current

    // ExoPlayer 생성
    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
            prepare()
            playWhenReady = true // 바로 재생
            repeatMode = if (isRepeated) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
        }
    }

    // 생명주기 관리
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    onVideoEnd.invoke()
                }
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                useController = false // 기본 ui 컨트롤러 숨김
                this.resizeMode = resizeMode // Crop

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