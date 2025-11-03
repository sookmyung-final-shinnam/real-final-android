package com.veryshinnam.myapp.feature.story.content

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryType
import com.veryshinnam.myapp.feature.story.components.StoryEndingPage
import com.veryshinnam.myapp.feature.story.components.StoryReaderPage
import kotlinx.coroutines.launch

@Composable
fun StoryReadingContent(
    pages: List<PageData>,
    storyType: StoryType,
    isTtsMode: Boolean,
    isReady: Boolean,
    onTtsModeChange: () -> Unit,
    onHome: () -> Unit,
    onPrologue: () -> Unit,
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

    BackHandler { onPrologue() }

    Box(Modifier.fillMaxSize()) {
        // 동화 페이지
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.matchParentSize()
        ) { page ->
            if (page < pages.size) {
                StoryReaderPage(
                    page = pages[page],
                    storyType = storyType,
                    pagerState = pagerState,
                    coroutineScope = coroutineScope,
                    pageCount = pages.size,
                    onEndingPage = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pages.size)
                        }
                    }
                )
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
                contentDescription = "읽어 주기 버튼",
                modifier = Modifier
                    .fillMaxHeight(0.24f)
                    .padding(WindowInsets.systemBars.asPaddingValues())
                    .align(Alignment.TopEnd)
                    .clickable { onTtsModeChange() },
                contentScale = ContentScale.Fit
            )

            // 양옆 페이지 버튼
//                StoryPageButtons(
//                    pagerState = pagerState,
//                    coroutineScope = coroutineScope,
//                    pageCount = pages.size,
//                    modifier = Modifier.align(Alignment.BottomCenter)
//                )
        }
    }
}
