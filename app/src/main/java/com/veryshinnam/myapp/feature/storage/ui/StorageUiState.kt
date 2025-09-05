package com.veryshinnam.myapp.feature.storage.ui

import com.veryshinnam.myapp.feature.character.ui.CharacterUiState
import com.veryshinnam.myapp.feature.character.ui.CharacterUiState.CharacterData
import com.veryshinnam.myapp.feature.storage.enums.Filter

sealed interface StorageUiState {
    data object Loading : StorageUiState
    data class Success(
        val data: List<StorageData>,
        val selectedFilter: Filter = Filter.ALL
    ) : StorageUiState
    data class Error(val message: String) : StorageUiState

    data class StorageData(
        val cId: Long,          // 캐릭터 아이디
        val cName: String,      // 캐릭터 이름
        val cImageUrl: String,  // 캐릭터 이미지
        val isFavorite: Boolean // 즐찾
    )
}