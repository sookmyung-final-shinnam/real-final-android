package com.veryshinnam.myapp.feature.creation.data.dto

data class StartConversationRequest(
    val themeNames: List<String>,
    val backgroundName: String,
    val characterName: String,
    val characterAge: Int,
    val gender: String,
    val eyeColor: String,
    val hairColor: String,
    val hairStyle: String
)
