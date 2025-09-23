package com.veryshinnam.myapp.feature.creation.data.dto

// 응답 결과
data class FeedbackResult (
    val feedbackResult: String, // "GOOD" or "NEEDS_CORRECTION"
    val feedbackText: String,
    val currentStep: String,
    val tryNum: Int
)