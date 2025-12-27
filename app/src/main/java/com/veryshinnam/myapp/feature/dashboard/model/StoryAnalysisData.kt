package com.veryshinnam.myapp.feature.dashboard.model

data class StoryAnalysisData(
    val storyId: Long,
    val createdAt: String,
    val attempts: Map<Attempt, Int>,
    val avgAttemptPerStage: Double,
    val avgAnswerLength: Int,
    val emotions: List<ChartStatData>,
    val summary: String
)
