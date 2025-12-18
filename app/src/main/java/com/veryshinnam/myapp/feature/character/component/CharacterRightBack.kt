package com.veryshinnam.myapp.feature.character.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeTitle
import com.veryshinnam.myapp.feature.character.model.StoriesData
import com.veryshinnam.myapp.feature.character.model.VideoStatus
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun CharacterRightBack(
    modifier: Modifier,
    stories: StoriesData,             // 동화 정보 (종이책 + 영상)
    onStoryClick: (Long, StoryType) -> Unit,
    onLockerClick: (Long) -> Unit,
    onMakingClick: () -> Unit,
    onKakaoClick: (String?) -> Unit,
    onStoryRect: (Rect) -> Unit,
    onVideoRect: (Rect) -> Unit,
    rotation: Float,
    storyInfo: String = "동화 보러 가기",
    videoInfo: String = "움직이는 동화 보러 가기",
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
    infoTextStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
) {
    Column(modifier = modifier) {
        // --- 동화 제목
        StrokeTitle(
            titleText = stories.title,
            titleColor = Color.White,
            strokeColor = colorResource(R.color.main_orange),
            titleTextStyle = titleTextStyle,
            strokeWidth = 4f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // --- 동화 보러 가기
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ) {

            // 종이 동화
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CharacterStoryButton(
                    storyId = stories.storyId,
                    imageUrl = stories.imageUrl,
                    youTubeLink = stories.imageYLink,
                    infoText = storyInfo,
                    onStoryClick = onStoryClick,
                    onKakaoClick = onKakaoClick,
                    modifier = Modifier.weight(1f)
                        .onGloballyPositioned { it ->
                            if (rotation == 180f) {
                                val rect = it.boundsInWindow()
                                onStoryRect(rect)
                                Log.d("manual", "story rect update: $rect")
                            }
                        }
                )

                Text(
                    text = storyInfo,
                    style = infoTextStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1
                )
            }


            // 움직이는 동화
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CharacterVideoButton(
                    storyId = stories.storyId,
                    videoUrl = stories.videoUrl,
                    videoStatus = stories.videoStatus,
                    youTubeLink = stories.videoYLink,
                    infoText = videoInfo,
                    onStoryClick = onStoryClick,
                    onMakingClick = onMakingClick,
                    onLockerClick = onLockerClick,
                    onKakaoClick = onKakaoClick,
                    modifier = Modifier.weight(1f)
                        .onGloballyPositioned { it ->
                            if (rotation == 180f) {
                                val rect = it.boundsInWindow()
                                onVideoRect(rect)
                                Log.d("manual", "video rect update: $rect")
                            }
                        }
                )

                Text(
                    text = videoInfo,
                    style = infoTextStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1
                )
            }

        }
    }
}