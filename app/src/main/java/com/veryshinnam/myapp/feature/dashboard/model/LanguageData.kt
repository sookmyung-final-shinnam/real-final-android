package com.veryshinnam.myapp.feature.dashboard.model

data class LanguageData(
    val storyId: Long,
    val storyName: String,
    val createdAt: String,
    val attempts: Map<Attempt, Int>,
    val avgAttemptPerStage: Double,
    val avgAnswerLength: Int,
    val newWords: List<String>
)