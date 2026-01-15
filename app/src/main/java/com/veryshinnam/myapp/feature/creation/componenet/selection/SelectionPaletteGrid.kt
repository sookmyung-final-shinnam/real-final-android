package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.CircleIconButton
import kotlinx.coroutines.launch

@Composable
fun SelectionPaletteGrid(
    title: String,
    colors: List<Pair<String, Int>>,
    selectedIndex: Int,
    selectedPage: Int,
    onSelect: (String, Int, Int) -> Unit,
    horizontalPadding: Dp = 12.dp,
    verticalPadding: Dp = 8.dp,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = Bold),
    modifier: Modifier
) {
    val pageSize = 4 // 4개씩 보이기
    val pagerState = rememberPagerState(
        initialPage = selectedPage,
        pageCount = { (colors.size + pageSize - 1) / pageSize }
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedIndex) {
        val targetPage = selectedIndex / pageSize
        if (pagerState.currentPage != targetPage) {
            pagerState.scrollToPage(targetPage)
        }
    }

    Column(modifier) {
        // 팔레트 위 제목
        Text(title,
            modifier = Modifier,
            style = titleTextStyle
        )

        Box(
            modifier = Modifier
                .padding(top=verticalPadding)
                .weight(1f)
        ){
            // 팔레트
            Card(
                modifier = Modifier.fillMaxSize().padding(horizontal = horizontalPadding),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.lemon_yellow)),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                ) { page ->
                    val startIndex = page * pageSize
                    val pageItems = colors.drop(startIndex).take(pageSize)

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(vertical = verticalPadding)
                    ) {
                        pageItems.forEachIndexed { indexInPage, (label, resId) ->
                            val actualIndex = startIndex + indexInPage

                            SelectionPaletteButton(
                                color = colorResource(resId),
                                labelT = label,
                                selected = actualIndex == selectedIndex,
                                onClick = {
                                    onSelect(
                                        colors[actualIndex].first,
                                        actualIndex,
                                        pagerState.currentPage
                                    )
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // 이전 버튼
            if (pagerState.currentPage > 0 || selectedIndex > 0) {
                CircleIconButton(
                    icon = Icons.Rounded.ChevronLeft,
                    desc = "이전",
                    onClick = {
                        val page = pagerState.currentPage
                        val pageStart = page * pageSize

                        val newIndex: Int
                        val newPage: Int

                        if (selectedIndex > pageStart) {
                            newIndex = selectedIndex - 1 // 페이지 내부 색상 이동
                            newPage = page
                        } else {
                            newIndex = pageStart - 1 // 페이지 이동
                            newPage = page - 1

                            scope.launch {
                                pagerState.animateScrollToPage(newPage)
                            }
                        }

                        onSelect(colors[newIndex].first, newIndex, newPage)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxHeight(0.6f)
                        .graphicsLayer {
                            translationX = -12.dp.toPx()
                        }
                        .semantics(true) {
                            contentDescription = "$title 이전 색상 이동"
                            role = Role.Button
                        },
                    containerColor = colorResource(R.color.main_orange).copy(0.5f)
                )
            }

            // 다음 버튼
            if (pagerState.currentPage < pagerState.pageCount - 1 ||
                selectedIndex < colors.lastIndex) {
                CircleIconButton(
                    icon = Icons.Rounded.ChevronRight,
                    desc = "다음",
                    onClick = {
                        val page = pagerState.currentPage
                        val pageStart = page * pageSize
                        val pageEnd = minOf(pageStart + pageSize - 1, colors.lastIndex)

                        val newIndex: Int
                        val newPage: Int

                        if (selectedIndex < pageEnd) {
                            newIndex = selectedIndex + 1
                            newPage = page
                        } else {
                            newIndex = (page + 1) * pageSize
                            newPage = page + 1

                            scope.launch {
                                pagerState.animateScrollToPage(newPage)
                            }
                        }

                        onSelect(colors[newIndex].first, newIndex, newPage)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight(0.6f)
                        .graphicsLayer {
                            translationX = 12.dp.toPx()
                        }
                        .semantics(true) {
                            contentDescription = "$title 다음 색상 이동"
                            role = Role.Button
                        },
                    containerColor = colorResource(R.color.main_orange).copy(0.5f)
                )
            }
        }
    }
}