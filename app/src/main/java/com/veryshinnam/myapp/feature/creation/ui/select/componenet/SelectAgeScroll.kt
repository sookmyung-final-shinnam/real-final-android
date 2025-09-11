package com.veryshinnam.myapp.feature.creation.ui.select.componenet

import android.R.attr.scaleX
import android.R.attr.scaleY
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.widget.NumberPicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.math.pow

@Composable
fun SelectAgeScroll(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    range: IntRange = 1..100,
) {

    val values = range.toList()

    val listState = rememberLazyListState(
        // 중앙 근처에서 시작 (예: 10000번째 아이템에서 시작)
        initialFirstVisibleItemIndex = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % values.size) + (value - 1)
    )
    val flingBehavior = rememberSnapFlingBehavior(listState)

    // 중앙값 추적 (스크롤 멈췄을 때)
    LaunchedEffect(value) {
        // 1. 우선 대략적인 위치로 이동
        val targetIndex = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % values.size) + (value - 1)
        listState.scrollToItem(targetIndex)
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
                    kotlin.math.abs(itemCenter - center).toFloat()
                } ?: 0f

                // 거리에 따라 가까울수록 강조
                val rawFraction = (distance / (itemInfo?.size?.times(3) ?: 1)).coerceIn(0f, 1f)
                val fraction = rawFraction.toDouble().pow(0.5).toFloat() // 부드럽게

                val scale = 1.2f - 0.7f * fraction
                val alpha = 1f - 0.7f * fraction
//                val color = Color.Black.copy(alpha = alpha)
                val color = lerp(
                    start = colorResource(R.color.main_orange),
                    stop = Color.Black,
                    fraction = fraction
                ).copy(alpha = alpha)

                Text(
                    text = itemValue.toString(),
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.SemiBold),
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
                .fillMaxWidth(0.5f)
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
