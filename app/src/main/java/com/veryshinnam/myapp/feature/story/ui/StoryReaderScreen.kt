package com.veryshinnam.myapp.feature.story.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.ui.components.StoryEndingPage
import com.veryshinnam.myapp.feature.story.ui.components.StoryPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun StoryReaderScreen(
    pages: List<PageData>,
    onBack: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size + 1 } // 마지막 EndingPage 포함
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler { onBack() }

    Box(
        Modifier.fillMaxSize()
    ) {
        // 동화 페이지
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.matchParentSize()
        ) { page ->
            if (page < pages.size) {
                StoryPage(page = pages[page])
            } else {
                StoryEndingPage()
            }
        }

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

        // 양옆 페이지 이동 버튼
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 이전 페이지 버튼
            // TODO: 이미지로 변경
            Button(
                onClick = {
                    if (pagerState.currentPage > 0) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.main_orange),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .aspectRatio(1f)
            ) {
                Text(
                    text = "◀",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 다음 페이지 버튼
            Button(
                onClick = {
                    if (pagerState.currentPage < pages.size) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.main_orange),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .aspectRatio(1f)
            ) {
                Text(
                    text = "▶",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}