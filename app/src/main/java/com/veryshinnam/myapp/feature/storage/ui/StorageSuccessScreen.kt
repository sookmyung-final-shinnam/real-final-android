package com.veryshinnam.myapp.feature.storage.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.storage.enums.CharacterFilter
import com.veryshinnam.myapp.feature.storage.enums.Filter
import com.veryshinnam.myapp.feature.storage.enums.StoryFilter
import com.veryshinnam.myapp.feature.storage.enums.Tab

@Composable
fun StorageSuccessScreen(
    data: List<StorageUiState.StorageData>,
    selectedTab: Tab,
    selectedFilter: Filter,
    onTabClick: (Tab) -> Unit,
    onFilterClick: (Filter) -> Unit,
    onFavoriteClick: (Long) -> Unit,
    onItemClick: (Long) -> Unit
) {
    Column  {
        // 탭 바
        Row {
            Tab.values().forEach { tab ->
                Text(
                    text = tab.name,
                    modifier = Modifier
                        .clickable { onTabClick(tab) }
                        .padding(8.dp),
                    fontWeight = if (tab == selectedTab) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        // 필터 바
        Row {
            val filters = when (selectedTab) {
                Tab.CHARACTER -> listOf(Filter.ALL) + CharacterFilter.values()
                Tab.STORY -> listOf(Filter.ALL) + StoryFilter.values()
            }
            filters.forEach { filter ->
                Text(
                    text = when (filter) {
                        Filter.ALL -> "전체"
                        is CharacterFilter -> when (filter) {
                            CharacterFilter.MALE -> "남자"
                            CharacterFilter.FEMALE -> "여자"
                        }
                        is StoryFilter -> when (filter) {
                            StoryFilter.STORY -> "동화"
                            StoryFilter.VIDEO -> "영상"
                        }
                    },
                    modifier = Modifier
                        .clickable { onFilterClick(filter) }
                        .padding(8.dp),
                    fontWeight = if (filter == selectedFilter) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(8.dp)
        ) {
            items(data) { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(4.dp)
                ) {
                    // 이미지 클릭 -> 상세
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = item.title,
                        modifier = Modifier
                            .size(100.dp)
                            .clickable { onItemClick(item.id) } // 이미지 클릭 시
                    )

                    // 즐겨찾기 버튼 클릭 -> 토글
                    IconButton(
                        onClick = { onFavoriteClick(item.id) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        val iconRes =
                            if (item.isFavorite) R.drawable.img_star_on else R.drawable.img_star_off
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = if (item.isFavorite) "즐겨찾기 해제" else "즐겨찾기"
                        )
                    }

                    // 제목 클릭 -> 상세
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable { onItemClick(item.id) } // 제목 클릭 시 상세
                    )
                }
            }
        }
    }
}