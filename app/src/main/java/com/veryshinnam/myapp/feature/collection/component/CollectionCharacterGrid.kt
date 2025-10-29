package com.veryshinnam.myapp.feature.collection.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.collection.model.CollectionData

@Composable
fun CollectionCharacterGrid(
    data: List<CollectionData>,
    onFavoriteClick: (Long) -> Unit,
    onItemClick: (CollectionData) -> Unit,
    cellPadding: Dp = 6.dp,
    modifier: Modifier,
    listState: LazyGridState = rememberLazyGridState()
) {

    // 스크롤이 맨 위가 아닌지 체크
    // 스크롤 시작 시 페이드 효과 시작
    val isTop by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
        }
    }

    Box(modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = listState,
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(cellPadding),
            horizontalArrangement = Arrangement.spacedBy(cellPadding),
            content = {
                itemsIndexed(data) { index, item ->

                    // 마지막 행에 바닥 여백 줌
                    val isLast = isLastRow(index, data.size, 3)

                    CollectionCharacterItem(
                        cId = item.id,
                        cName = item.name,
                        cImage = item.image,
                        isFavorite = item.isFavorite,
                        onFavoriteClick = onFavoriteClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3f / 4f)
                            .clickable { onItemClick(item) }
                    )
                }
            }
        )

        if (!isTop) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.08f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(R.color.background_yellow),
                                Color.Transparent
                            )
                        )
                    )
                    .align(Alignment.TopCenter)
            )
        }
    }
}

// index가 마지막 행의 아이템인지 체크하는 함수
fun isLastRow(
    index: Int,      // 현재 아이템 index
    totalItems: Int, // 전체 아이템 개수
    columns: Int     // 한 행에 들어가는 열 수 
): Boolean {
    val totalRows = (totalItems + columns - 1) / columns // 전체 행(= 마지막 행) 수 계산
    val currentRow = index / columns    // 현재 행수
    return currentRow == totalRows - 1  // 0부터 시작 고려
}