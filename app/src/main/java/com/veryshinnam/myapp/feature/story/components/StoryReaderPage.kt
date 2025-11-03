package com.veryshinnam.myapp.feature.story.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.VideoPlayer
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun StoryReaderPage(
    page: PageData,
    storyType: StoryType,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    pageCount: Int,
    horizontalPadding: Dp = 16.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center),
    onEndingPage: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        when (storyType) {
            // 페이지 이미지
            StoryType.IMAGE -> {
                AsyncImage(
                    model = page.url,
                    contentDescription = "페이지 이미지",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // 페이지 영상
            StoryType.VIDEO -> {
                VideoPlayer(
                    videoUrl = page.url,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // --- 페이지 하단
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = horizontalPadding/2, start = horizontalPadding/2)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(WindowInsets.navigationBars.asPaddingValues()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(horizontalPadding/2)
            ) {
                // ◀ 이전 버튼
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
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .weight(0.08f)
                        .aspectRatio(1f)
                ) {
                    Text(
                        text = "◀",
                        style = textStyle.copy(fontSize = textStyle.fontSize * 1.2f)
                    )
                }

                // 줄거리 내용
                Box(
                    modifier = Modifier
//                        .fillMaxWidth(.12f)
                        .weight(1f)
                        .background(
                            color = colorResource(R.color.background_yellow).copy(alpha = 0.5f),
                            shape = RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = colorResource(R.color.main_orange),
                            shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontalPadding ),
                        text = page.content,
                        style = textStyle
                    )
                }

                // ▶ 다음 버튼
                Button(
                    onClick = {
                        if (pagerState.currentPage < pageCount - 1) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }  else {
                            onEndingPage()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.main_orange),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .weight(0.08f)
                        .aspectRatio(1f)
                ) {
                    Text(
                        text = "▶",
                        style = textStyle.copy(fontSize = textStyle.fontSize * 1.2f)
                    )
                }
            }
        }
    }
}