package com.veryshinnam.myapp.feature.creation.model

data class FeedbackData(
    val isPositive: Boolean, // 피드백 결과 긍정 여부
    val text: String,        // 피드백 메시지
    val tryNum: Int          // 피드백 시도
)
