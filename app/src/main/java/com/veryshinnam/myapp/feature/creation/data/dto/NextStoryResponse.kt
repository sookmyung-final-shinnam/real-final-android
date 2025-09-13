package com.veryshinnam.myapp.feature.creation.data.dto

data class NextStoryResponse (
    val messageId: Long,
    val nextStory: String,
    val llmQuestion: String
)
