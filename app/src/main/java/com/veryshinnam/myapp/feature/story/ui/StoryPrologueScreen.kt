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
import com.veryshinnam.myapp.feature.story.ui.components.StoryGoToButton
import com.veryshinnam.myapp.feature.story.ui.components.StoryInfoFade

@Composable
fun StoryPrologueScreen(
    cImage: String,  // 캐릭터 이미지
    sTitle: String,  // 동화책 제목
    stags: String,
    sDesc: String // 동화책 줄거리
) {
    val activity = LocalActivity.current

    // 가로모드 + id가 바뀌면 재로딩
    LaunchedEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 동화 썸네일 이미지
        AsyncImage(
            model = cImage,
            contentDescription = "동화 썸네일 이미지",
            modifier = Modifier.fillMaxWidth(0.6f).zIndex(0f),
            contentScale = ContentScale.Crop
        )

        // 동화 정보 - 오른쪽으로 붙이기
        StoryInfoFade(sTitle, stags, sDesc,
            Modifier.fillMaxWidth(0.6f).align(Alignment.CenterEnd))

        // 보러가기 버튼
        StoryGoToButton(onButtonClick = {},
            modifier = Modifier
                .fillMaxHeight(0.25f)
                .padding(16.dp)
                .align(Alignment.BottomEnd))
    }
}