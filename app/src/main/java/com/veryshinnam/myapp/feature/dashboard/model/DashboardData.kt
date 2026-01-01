package com.veryshinnam.myapp.feature.dashboard.model

data class DashboardData(
    val username: String,
    val maxTheme: String,
    val maxBackground: String,
    val backgroundStats: List<StatData>,
    val themeStats: List<StatData>,
    val languageList: List<LanguageData>,
    val emotionList: List<EmotionData>,
    val parentAdvice: String
)