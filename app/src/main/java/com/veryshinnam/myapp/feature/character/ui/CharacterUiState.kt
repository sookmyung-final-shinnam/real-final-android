package com.veryshinnam.myapp.feature.character.ui

import java.time.LocalDate

sealed interface CharacterUiState {
    data object Loading : CharacterUiState
    data class Success(val data: CharacterData) : CharacterUiState
    data class Error(val message: String) : CharacterUiState

    data class CharacterData(
        val characterId: Long,
        val name: String,
        val gender: String,
        val age: Int,
        val imageUrl: String,
        val personality: String,
        val important: Boolean, // 즐겨찾기
        val storyId: Long?,
        val storyTitle: String?,
        val videoId: Long?,
        val videoTitle: String?,
        val createdAt: String
    )
}