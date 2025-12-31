package com.veryshinnam.myapp.feature.dashboard.model

data class DashboardData(
    val backgroundStats: List<StatData>,
    val themeStats: List<StatData>,
    val languageList: List<LanguageData>,
    val emotionList: List<EmotionData>,
    val parentAdvice: String
)