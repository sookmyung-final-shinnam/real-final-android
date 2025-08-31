package com.veryshinnam.myapp.feature.creation.ui.conversation

enum class ConversationPhase {
    IDLE,               // 초기
    FETCHING_QUESTION,  // 질문 로딩
    ASKING,             // 질문 읽기 완료(또는 직후)
    LISTENING,          // STT 인식 중
    SENDING_FEEDBACK,   // 피드백 요청 중
    FEEDBACK_SHOWN,     // 피드백 표시
    PENDING             // 서버가 PENDING 응답
}


data class ConversationUiState(
    val sessionId: Long? = null,
    val currentStep: String? = null,

    val messageId: Long? = null,
    val nextStory: String? = null,        // 내레이션
    val llmQuestion: String? = null,      // 질문

    val userAnswer: String? = null,       // STT 결과
    val partialAnswer: String? = null,
    val feedback: String? = null,         // 서버 피드백
    val sentiment: String? = null,        // "POSITIVE"/"NEGATIVE"

    val phase: ConversationPhase = ConversationPhase.IDLE,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)