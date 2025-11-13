package com.veryshinnam.myapp.feature.character.ui

import com.veryshinnam.myapp.feature.character.model.CharacterData

sealed interface CharacterUiState {
    data object Loading : CharacterUiState
    data class Error(val message: String) : CharacterUiState

    data class Success(val characterData: CharacterData) : CharacterUiState
}