package com.veryshinnam.myapp.feature.creation.model

enum class ConversationStep {
    START,      // 대화 시작 단계
    STORY,      // 이야기 전개 단계
    QUESTION,   // 질문 단계
    ANSWER,     // 사용자 대답 단계
    FEEDBACK,   // 피드백 단계
    END         // 대화 종료
}