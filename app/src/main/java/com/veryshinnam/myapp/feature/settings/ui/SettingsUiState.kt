package com.veryshinnam.myapp.feature.settings.ui

sealed interface SettingsUiState {
    object Loading : SettingsUiState
    object Success : SettingsUiState
    data class Error(val message: String) : SettingsUiState
}
