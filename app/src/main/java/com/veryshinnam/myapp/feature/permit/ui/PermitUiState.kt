package com.veryshinnam.myapp.feature.permit.ui

sealed interface PermitUiState {
    object Idle : PermitUiState // 스플래시
    object Loading : PermitUiState
    object Success : PermitUiState
    data class Error(val message: String) : PermitUiState
}