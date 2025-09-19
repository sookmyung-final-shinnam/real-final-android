package com.veryshinnam.myapp.feature.collection.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.feature.collection.model.CollectionData
import com.veryshinnam.myapp.common.enums.Gender
import com.veryshinnam.myapp.feature.collection.ui.component.FilterButtons
import com.veryshinnam.myapp.feature.collection.ui.component.CollectionCharacterGrid
import com.veryshinnam.myapp.feature.collection.ui.component.CollectionInfo

@Composable
fun CollectionCharactersScreen(
    data: List<CollectionData>,
    selectedFilter: Gender,
    onFilterClick: (Gender) -> Unit,
    onFavoriteClick: (Long) -> Unit,
    onItemClick: (Long) -> Unit
) {
    val sectionSpacing = 16.dp // 컴포저블 사이 여백

    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()
            .padding(horizontal = sectionSpacing)
        ) {
            // 보관함 상단
            CollectionInfo(
                characters = data.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.25f)    // 높이 비율 0.25
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.75f) // 높이 비율 0.75
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
                CollectionCharacterGrid(
                    data = data,
                    onFavoriteClick = onFavoriteClick,
                    onItemClick = onItemClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}