package com.veryshinnam.myapp.feature.creation.data.dto

// 대화 세션 시작 응답 결과
data class StartResult(
    val sessionId: Long,
    val nextStory: String,
    val currentStep: String
)