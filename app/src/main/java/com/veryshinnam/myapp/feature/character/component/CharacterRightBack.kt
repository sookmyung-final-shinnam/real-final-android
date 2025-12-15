package com.veryshinnam.myapp.feature.character.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
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
import com.veryshinnam.myapp.feature.character.model.StoryStatus
import com.veryshinnam.myapp.feature.story.model.StoryType
import kotlinx.coroutines.delay

@Composable
fun CharacterRightBack(
    modifier: Modifier,
    stories: StoriesData,             // 동화 정보 (종이책+영상)
    onStoryClick: (Long, StoryType) -> Unit,
    onLockerClick: (Long) -> Unit,
    onMakingClick: () -> Unit,
    onShareClick: (String?) -> Unit,
    onLockerRect: (Rect) -> Unit,
    onKakaoRect: (Rect) -> Unit,
    rotation: Float,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
    infoTextStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
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
                    storyType = StoryType.IMAGE,
                    storyTypeText = "동화",
                    storyUrl = stories.imageUrl,
                    storyYLink = stories.imageYLink,
                    storyStatus = StoryStatus.COMPLETED,
                    onStoryClick = onStoryClick,
                    onShareClick = onShareClick,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "동화 보러 가기",
                    style = infoTextStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 움직이는 동화
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CharacterStoryButton(
                    storyId = stories.storyId,
                    storyType = StoryType.VIDEO,
                    storyTypeText = "움직이는 동화",
                    videoUrl = stories.videoUrl,
                    storyYLink = stories.videoYLink,
                    storyStatus = stories.storyStatus,
                    onStoryClick = onStoryClick,
                    onMakingClick = onMakingClick,
                    onLockerClick = onLockerClick,
                    onShareClick = onShareClick,
                    modifier = Modifier.weight(1f)
                        .onGloballyPositioned { it ->
                            if (rotation == 180f) {
                                val rect = it.boundsInWindow()
                                onLockerRect(rect)
                                Log.d("manual", "rect update: $rect")
                            }
                        }
                )

                Text(
                    text = "움직이는 동화 보러 가기",
                    style = infoTextStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}