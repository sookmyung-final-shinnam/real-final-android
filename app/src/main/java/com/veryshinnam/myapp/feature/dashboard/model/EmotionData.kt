package com.veryshinnam.myapp.feature.dashboard.model

data class EmotionData(
    val storyId: Long,
    val createdAt: String,
    val emotions: Map<Emotion, Float>,
    val summary: String
)
