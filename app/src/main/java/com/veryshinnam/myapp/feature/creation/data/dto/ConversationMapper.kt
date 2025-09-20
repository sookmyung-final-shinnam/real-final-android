package com.veryshinnam.myapp.feature.creation.data.dto

import com.veryshinnam.myapp.feature.creation.model.FeedbackData
import com.veryshinnam.myapp.feature.creation.model.SelectionData

fun SelectionData.toStartRequest(): StartRequest {
    return StartRequest(
        themeNames = themes,
        backgroundName = background,
        characterName = name,
        characterAge = age,
        gender = gender,
        eyeColor = eyeColor,
        hairColor = hairColor,
        hairStyle = hairStyle
    )
}

fun FeedbackResult.toFeedbackData(): FeedbackData {
    return FeedbackData(
        isPositive = (feedbackResult == "GOOD"),
        text = feedbackText,
        tryNum = tryNum
    )
}