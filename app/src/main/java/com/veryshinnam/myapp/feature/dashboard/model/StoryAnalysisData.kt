package com.veryshinnam.myapp.feature.dashboard.model

data class StoryAnalysisData(
    val storyId: Long,
    val storyTitle: String,
    val createdAt: String,
    val attempts: Map<Attempt, Int>,
    val avgAttemptPerStage: Double,
    val avgAnswerLength: Int,
    val newWords: List<String>,
    val emotions: List<ChartStatData>,
    val emotionText: String, // 대체 텍스스용
    val summary: String
)
