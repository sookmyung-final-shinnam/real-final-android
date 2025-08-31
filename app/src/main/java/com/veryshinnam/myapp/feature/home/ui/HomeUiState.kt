package com.veryshinnam.myapp.feature.home.ui

import com.veryshinnam.myapp.feature.home.data.dto.FavoriteCharacter

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val data: HomeData) : HomeUiState
    data class Error(val message: String) : HomeUiState

    data class HomeData(
        val username: String,
        val points: Int,
        val myCharacters: Int,
        val favoriteCharacters: List<FavoriteCharacter>
    )
}

