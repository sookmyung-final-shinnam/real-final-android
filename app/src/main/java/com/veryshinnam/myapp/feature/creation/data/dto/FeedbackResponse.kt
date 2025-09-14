package com.veryshinnam.myapp.feature.creation.data.dto

data class FeedbackResponse (
    val feedbackResult: String,   // "GOOD" or "NEEDS_CORRECTION"
    val feedbackText: String,
    val currentStep: String,      // "STEP_01", "STEP_02", ...
    val tryNum: Int
)
