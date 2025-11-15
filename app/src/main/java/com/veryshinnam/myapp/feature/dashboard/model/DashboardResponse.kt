package com.veryshinnam.myapp.feature.dashboard.model

data class DashboardResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: DashboardResult?
)

data class DashboardResult(
    val dashboardId: Long,
    val backgroundStats: List<StatItem>,
    val themeStats: List<StatItem>,
    val languageStats: List<LanguageItem>,
    val emotionsStats: List<EmotionItem>,
    val parentAdvice: String
)

data class StatItem(
    val name: String,
    val count: Int,
    val percent: Double
)

data class LanguageItem(
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

data class EmotionItem(
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