package com.veryshinnam.myapp.feature.story.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.feature.story.ui.components.CharacterFrame
import com.veryshinnam.myapp.feature.story.ui.components.StoryInfo

@Composable
fun StoryPrologueScreen(
    cImage: String,  // 캐릭터 이미지
    sTitle: String,  // 동화책 제목
    sContent: String // 동화책 줄거리
) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp) // 양옆 여백
        ) {
            // 캐릭터 액자 이미지
            CharacterFrame(
                imageUrl = cImage,
                modifier = Modifier
                    .align(Alignment.TopCenter)   // 상단부
                    .fillMaxHeight(0.75f) // 높이 75%
            )

            // 스토리 정보
            StoryInfo(
                title = sTitle,
                desc = sContent,
                modifier = Modifier
                    .align(Alignment.BottomCenter) // 하단부
                    .fillMaxHeight(0.5f)  // 높이 50%
            )
        }
}