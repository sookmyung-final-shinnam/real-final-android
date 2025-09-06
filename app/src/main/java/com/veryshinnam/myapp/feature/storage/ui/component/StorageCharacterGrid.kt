package com.veryshinnam.myapp.feature.storage.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.storage.ui.StorageUiState

@Composable
fun StorageCharacterGrid(
    data: List<StorageUiState.StorageData>,
    onFavoriteClick: (Long) -> Unit,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyGridState = rememberLazyGridState()
) {

    // 스크롤이 맨 위가 아닌지 체크
    // 스크롤 시작 시 페이드 효과 시작
    val showTopFade by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0
        }
    }

    Box(modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = listState,
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(data) { item ->
                    StorageCharacterItem(
                        cId = item.characterId,
                        cName = item.characterName,
                        cImage = item.characterImage,
                        isFavorite = item.isFavorite,
                        onFavoriteClick = onFavoriteClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3f / 4f)
                            .clickable { onItemClick(item.characterId) }
                    )
                }
            }
        )

        if (showTopFade) {
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