package com.veryshinnam.myapp.feature.collection.ui

import com.veryshinnam.myapp.feature.collection.model.Filter

sealed interface CollectionUiState {
    data object Loading : CollectionUiState
    data class Success(
        val selectedFilter: Filter = Filter.ALL,
        val data: List<StorageData>
    ) : CollectionUiState
    data class Error(val message: String) : CollectionUiState

    data class StorageData(
        val characterId: Long,       // 캐릭터 아이디
        val characterName: String,   // 캐릭터 이름
        val characterImage: String?, // 캐릭터 이미지
        val isFavorite: Boolean      // 캐릭터 즐찾 여부
    )
}