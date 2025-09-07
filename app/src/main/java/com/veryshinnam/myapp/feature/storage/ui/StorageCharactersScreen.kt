package com.veryshinnam.myapp.feature.storage.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.feature.storage.enums.Filter
import com.veryshinnam.myapp.feature.storage.ui.component.FilterButtons
import com.veryshinnam.myapp.feature.storage.ui.component.StorageCharacterGrid
import com.veryshinnam.myapp.feature.storage.ui.component.StorageInfo

@Composable
fun StorageCharactersScreen(
    data: List<StorageUiState.StorageData>,
    selectedFilter: Filter,
    onFilterClick: (Filter) -> Unit,
    onFavoriteClick: (Long) -> Unit,
    onItemClick: (Long) -> Unit
) {
    val sectionSpacing = 16.dp // 컴포저블 사이 여백

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = sectionSpacing)
    ) {
        // 보관함 상단
        StorageInfo(
            modifier = Modifier
            .fillMaxWidth()
            .weight(0.25f)    // 높이 비율 0.2
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.75f) // 높이 비율 0.8
        ) {
            // 캐릭터 성별 필터
            Spacer(modifier = Modifier.height(sectionSpacing))
            FilterButtons(
                selectedFilter = selectedFilter,
                onFilterClick = onFilterClick,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
            )
            Spacer(modifier = Modifier.height(sectionSpacing))

            // 캐릭터 아이템 그리드 (3*3)
            StorageCharacterGrid(
                data = data,
                onFavoriteClick = onFavoriteClick,
                onItemClick = onItemClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}