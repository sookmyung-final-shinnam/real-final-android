package com.veryshinnam.myapp.feature.character.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeTitle
import com.veryshinnam.myapp.feature.character.model.StoriesData
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun CharacterRightBack(
    modifier: Modifier,
    stories: StoriesData,             // 동화 정보 (종이책+영상)
    onStoryClick: (Long, StoryType) -> Unit,
    onLockerClick: (Long) -> Unit,
    onShareClick: (String) -> Unit,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
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
            CharacterStoryButton(
                storyId = stories.storyId,
                storyType = StoryType.IMAGE,
                storyUrl = stories.imageUrl,
                typeText = "동화",
                onStoryClick = onStoryClick,
                onShareClick = onShareClick,
                modifier = Modifier.weight(1f)
            )

            // 움직이는 동화
            CharacterStoryButton(
                storyId = stories.storyId,
                storyType = StoryType.VIDEO,
                storyUrl = stories.videoUrl,
                typeText = "움직이는 동화",
                onStoryClick = onStoryClick,
                onLockerClick = onLockerClick,
                onShareClick = onShareClick,
                modifier = Modifier.weight(1f)
            )
        }
    }

}