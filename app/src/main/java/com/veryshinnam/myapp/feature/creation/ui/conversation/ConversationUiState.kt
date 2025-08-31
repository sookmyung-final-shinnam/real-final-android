package com.veryshinnam.myapp.feature.creation.ui.conversation

data class ConversationUiState(
    val sessionId: Long? = null,
    val currentStep: String? = null,
    val nextStory: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)