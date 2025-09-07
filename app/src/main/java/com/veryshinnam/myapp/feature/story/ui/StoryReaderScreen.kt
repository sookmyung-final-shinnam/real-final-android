package com.veryshinnam.myapp.feature.story.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.ui.components.StoryEndingPage
import com.veryshinnam.myapp.feature.story.ui.components.StoryPage

@Composable
fun StoryReaderScreen(
    pages: List<PageData>,
    onBack: () -> Unit
) {

    BackHandler {
        onBack()
    }
//    val pagerState = rememberPagerState()
//
//    HorizontalPager(
//        pageCount = pages.size + 1, // 마지막은 EndingPage
//        state = pagerState
//    ) { page ->
//        if (page < pages.size) {
//            StoryPage(pageData = pages[page])
//        } else {
//            StoryEndingPage()
//        }
//    }
    Text("h2")
}