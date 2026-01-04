package com.veryshinnam.myapp.feature.story.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.VideoPlayer
import com.veryshinnam.myapp.feature.story.model.StoryData
import com.veryshinnam.myapp.feature.story.model.StoryType
import com.veryshinnam.myapp.feature.story.components.StoryReadButton
import com.veryshinnam.myapp.feature.story.components.StoryInfoFade
import kotlin.math.max
import kotlin.math.min

@Composable
fun StoryPrologueContent(
    story: StoryData,
    storyType: StoryType,
    onReadClick: () -> Unit,
) {
    // --- 동화 썸네일 이미지 (45:55)
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.main_orange))
        .navigationBarsPadding()
    ) {
        // --- 썸네일
        when (storyType) {
            StoryType.IMAGE -> {
                    AsyncImage(
                        model = story.thumbnail,
                        contentDescription = "동화 썸네일 이미지",
                        modifier = Modifier.fillMaxWidth(0.45f),
                        contentScale = ContentScale.Crop
                    )
            }
            StoryType.VIDEO -> {
                VideoPlayer(
                    videoUrl = story.thumbnail,
                    modifier = Modifier.fillMaxWidth(0.45f)
                )
            }
        }

        // --- 동화 정보
        StoryInfoFade(
            story,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterEnd)
        )

        // 보러가기 버튼
        Box(
            modifier = Modifier
            .fillMaxWidth(0.48f)
            .fillMaxHeight()
        ) {
            StoryReadButton(
                onButtonClick = onReadClick,
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}