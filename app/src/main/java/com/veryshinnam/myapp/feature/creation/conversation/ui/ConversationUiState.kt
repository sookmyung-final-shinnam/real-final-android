package com.veryshinnam.myapp.feature.creation.conversation.ui

import com.veryshinnam.myapp.feature.creation.model.CurrentStep
import com.veryshinnam.myapp.feature.creation.model.FeedbackData
import com.veryshinnam.myapp.feature.creation.model.QuestionData

sealed interface ConversationUiState {
    data object Loading : ConversationUiState
    data class Error(val message: String) : ConversationUiState

    data class Success(
        val sessionId: Long,
        val nextStory: String,
        val currentStep: CurrentStep = CurrentStep.START, // 화면 분기처리용
        val loopStep: Int = 0,                      // 0=START, STEP_01 ~03
        val questionData: QuestionData? = null, // 다음 이야기+질문
        val feedbackData: FeedbackData? = null // 답변 피드백
    ) : ConversationUiState
}