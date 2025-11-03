package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import kotlin.math.abs
import kotlin.math.pow

@Composable
fun SelectionAgeScroll(
    age: Int,
    listState: LazyListState,
    onAgeConfirm: (Int) -> Unit, // 나이 확정
    modifier: Modifier = Modifier,
    range: IntRange = 1..100,
    textStyle: TextStyle = MaterialTheme.typography.displaySmall.copy(fontWeight = Bold)
) {

    val values = range.toList()
    val flingBehavior = rememberSnapFlingBehavior(listState)

    // 사용자가 스크롤했는지 추적
    var hasUserScrolled by remember { mutableStateOf(false) }

    // 중앙값 추적 (스크롤 멈췄을 때)
    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            hasUserScrolled = true
        }
        if (!listState.isScrollInProgress && hasUserScrolled) {

            // 화면 중앙 좌표 계산
            val center = (listState.layoutInfo.viewportStartOffset + listState.layoutInfo.viewportEndOffset) / 2

            // 중앙과 가장 가까운 값 찾기
            val closest = listState.layoutInfo.visibleItemsInfo.minByOrNull { item ->
                val itemCenter = item.offset + item.size / 2
                abs(itemCenter - center)
            }

            // 중앙과 가장 가까운 값 > 나이
            closest?.let {
                val selectedAge = values[it.index % values.size]
                onAgeConfirm(selectedAge)
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .background(Color.Transparent)
                .padding(vertical = 12.dp)
        ) {
            items(Int.MAX_VALUE) { index ->
                val itemValue = values[index % values.size] // 반복

                // 현재 아이템의 중앙 위치
                val itemInfo = listState.layoutInfo.visibleItemsInfo.find { it.index == index }
                val center = (listState.layoutInfo.viewportStartOffset +
                        listState.layoutInfo.viewportEndOffset) / 2

                // 중앙과 거리
                val distance = itemInfo?.let {
                    val itemCenter = it.offset + it.size / 2
                    abs(itemCenter - center).toFloat()
                } ?: 0f

                // 거리에 따라 가까울수록 강조
                val rawFraction = (distance / (itemInfo?.size?.times(3) ?: 1)).coerceIn(0f, 1f)
                val fraction = rawFraction.toDouble().pow(0.5).toFloat() // 부드럽게

                val scale = 1.2f - 0.9f * fraction
                val alpha = 1f - 0.7f * fraction
                val color = lerp(
                    start = colorResource(R.color.main_orange),
                    stop = Color.Black,
                    fraction = fraction
                ).copy(alpha = alpha)

                Text(
                    text = itemValue.toString(),
                    style = textStyle,
                    color = color,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxHeight() // 부모 높이의 20%
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
                        .graphicsLayer {
                            this.alpha = alpha   // 알파 적용
                            this.scaleX = scale
                            this.scaleY = scale
                        }
                )
            }
        }

        // 중앙 강조 박스
        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(72.dp)
        ) {
            // 위쪽 선
            Divider(
                color = colorResource(R.color.main_orange),
                thickness = 2.dp,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            // 아래쪽 선
            Divider(
                color = colorResource(R.color.main_orange),
                thickness = 2.dp,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
