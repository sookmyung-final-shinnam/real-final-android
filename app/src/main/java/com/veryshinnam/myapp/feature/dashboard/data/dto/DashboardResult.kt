package com.veryshinnam.myapp.feature.dashboard.data.dto

data class DashboardResult(
    val username: String,
    val dashboardId: Long?,     // null 가능
    val maxTheme: String?,      // null 가능
    val maxBackground: String?, // null 가능
    val backgroundStats: List<StatItemResult>,
    val themeStats: List<StatItemResult>,
    val languageStats: List<LanguageItemResult>, // 최근 10개
    val emotionsStats: List<EmotionItemResult>,  // 최근 10개
    val parentAdvice: String?   // null 가능
)

data class StatItemResult(
    val name: String,
    val count: Int,
    val percent: Double
)

data class LanguageItemResult(
    val storyId: Long,
    val storyName: String,
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