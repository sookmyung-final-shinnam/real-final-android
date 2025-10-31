package com.veryshinnam.myapp.feature.story.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.VideoPlayer
import com.veryshinnam.myapp.feature.story.model.StoryData
import com.veryshinnam.myapp.feature.story.model.StoryType
import com.veryshinnam.myapp.feature.story.components.StoryReadButton
import com.veryshinnam.myapp.feature.story.components.StoryInfoFade

@Composable
fun StoryPrologueContent(
    story: StoryData,
    storyType: StoryType,
    onReadClick: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.main_orange))
        .padding( WindowInsets.navigationBars.asPaddingValues())
    ) {
        // --- 동화 썸네일 이미지
        when (storyType) {
            // 페이지 이미지
            StoryType.IMAGE -> {
                AsyncImage(
                    model = story.thumbnail,
                    contentDescription = "동화 썸네일 이미지",
                    modifier = Modifier.fillMaxWidth(0.6f).zIndex(0f),
                    contentScale = ContentScale.Crop
                )
            }
            StoryType.VIDEO -> {
                VideoPlayer(
                    videoUrl = story.thumbnail,
                    modifier = Modifier.fillMaxWidth(0.6f).fillMaxHeight().zIndex(0f)
                )
            }
        }

        // --- 동화 정보
        StoryInfoFade(
            story,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .align(Alignment.CenterEnd)
        )

        // 보러가기 버튼
        StoryReadButton(onButtonClick = onReadClick,
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .padding(16.dp)
                .align(Alignment.BottomEnd))
    }
}