package com.veryshinnam.myapp.feature.creation.data.dto

// 다음 스텝에서 메시지 조회 응답 결과
data class NextStepResult (
    val messageId: Long,
    val nextStory: String,
    val llmQuestion: String
)
