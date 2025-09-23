package com.veryshinnam.myapp.feature.creation.ui.conversation

import com.veryshinnam.myapp.feature.creation.model.AnswerData
import com.veryshinnam.myapp.feature.creation.model.ConversationStep
import com.veryshinnam.myapp.feature.creation.model.FeedbackData
import com.veryshinnam.myapp.feature.creation.model.QuestionData

sealed interface ConversationUiState {
    data object Loading : ConversationUiState
    data class Error(val message: String) : ConversationUiState

    data class Success(
        val sessionId: Long,
        val nextStory: String,
        val questionData: QuestionData,
        val answerData: AnswerData,
        val feedbackData: FeedbackData,
        val conversationStep: ConversationStep,
        val loopStep: Int = 1
    ) : ConversationUiState
}