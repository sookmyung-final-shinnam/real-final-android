package com.veryshinnam.myapp.feature.dashboard.model

data class WordsData(
    val storyId: Long,
    val createdAt: String,
    val newWords: List<String>,
    val count: Int
)