package com.veryshinnam.myapp.feature.story.ui

import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryData

sealed interface StoryUiState {
    data object Loading : StoryUiState
    data class Error(val message: String) : StoryUiState

    data class Success(
        val storyData: StoryData,       // 동화책 정보
        val pagesData: List<PageData>,  // 동화책 페이지 리스트
        val isPrologue: Boolean = true, // 동화 진행 단계
        val isSpeaking: Boolean = true, // tts 재생 여부
    ) : StoryUiState
}