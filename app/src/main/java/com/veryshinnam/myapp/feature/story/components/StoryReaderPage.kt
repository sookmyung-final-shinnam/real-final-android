package com.veryshinnam.myapp.feature.story.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.VideoPlayer
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun StoryReaderPage(
    page: PageData,
    storyType: StoryType
) {
    Box(Modifier.fillMaxSize()) {
        when (storyType) {
            // 페이지 이미지
            StoryType.IMAGE -> {
                AsyncImage(
                    model = page.url,
                    contentDescription = "페이지 이미지",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // 페이지 영상
            StoryType.VIDEO -> {
                VideoPlayer(
                    videoUrl = page.url,
                    modifier = Modifier.fillMaxSize()
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
                containerColor = colorResource(R.color.background_yellow).copy(alpha = 0.5f)
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