package com.veryshinnam.myapp.feature.dashboard.data.dto

data class DashboardResult(
    val dashboardId: Long,
    val backgroundStats: List<StatItemResult>,
    val themeStats: List<StatItemResult>,
    val languageStats: List<LanguageItemResult>,
    val emotionsStats: List<EmotionItemResult>,
    val parentAdvice: String
)

data class StatItemResult(
    val name: String,
    val count: Int,
    val percent: Double
)

data class LanguageItemResult(
    val storyId: Long,
    val createdAt: String,
    val attemptStats: AttemptStats,
    val avgAttemptPerStage: Double,
    val avgAnswerLength: Int,
    val newWords: List<String>
)

data class AttemptStats(
    val giCount: Int,
    val seungCount: Int,
    val jeonCount: Int,
    val gyeolCount: Int
)

data class EmotionItemResult(
    val storyId: Long,
    val createdAt: String,
    val joy: Double,
    val sadness: Double,
    val anger: Double,
    val fear: Double,
    val surprise: Double,
    val neutral: Double,
    val summary: String
)