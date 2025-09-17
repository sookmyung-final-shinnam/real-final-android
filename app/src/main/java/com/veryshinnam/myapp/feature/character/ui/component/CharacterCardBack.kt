package com.veryshinnam.myapp.feature.character.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.feature.character.model.StoryVideoData
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun CharacterCardBack(
    story: StoryVideoData,             // 동화 정보 (종이책+영상)
    onStoryClick: (Long, StoryType) -> Unit,
    modifier: Modifier = Modifier // 부모에서 넘겨받은 크기
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        // 상단에 제목
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,   // 가운데 정렬
            text = story.title,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(8.dp))

        // 양 옆에 동화 보러가기 버튼
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // 종이책
            CharacterStoryButton(
                storyId = story.storyId ?: -1L,
                storyType = StoryType.IMAGE,
                storyUrl = story.storyImage,
                type = "종이책",
                onStoryClick = onStoryClick,
                modifier = Modifier.weight(1f)
            )

            // 짧은 영상
            CharacterStoryButton(
                storyId = story.storyId?: -1L,
                storyType = StoryType.VIDEO,
                storyUrl = story.videoImage,
                type = "움직이는 책",
                onStoryClick = onStoryClick,
                modifier = Modifier.weight(1f)
            )
        }
    }

}