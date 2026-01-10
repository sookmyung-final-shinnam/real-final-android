package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlin.math.abs

// 공통 나이 스크롤 효과
internal suspend fun scrollToAge(
    listState: LazyListState,
    targetAge: Int,
    range: IntRange
) {
    if (listState.layoutInfo.visibleItemsInfo.isEmpty()) return

    val itemCount = range.count()
    val base = Int.MAX_VALUE / 2
    val targetIndex = base - (base % itemCount) + (targetAge - range.first)

    val viewportHeight = listState.layoutInfo.viewportSize.height
    val itemHeight = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
    val centerOffset = (viewportHeight / 2) - (itemHeight / 2)

    listState.animateScrollToItem(
        index = targetIndex,
        scrollOffset = -centerOffset
    )
}

@Composable
fun SelectionAgeScroll(
    age: Int,
    range: IntRange,
    listState: LazyListState,
    flingBehavior: FlingBehavior,
    onSelectAge: (Int) -> Unit, // 나이 변경
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold, textAlign = TextAlign.Center),
    ageTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(color = colorResource(R.color.main_orange), fontWeight = Bold)
) {
    var didInitialSync by remember { mutableStateOf(false) } // 초기 진입, 재진입용
    val itemCount = range.count()

    fun LazyListState.centeredIndex(): Int? {
        val items = layoutInfo.visibleItemsInfo
        if (items.isEmpty()) return null

        val center = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
        return items.minByOrNull {
            abs((it.offset + it.size / 2) - center)
        }?.index
    }

    // 나이 초기 정렬
    LaunchedEffect(Unit) {
        if (didInitialSync) return@LaunchedEffect

        // 레이아웃 준비 대기
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.isNotEmpty() }
            .first { it }

        scrollToAge(listState, age, range)
        didInitialSync = true
    }

    // 스크롤 멈출 때 나이 확정 후 업데이트
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.isScrollInProgress to listState.centeredIndex()
        }
            .distinctUntilChanged()
            .filter { (isScrolling, _) -> !isScrolling && didInitialSync }
            .collect { (_, centeredIdx) ->
                centeredIdx?.let { index ->
                    val newAge = (index % itemCount) + range.first
                    onSelectAge(newAge)
                }
            }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 나이 피커
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.44f)
                .zIndex(20f)
                .background(Color.Transparent),
        ) {
            items(Int.MAX_VALUE) { index ->
                val age = range.first + (index % itemCount)

                // 현재 아이템 정보
                val itemInfo = listState.layoutInfo.visibleItemsInfo
                    .firstOrNull { it.index == index }

                // 뷰포트 중앙
                val viewportCenter =
                    (listState.layoutInfo.viewportStartOffset +
                            listState.layoutInfo.viewportEndOffset) / 2

                // 중앙과의 거리(px)
                val distancePx = itemInfo?.let {
                    val itemCenter = it.offset + it.size / 2
                    abs(itemCenter - viewportCenter).toFloat()
                } ?: Float.MAX_VALUE

                // 기준 거리 (아이템 높이 * 3)
                val maxDistance = (itemInfo?.size ?: 1) * 1.4f

                // 0f(중앙) ~ 1f(멀어짐)
                val rawFraction = (distancePx / maxDistance).coerceIn(0f, 1f)
                val fraction = rawFraction * rawFraction * rawFraction

                // 시각 효과 값
                val scale = 1.9f - 1.85f * fraction
                val alpha = 1f - 0.8f * fraction

                val color = lerp(
                    start = colorResource(R.color.main_orange),
                    stop = Color.Black,
                    fraction = fraction
                ).copy(alpha = alpha)

                Text(
                    text = age.toString(),
                    style = textStyle.copy(color = color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        }
                )
            }
        }

        // 3. 중앙 강조 박스
        Box(
            modifier = Modifier
                .fillMaxWidth(0.44f)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            colorResource(R.color.lemon_yellow),
                            Color.Transparent
                        )
                    )
                )
                .height(76.dp)
        ) {
            // 위쪽 선
            HorizontalDivider(
                color = colorResource(R.color.main_orange),
                thickness = 4.dp,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            // 아래쪽 선
            HorizontalDivider(
                color = colorResource(R.color.main_orange),
                thickness = 4.dp,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            Text(
                "세 ",
                modifier = Modifier.align(Alignment.CenterEnd),
                style = ageTextStyle
            )
        }
    }
}
