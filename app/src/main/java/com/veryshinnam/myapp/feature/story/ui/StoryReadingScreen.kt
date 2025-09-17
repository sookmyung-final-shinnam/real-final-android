package com.veryshinnam.myapp.feature.story.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    isTtsMode: Boolean,
    isReady: Boolean,
    onTtsModeChange: () -> Unit,
    onBack: () -> Unit,
    onHome: () -> Unit,
    onSpeakPage: (String) -> Unit,
    onStopSpeaking: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size + 1 } // 마지막 EndingPage 포함
    )
    val coroutineScope = rememberCoroutineScope()

    // 자동 읽기 > TTS 모드 ON + 엔진 준비 + 페이지 변경 시
    LaunchedEffect(pagerState.currentPage, isTtsMode, isReady) {
        if (pagerState.currentPage < pages.size) {
            if (isTtsMode && isReady) {
                val text = pages[pagerState.currentPage].content
                onSpeakPage(text)
            } else {
                onStopSpeaking()
            }
        } else {
            onStopSpeaking()
        }
    }

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
                StoryEndingPage(
                    onRestart = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0) // 첫 페이지로 이동
                        }
                    },
                    onHome = onHome
                )
            }
        }

        if (pagerState.currentPage < pages.size) {
            // TTS 재생 버튼
            Image(
                painter = if (isTtsMode) { painterResource(R.drawable.img_speak_on) }
                        else { painterResource(R.drawable.img_speak_off) },
                contentDescription = "읽어주기 버튼",
                modifier = Modifier.align(Alignment.TopEnd)
                    .fillMaxHeight(0.2f)
                    .padding(16.dp)
                    .clickable { onTtsModeChange() },
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