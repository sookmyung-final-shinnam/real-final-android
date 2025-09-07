package com.veryshinnam.myapp.feature.story.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.veryshinnam.myapp.feature.story.model.StoryData
import com.veryshinnam.myapp.feature.story.ui.components.StoryReadButton
import com.veryshinnam.myapp.feature.story.ui.components.StoryInfoFade

@Composable
fun StoryPrologueScreen(
    story: StoryData,
    onReadClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 동화 썸네일 이미지
        AsyncImage(
            model = story.thumbnail,
            contentDescription = "동화 썸네일 이미지",
            modifier = Modifier.fillMaxWidth(0.6f).zIndex(0f),
            contentScale = ContentScale.Crop
        )

        // 동화 정보 - 오른쪽으로 붙이기
        StoryInfoFade(story,
            Modifier.fillMaxWidth(0.6f).align(Alignment.CenterEnd))

        // 보러가기 버튼
        StoryReadButton(onButtonClick = onReadClick,
            modifier = Modifier
                .fillMaxHeight(0.25f)
                .padding(16.dp)
                .align(Alignment.BottomEnd))
    }
}