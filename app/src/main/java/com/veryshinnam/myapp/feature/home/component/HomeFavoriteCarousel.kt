package com.veryshinnam.myapp.feature.home.component

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.home.model.FavoriteData
import kotlin.math.abs

@Composable
fun HomeFavoriteCarousel(
    modifier: Modifier = Modifier,
    characters: List<FavoriteData>,
    lastSelectedId: Long?,
    onCharacterClick: (Long) -> Unit,
) {
    // 즐찾 캐릭터가 없는 경우
    if (characters.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(colorResource(R.color.main_orange)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)   // 가로는 60% 정도
                    .aspectRatio(3f / 4f) // 비율 유지 (3:4)
            ) {
                HomeNullCard(modifier = Modifier.fillMaxSize().padding(16.dp))
            }
        }
        return
    }

// 부족한 건 null로 채워서 카드 다섯개 맞추기
    val favorites: List<FavoriteData?> =
        if (characters.size < 5) {
            characters + List(5 - characters.size) { null }
        } else { characters }

    val n = favorites.size
    val loopCount = Int.MAX_VALUE  // 무한 스크롤

    val baseIndex = (loopCount / 2) - ((loopCount / 2) % n) // 스크롤 상태 관리 (첫 인덱스에서 시작)
    val listState = rememberLazyListState()

// 현재 중앙 아이템 인덱스 계산
    val layoutInfo = listState.layoutInfo           // 현재 리스트 레이아웃 정보
    val visibleItems = layoutInfo.visibleItemsInfo  // 현재 보이는 아이템들 정보
    val screenCenter = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.width / 2


// 보이는 아이템 중 "화면 중앙에 가장 가까운 아이템" 찾기
    val centerItem = visibleItems.minByOrNull { item ->
        abs((item.offset + item.size / 2) - screenCenter)
    }

// 그 아이템의 index를 n(데이터 크기)로 나눈 값 → 실제 데이터 인덱스
    val currentIndex = centerItem?.index?.rem(n)?.let { (it + n) % n } ?: 0

    LaunchedEffect(lastSelectedId) {
        if (lastSelectedId != null) {
            val selectedIndex = characters.indexOfFirst { it.id == lastSelectedId }
            if (selectedIndex >= 0) {
                val targetIndex = baseIndex + selectedIndex
                listState.scrollToItem(targetIndex)
            }
        } else {
            // 아무 선택 없으면 기본 가운데(baseIndex)로
            listState.scrollToItem(baseIndex)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,           // 세로 중앙
        horizontalAlignment = Alignment.CenterHorizontally  // 가로 중앙
    ) {
        // 캐러셀
        LazyRow(
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(listState), // 스크롤 시 중간 위치
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 100.dp),
        ) {
            items(loopCount) { index ->
                // 무한 스크롤 효과: 실제 데이터는 index % n 으로 순환
                val character = favorites[index % n]

                // 현재 아이템의 중심점 계산
                val currentItem = visibleItems.find { it.index == index }
                val itemCenter = currentItem?.let { it.offset + it.size / 2 } ?: 0

                // 화면 중앙과의 거리 계산
                val distanceFromCenter = abs(screenCenter - itemCenter).toFloat()
                val maxDistance = layoutInfo.viewportSize.width / 2f

                // 0 ~ 1 사이로 정규화 (가운데 = 0, 멀리 = 1)
                val normalizedDistance = (distanceFromCenter / maxDistance).coerceAtMost(1f)

                val scale = 1f - (normalizedDistance * 0.4f) // 중앙에 가까울수록 크게
                val alpha = 1f - (normalizedDistance * 0.5f) // 중앙에 가까울수록 선명

                val isCenter = normalizedDistance < 0.3f // 중앙 여부 플래그

                HomeFavoriteCard(
                    character = character,
                    modifier = Modifier
                        .fillMaxHeight(0.8f)         // 세로 크기 기준
                        .aspectRatio(3f / 4f)        // 가로:세로 = 3:4
                        .graphicsLayer { // 중앙 강조 효과
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        },
                    isCenter = isCenter,
                    onClick = { c -> c?.let { onCharacterClick(it.id) } }
                )
            }
        }

        // 현재 캐릭터 인덱스 표시
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Text(
            text = buildAnnotatedString {
                // 현재 인덱스 부분 → Bold + 크게
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp, // 원하는 크기
                        color = colorResource(R.color.main_orange)
                    )
                ) {
                    append("${currentIndex + 1}")
                }
                // 구분자 + 전체 개수 부분 → 기본 스타일
                append(" / $n")
            },
            style = MaterialTheme.typography.headlineSmall.copy(color = colorResource(R.color.main_orange))
        )
    }
}