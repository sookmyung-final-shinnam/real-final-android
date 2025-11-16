package com.veryshinnam.myapp.feature.creation.model

data class ManualScriptData (
    val step: ConversationStep,
    val nextStory: String = "",
    val question: String = "",
    val feedback: FeedbackData = FeedbackData(false, "", 0),
)