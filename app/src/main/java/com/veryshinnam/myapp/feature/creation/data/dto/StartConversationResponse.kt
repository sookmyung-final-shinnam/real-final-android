package com.veryshinnam.myapp.feature.creation.data.dto

// 대화 시작 (동화 생성)
data class StartConversationResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: StartConversationResult // 대화 시작 상세 응답
)

// 대화 시작 상세 응답
data class StartConversationResult(
    val sessionId: Long,
    val nextStory: String,
    val currentStep: String
)