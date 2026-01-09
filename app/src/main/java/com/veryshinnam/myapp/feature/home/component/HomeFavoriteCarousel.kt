package com.veryshinnam.myapp.feature.home.component

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.CircleIconButton
import com.veryshinnam.myapp.feature.home.model.FavoriteData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.abs


// 카드 이동 버튼 - 스크롤 애니메이션 효과
suspend fun animateToCenter(
    listState: LazyListState,
    targetIndex: Int
) {
    snapshotFlow { listState.layoutInfo.visibleItemsInfo.isNotEmpty() }
        .first { it }

    listState.animateScrollToItem(targetIndex)

    snapshotFlow { listState.layoutInfo }
        .first { info ->
            info.visibleItemsInfo.any { it.index == targetIndex }
        }

    val info = listState.layoutInfo
    val viewportCenter = info.viewportSize.width / 2
    val item = info.visibleItemsInfo.first { it.index == targetIndex }
    val itemSize = item.size

    val scrollOffset = -(viewportCenter - itemSize / 2)

    listState.animateScrollToItem(
        index = targetIndex,
        scrollOffset = scrollOffset
    )
}

@Composable
fun HomeFavoriteCarousel(
    modifier: Modifier = Modifier,
    characters: List<FavoriteData>,
    lastSelectedId: Long?,
    onCharacterClick: (Long) -> Unit,
    onCharacterChanged: (Long) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    spanTextStyle: TextStyle = MaterialTheme.typography.displaySmall
) {
    val density = LocalDensity.current
    val spacer = 8.dp

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val focusRequester = remember { FocusRequester() }
    var centerCard by remember { mutableStateOf<Rect?>(null) } // 가운데 카드 위치 정보

    // 부족한 건 null로 채워서 카드 다섯 개 맞추기
    val favorites: List<FavoriteData?> =
        if (characters.size < 5) {
            characters + List(5 - characters.size) { null }
        } else { characters }

    val n = favorites.size // 5개 제한

    // 무한 스크롤 고려 시작 인덱스
    val baseIndex = remember(n) {
        val mid = Int.MAX_VALUE / 2
        mid - (mid % n) // n의 배수로 둠
    }

    // 화면 센터 캐릭터 번호
    val centerIndex by remember {
        derivedStateOf {
            val info = listState.layoutInfo
            val visible = info.visibleItemsInfo
            if (visible.isEmpty()) return@derivedStateOf 0

            val viewportCenter = info.viewportStartOffset + info.viewportSize.width / 2

            val centerItem = visible.minByOrNull { item ->
                abs((item.offset + item.size / 2) - viewportCenter)
            }

            centerItem?.index?.mod(n) ?: 0
        }
    }

    // -- 초기화 설정
    // 초기 정렬 | lastSelectedId 변경 | 화면 회전 시 재정렬
    LaunchedEffect(lastSelectedId, listState.layoutInfo.viewportSize) {
        // 레이아웃 준비 대기
        snapshotFlow {
            val info = listState.layoutInfo
            info.visibleItemsInfo.isNotEmpty() &&
                    info.visibleItemsInfo.all { it.size > 0 }
        }.first { it }

        val target =
            lastSelectedId?.let { id ->
                val selected = characters.indexOfFirst { it.id == id }
                if (selected >= 0) baseIndex + selected else baseIndex
            } ?: baseIndex

        // 먼저 target 근처로 점프
        listState.scrollToItem(target)

        // 한 프레임 대기
        snapshotFlow { listState.layoutInfo }
            .first { it.visibleItemsInfo.any { item -> item.index == target } }

        // 정확한 중앙 정렬 계산
        val info = listState.layoutInfo
        val viewportCenter = info.viewportSize.width / 2

        val item = info.visibleItemsInfo.firstOrNull { it.index == target }
        if (item != null) {

            // 아이템의 중앙이 뷰포트 중앙에 오도록 offset 계산
            val itemSize = item.size
            val scrollOffset = -(viewportCenter - itemSize / 2)

            listState.scrollToItem(
                index = target,
                scrollOffset = scrollOffset
            )
        }
    }

    // 스크롤 중단 시 마지막 선택 캐릭터 업데이트
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            favorites[centerIndex]?.let { it ->
                onCharacterChanged(it.id)
            }
        }
    }

    LaunchedEffect(centerIndex) {
        focusRequester.requestFocus()
    }

    // --- 즐찾 캐릭터가 없는 경우
    if (characters.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(colorResource(R.color.main_orange)),
                modifier = Modifier
                    .semantics { }
                    .fillMaxWidth(0.6f)   // 가로는 60% 정도
                    .aspectRatio(3f / 4f)   // 카드 비율 유지 (3:4)
                    .clearAndSetSemantics {
                        contentDescription = "즐겨찾기한 캐릭터 없음. 보관함에서 즐겨찾기 버튼으로 캐릭터를 추가하세요!"
                    }
            ) {
                HomeNullCard(modifier = Modifier.fillMaxSize().padding(16.dp))
            }
        }
        return
    }

    // --- 즐찾 캐릭터 하나라도 있는 경우
    Column(
        modifier = modifier.fillMaxWidth().padding(top = spacer)
            .semantics {
                isTraversalGroup = true
            },
        verticalArrangement = Arrangement.spacedBy(spacer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // 캐러셀
            LazyRow(
                state = listState,
                flingBehavior = rememberSnapFlingBehavior(listState),
                modifier = Modifier.fillMaxWidth().clearAndSetSemantics { }
            ) {
                items(Int.MAX_VALUE) { index ->     // 무한 스크롤
                    val character = favorites[index % n]

                    // 현재 아이템 정보
                    val itemInfo = listState.layoutInfo.visibleItemsInfo
                        .firstOrNull { it.index == index }

                    // 뷰포트 중앙
                    val viewportCenter = listState.layoutInfo.viewportStartOffset +
                            listState.layoutInfo.viewportSize.width / 2

                    val distance = itemInfo?.let {
                        val itemCenter = it.offset + it.size / 2
                        abs(itemCenter - viewportCenter).toFloat()
                    } ?: Float.MAX_VALUE

                    val maxDistance = (itemInfo?.size ?: 1) * 1.4f

                    val rawFraction = (distance / maxDistance).coerceIn(0f, 1f)
                    val fraction = rawFraction * rawFraction * rawFraction

                    val scale = 1.0f - fraction * 0.9f
                    val alpha = 1.4f - fraction * 1.3f
                    val isCenter = fraction < 0.25f

                    HomeFavoriteCard(
                        character = character,
                        index = (index % n) + 1,
                        modifier = Modifier
                            .fillMaxHeight(0.8f)    // 세로 크기 기준
                            .aspectRatio(3f / 4f)     // 가로:세로 = 3:4
                            .graphicsLayer {                // 중앙 강조 효과
                                scaleX = scale
                                scaleY = scale
                                this.alpha = alpha
                            }
                            .border(
                                width = 2.dp,
                                color = colorResource(id = R.color.main_orange),
                                shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .onGloballyPositioned { coords ->
                                if (isCenter && centerCard == null) {
                                    centerCard = coords.boundsInWindow()
                                }
                            },
                        isCenter = isCenter,
                        onClick = { c ->
                            c?.let { onCharacterClick(it.id) }
                        }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .aspectRatio(3f / 4f)
                    .focusRequester(focusRequester)
                    .focusable()
                    .semantics {
                        val character = favorites.getOrNull(centerIndex)

                        contentDescription = character?.let {
                            "현재 선택된 캐릭터 카드, ${it.name}, 전체 ${n}개 중 ${centerIndex + 1}번째"
                        } ?: "비어 있는 카드, 보관함에서 즐겨찾기 버튼으로 캐릭터를 추가할 수 있어요."

                        if (character != null) {
                            role = Role.Button
                        }
                    }
            )
        }

        // -- 현재 캐릭터 인덱스 표시 및 카드 이동 버튼
        Box(
            modifier = Modifier.weight(1f)
                .semantics {
                    traversalIndex = 1f
                    isTraversalGroup = true
                },
            contentAlignment = Alignment.Center
        ) {
            // 현재 캐릭터 인덱스
            Text(
                text = buildAnnotatedString {
                    // 현재 인덱스 부분 강조
                    withStyle(
                        style = spanTextStyle.toSpanStyle().copy(
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.main_orange)
                        )
                    ) {
                        append("${centerIndex + 1}")
                    }
                    // 구분자 + 전체 개수 부분 → 기본 스타일
                    append(" / $n")
                },
                style = textStyle.copy(
                    color = colorResource(R.color.main_orange)
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.clearAndSetSemantics { } // 장식용
            )

            // 카드 이동 버튼
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .semantics {
                        isTraversalGroup = true
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // ◀ 이전 버튼
                CircleIconButton(
                    icon = Icons.Rounded.ChevronLeft,
                    desc = "이전",
                    onClick = {
                        scope.launch {
                            val info = listState.layoutInfo
                            val viewportCenter =
                                info.viewportStartOffset + info.viewportSize.width / 2

                            val centerItem = info.visibleItemsInfo.minByOrNull { item ->
                                abs((item.offset + item.size / 2) - viewportCenter)
                            } ?: return@launch

                            val target = centerItem.index - 1
                            animateToCenter(listState, target)
                        }
                    },
                    modifier = Modifier.fillMaxHeight().semantics(true) {
                        contentDescription = "이전 즐겨찾기 캐릭터"
                        role = Role.Button
                    }
                )

                Spacer(Modifier.fillMaxSize(.4f))

                // ▶ 다음 버튼
                CircleIconButton(
                    icon = Icons.Rounded.ChevronRight,
                    desc = "다음",
                    onClick = {
                        scope.launch {
                            val info = listState.layoutInfo
                            val viewportCenter =
                                info.viewportStartOffset + info.viewportSize.width / 2

                            val centerItem = info.visibleItemsInfo.minByOrNull { item ->
                                abs((item.offset + item.size / 2) - viewportCenter)
                            } ?: return@launch

                            val target = centerItem.index + 1
                            animateToCenter(listState, target)
                        }
                    },
                    modifier = Modifier.fillMaxHeight()
                        .semantics(true) {
                            contentDescription = "다음 즐겨찾기 캐릭터"
                            role = Role.Button
                        }
                )
            }
        }
    }
}