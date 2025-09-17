package com.veryshinnam.myapp.feature.story.ui.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun StoryReaderPage(
    page: PageData,
    storyType: StoryType
) {
    val context = LocalContext.current

    // ExoPlayer는 VIDEO일 때만 생성
    val exoPlayer = remember(storyType, page.image) {
        if (storyType == StoryType.VIDEO) {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(Uri.parse(page.image))) // image 필드에 mp4 URL 들어옴
                prepare()
                playWhenReady = true
            }
        } else null
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer?.release()
        }
    }

    Box(Modifier.fillMaxSize()) {
        // 페이지 이미지
        when (storyType) {
            StoryType.IMAGE -> {
                AsyncImage(
                    model = page.image,
                    contentDescription = "페이지 이미지",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            StoryType.VIDEO -> {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = {
                        PlayerView(it).apply {
                            player = exoPlayer
                            useController = false // 기본 UI 컨트롤러 숨김
                        }
                    }
                )
            }
        }

        // 줄거리 텍스트
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.2f)
                .padding(bottom = 8.dp), // 바텀 마진 효과
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.background_yellow)
            ),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(4.dp, colorResource(R.color.main_orange))
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                contentAlignment = Alignment.Center // 가로+세로 중앙 배치
            ) {
                Text(
                    text = page.content,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}