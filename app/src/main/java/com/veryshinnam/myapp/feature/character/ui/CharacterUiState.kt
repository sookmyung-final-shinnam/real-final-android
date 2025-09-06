package com.veryshinnam.myapp.feature.character.ui

import com.veryshinnam.myapp.feature.character.model.CharacterData
import com.veryshinnam.myapp.feature.character.model.StoryData
import java.time.LocalDate

sealed interface CharacterUiState {
    data object Loading : CharacterUiState
    data class Success(
        val characterData: CharacterData,
        val storyData: StoryData
    ) : CharacterUiState
    data class Error(val message: String) : CharacterUiState
}