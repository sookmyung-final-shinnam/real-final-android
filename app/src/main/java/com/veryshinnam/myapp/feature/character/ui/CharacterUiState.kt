package com.veryshinnam.myapp.feature.character.ui

data class CharacterData(
    val characterId: Long,
    val name: String,
    val gender: String,
    val age: Int,
    val imageUrl: String,
    val personality: String,
    val important: Boolean,
    val storyId: Long,
    val storyTitle: String
)

sealed interface CharacterUiState {
    data object Loading : CharacterUiState
    data class Success(val data: CharacterData) : CharacterUiState
    data class Error(val message: String) : CharacterUiState
}