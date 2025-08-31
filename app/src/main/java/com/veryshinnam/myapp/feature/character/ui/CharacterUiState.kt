package com.veryshinnam.myapp.feature.character.ui

data class CharacterUiModel(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val characterId: Long,
    val name: String,
    val gender: String,
    val age: Int,
    val imageUrl: String,
    val personality: String,
    val important: Boolean,
    val createTime: String,
    val storyId: Long,
    val storyTitle: String
)

sealed interface CharacterUiState {
    data object Loading : CharacterUiState
    data class Success(val data: CharacterUiModel) : CharacterUiState
    data class Error(val message: String) : CharacterUiState
}