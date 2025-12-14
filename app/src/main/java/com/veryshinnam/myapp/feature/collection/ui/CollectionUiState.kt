package com.veryshinnam.myapp.feature.collection.ui

import com.veryshinnam.myapp.common.model.Gender
import com.veryshinnam.myapp.feature.collection.model.CollectionData

sealed interface CollectionUiState {
    data object Loading : CollectionUiState
    data class Error(val message: String) : CollectionUiState

    data class Success(
        val selectedFilter: Gender = Gender.ALL,
        val collectionDataList: List<CollectionData>
    ) : CollectionUiState
}