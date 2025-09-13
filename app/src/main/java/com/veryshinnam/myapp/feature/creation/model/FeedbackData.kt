package com.veryshinnam.myapp.feature.creation.model

data class FeedbackData(
    val result: String,   // GOOD / NEEDS_CORRECTION
    val text: String,     // 피드백 메시지
    val tryNum: Int
)
