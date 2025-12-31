package com.veryshinnam.myapp.feature.dashboard.data.dto

import com.veryshinnam.myapp.feature.dashboard.model.Attempt
import com.veryshinnam.myapp.feature.dashboard.model.DashboardData
import com.veryshinnam.myapp.feature.dashboard.model.Emotion
import com.veryshinnam.myapp.feature.dashboard.model.EmotionData
import com.veryshinnam.myapp.feature.dashboard.model.LanguageData
import com.veryshinnam.myapp.feature.dashboard.model.StatData

fun DashboardResult.toDashboardData(): DashboardData {
    return DashboardData(
        backgroundStats = backgroundStats.map { it.toStatData() },
        themeStats = themeStats.map { it.toStatData() },
        languageList = languageStats
            .take(5)    // 5개 제한
            .map { it.toLanguageData() },
        emotionList = emotionsStats
            .take(5)    // 5개 제한
            .map { it.toEmotionData() },
        parentAdvice = parentAdvice
    )
}

fun StatItemResult.toStatData(): StatData {
    return StatData(
        name = name,
        count = count
    )
}

fun LanguageItemResult.toLanguageData(): LanguageData {
    return LanguageData(
        storyId = storyId,
        createdAt = createdAt.substring(0, 10),
        attempts =  mapOf(
            Attempt.GI to attemptStats.giCount,
            Attempt.SEUNG to attemptStats.seungCount,
            Attempt.JEON to attemptStats.jeonCount,
            Attempt.GYEOL to attemptStats.gyeolCount
        ),
        avgAttemptPerStage = avgAttemptPerStage,
        avgAnswerLength = avgAnswerLength,
        newWords = newWords
    )
}

fun EmotionItemResult.toEmotionData(): EmotionData {
    return EmotionData(
        storyId = storyId,
        createdAt = createdAt.substring(0, 10),
        emotions = mapOf(
            Emotion.JOY to joy.toFloat(),
            Emotion.SADNESS to sadness.toFloat(),
            Emotion.ANGER to anger.toFloat(),
            Emotion.FEAR to fear.toFloat(),
            Emotion.SURPRISE to surprise.toFloat(),
            Emotion.NEUTRAL to neutral.toFloat()
        ),
        summary = summary
    )
}