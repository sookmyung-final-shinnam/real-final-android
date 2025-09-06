package com.veryshinnam.myapp.feature.story.ui

sealed interface StoryUiState {
    data object Loading : StoryUiState
    data class Error(val message: String) : StoryUiState

    data class Success(
        val data: StoryData,        // 동화책 정보
        val pages: List<StoryPage>, // 동화책 페이지 리스트
        val isSpeaking: Boolean = false  // tts 재생 여부
    ) : StoryUiState

    data class StoryData(
        val title: String,
        val imageUrl: String,   // 표지
        val description: String // 줄거리
    )

    data class StoryPage(
        val pageId: Long,
        val imageUrl: String? = null,
        val content: String = ""
    )
}