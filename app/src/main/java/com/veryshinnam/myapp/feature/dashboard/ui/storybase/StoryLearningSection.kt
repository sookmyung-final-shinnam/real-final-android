package com.veryshinnam.myapp.feature.dashboard.ui.storybase

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.feature.dashboard.model.EmotionItem
import com.veryshinnam.myapp.feature.dashboard.model.LanguageItem
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlin.math.abs

@Composable
fun StoryLearningSection(
    stories: List<Pair<LanguageItem, EmotionItem>>,
    onCardClick: (LanguageItem, EmotionItem) -> Unit
) {
    if (stories.isEmpty()) {
        Text("📚 학습 데이터가 없어요.")
        return
    }

    val listState = rememberLazyListState()
    val density = LocalDensity.current

    val n = stories.size

    val loopCount = 1000 * n

    val baseIndex = (loopCount / 2) - ((loopCount / 2) % n) // 중앙 시작 인덱스

    // 중앙 정렬을 위한 초기 스크롤
    LaunchedEffect(Unit) {
        val itemWidth = with(density) { 300.dp.toPx() + 16.dp.toPx() }

        // 뷰포트 너비가 결정된 후, 중앙 정렬 오프셋 계산 및 스크롤
        snapshotFlow { listState.layoutInfo.viewportSize.width }
            .filter { it > 0 }
            .firstOrNull()?.let { viewportWidth ->
                val offset = ((viewportWidth - itemWidth) / 2).toInt()
                listState.scrollToItem(baseIndex, -offset)
            }
    }

    LazyRow(
        state = listState,
        flingBehavior = rememberSnapFlingBehavior(listState),
        contentPadding = PaddingValues(horizontal = 64.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.height(600.dp)
    ) {
        items(loopCount) { index ->
            val (lang, emotion) = stories[index % n]

            // 가운데일수록 확대하는 효과
            // LazyListState를 이용한 중앙 아이템 감지 및 스케일링 로직
            val visibleItems = listState.layoutInfo.visibleItemsInfo
            val centerOffset = listState.layoutInfo.viewportStartOffset + listState.layoutInfo.viewportSize.width / 2

            val currentItemInfo = visibleItems.find { it.index == index }
            val itemCenter = currentItemInfo?.let { (it.offset + it.size / 2).toFloat() } ?: 0f

            val dist = abs(centerOffset - itemCenter) // 아이템 중심과 뷰포트 중심 사이의 거리
            val maxDist = listState.layoutInfo.viewportSize.width / 2f // 최대 거리 (뷰포트 중심에서 시작/끝까지)
            val normalized = (dist / maxDist).coerceAtMost(1f) // 정규화된 거리 (0.0 ~ 1.0)

            // 중앙에 가까울수록 (normalized가 0에 가까울수록) scale이 커짐
            val scale = 1f - (normalized * 0.4f)
            val alpha = 1f - (normalized * 0.3f)
            val isCenter = normalized < 0.15f

            Box(
                modifier = Modifier
                    .width(300.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    },
                contentAlignment = Alignment.Center
            ) {
                StoryCardItem(
                    lang = lang,
                    emotion = emotion,
                    isCenter = isCenter,
                    onClick = { onCardClick(lang, emotion) }
                )
            }
        }
    }

}