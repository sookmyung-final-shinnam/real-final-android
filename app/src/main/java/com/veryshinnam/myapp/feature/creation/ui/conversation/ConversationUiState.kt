package com.veryshinnam.myapp.feature.creation.ui.conversation

import com.veryshinnam.myapp.feature.creation.model.AnswerData
import com.veryshinnam.myapp.feature.creation.model.ConversationStep
import com.veryshinnam.myapp.feature.creation.model.FeedbackData
import com.veryshinnam.myapp.feature.creation.model.QuestionData

sealed interface ConversationUiState {
    data object Loading : ConversationUiState
    data class Error(val message: String) : ConversationUiState

    data class Success(
        val sessionId: Long = -1L,
        val nextStory: String = "",
        val questionData: QuestionData = QuestionData(  // 메시지 아이디 + 질문
            messageId = -1,
            question = ""
        ),
        val answerData: AnswerData = AnswerData(        // 응답 피드백
            userAnswer = "",
            partialAnswer = ""
        ),
        val feedbackData: FeedbackData = FeedbackData(  // 답변 피드백
            isPositive = false,
            text = "",
            tryNum = 0
        ),
        val conversationStep: ConversationStep = ConversationStep.START, // 화면 분기 처리용
        val loopStep: Int = 1 // STEP_01 ~ 03, END
    ) : ConversationUiState
}