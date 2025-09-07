package com.veryshinnam.myapp.feature.story.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.ui.components.StoryEndingPage
import com.veryshinnam.myapp.feature.story.ui.components.StoryPageButtons
import com.veryshinnam.myapp.feature.story.ui.components.StoryReaderPage
import kotlinx.coroutines.launch

@Composable
fun StoryReadingScreen(
    pages: List<PageData>,
    onBack: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size + 1 } // 마지막 EndingPage 포함
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler { onBack() }

    Box(Modifier.fillMaxSize()) {
        // 동화 페이지
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.matchParentSize()
        ) { page ->
            if (page < pages.size) {
                StoryReaderPage(page = pages[page])
            } else {
                StoryEndingPage()
            }
        }

        if (pagerState.currentPage < pages.size) {
            // TTS 재생 버튼
            Image(
                painter = painterResource(R.drawable.img_speak_on),
//            painter = painterResource(R.drawable.img_speak_off),
                contentDescription = "읽어주기 버튼",
                modifier = Modifier.align(Alignment.TopEnd)
                    .fillMaxHeight(0.2f)
                    .padding(16.dp)
                    .clickable() {
                        // TODO: 현재 페이지 텍스트 읽기
                        if (pagerState.currentPage < pages.size) {
                            val textToRead = pages[pagerState.currentPage].content
                            // TTSManager.speak(textToRead)
                        }
                    },
                contentScale = ContentScale.Fit
            )

            // 양옆 페이지 버튼
            StoryPageButtons(
                pagerState = pagerState,
                coroutineScope = coroutineScope,
                pageCount = pages.size,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

    }
}