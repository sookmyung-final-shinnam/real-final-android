package com.veryshinnam.myapp.feature.storage.ui

import com.veryshinnam.myapp.feature.character.ui.CharacterUiState
import com.veryshinnam.myapp.feature.character.ui.CharacterUiState.CharacterData
import com.veryshinnam.myapp.feature.storage.enums.Filter
import com.veryshinnam.myapp.feature.storage.enums.Tab

sealed interface StorageUiState {
    data object Loading : StorageUiState
    data class Success(
        val data: List<StorageData>,
        val selectedTab: Tab = Tab.CHARACTER,
        val selectedFilter: Filter = Filter.ALL
    ) : StorageUiState
    data class Error(val message: String) : StorageUiState

    data class StorageData(
        val id: Long,
        val title: String,
        val imageUrl: String,
        val isFavorite: Boolean,
        val isOpen: Boolean // 잠금 여부
    )
}