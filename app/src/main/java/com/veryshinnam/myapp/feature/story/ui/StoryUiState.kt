package com.veryshinnam.myapp.feature.story.ui

import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryData

sealed interface StoryUiState {
    data object Loading : StoryUiState
    data class Error(val message: String) : StoryUiState

    data class Success(
        val data: StoryData,        // 동화책 정보
        val pages: List<PageData>,  // 동화책 페이지 리스트
        val isSpeaking: Boolean = false  // tts 재생 여부
    ) : StoryUiState
}