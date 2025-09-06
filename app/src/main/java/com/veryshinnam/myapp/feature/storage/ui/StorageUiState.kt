package com.veryshinnam.myapp.feature.storage.ui

import com.veryshinnam.myapp.feature.storage.enums.Filter

sealed interface StorageUiState {
    data object Loading : StorageUiState
    data class Success(
        val selectedFilter: Filter = Filter.ALL,
        val data: List<StorageData>
    ) : StorageUiState
    data class Error(val message: String) : StorageUiState

    data class StorageData(
        val characterId: Long,       // 캐릭터 아이디
        val characterName: String,   // 캐릭터 이름
        val characterImage: String?, // 캐릭터 이미지
        val isFavorite: Boolean      // 캐릭터 즐찾 여부
    )
}