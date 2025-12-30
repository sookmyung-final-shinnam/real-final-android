package com.veryshinnam.myapp.feature.dashboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.common.model.DashboardInit
import com.veryshinnam.myapp.feature.dashboard.data.repository.DashboardRepository
import com.veryshinnam.myapp.feature.dashboard.model.ChartStatData
import com.veryshinnam.myapp.feature.dashboard.model.Emotion
import com.veryshinnam.myapp.feature.dashboard.model.EmotionData
import com.veryshinnam.myapp.feature.dashboard.model.LanguageData
import com.veryshinnam.myapp.feature.dashboard.model.StatData
import com.veryshinnam.myapp.feature.dashboard.model.StoryAnalysisData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    // 화면 전체 ui 상태
    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState = _uiState.asStateFlow()

    // 유저 닉네임
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    init {
        fetchDashboard()
    }

    fun fetchDashboard() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading

            try {
                // api 호출
                val dashboard = repository.getDashboard()

                // 테마용 차트 정보 변환
                val themeChart =  groupByCustom(
                    stats = dashboard.themeStats,
                    initList = DashboardInit.THEMES,
                    customLabel = DashboardInit.CUSTOM)

                // 배경용 차트 정보 변환
                val backgroundChart = groupByCustom(
                    stats = dashboard.backgroundStats,
                    initList = DashboardInit.BACKGROUNDS,
                    customLabel = DashboardInit.CUSTOM
                )

                // 개별 스토리 분석
                val storyAnalysis = toStoryAnalysisData(
                    dashboard.emotionList,
                    dashboard.languageList
                )

                _uiState.value = DashboardUiState.Success(
                    themeChart = toChartStats(themeChart),
                    themeList = dashboard.themeStats,
                    backgroundChart = toChartStats(backgroundChart),
                    backgroundList = dashboard.backgroundStats,

                    storyAnalysis = storyAnalysis,
                    storyIndex = 0,

                    advice = dashboard.parentAdvice
                )

            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error("대시보드 불러오기 실패: ${e.message}")
            }
        }
    }

    // 직접 추가로 묶기
    fun groupByCustom(
        stats: List<StatData>,
        initList: List<String>,
        customLabel: String
    ): List<StatData> {

        val result = mutableListOf<StatData>()
        var customCount = 0

        for (stat in stats) {
            if (stat.name in initList) {
                result.add(stat)
            } else {
                customCount += stat.count
            }
        }

        if (customCount > 0) {
            result.add(StatData(customLabel, customCount))
        }

        return result
    }

    // 차트 그리기 위한
    fun toChartStats(stats: List<StatData>): List<ChartStatData> {
        val total = stats.sumOf { it.count } // cnt 모두 합치기 (int)
            .toFloat()                       // int > float 변환
            .coerceAtLeast(1f) // 1.0f 보장

        return stats.map {
            ChartStatData(
                name = it.name,
                ratio = it.count / total
            )
        }
    }

    fun toStoryAnalysisData(
        emotionData: List<EmotionData>,
        languageData: List<LanguageData>
    ): List<StoryAnalysisData> {

        val emotionMap = emotionData.associateBy { it.storyId }

        return languageData.map { language ->
            val emotionData = emotionMap[language.storyId]

            val chartStats: List<ChartStatData> =
                // Emotion 순서대로 값 채우기
                Emotion.values().map { emotion ->
                    ChartStatData(
                        name = emotion.type,
                        ratio = emotionData?.emotions?.get(emotion) ?: 0f
                    )
                }

            StoryAnalysisData(
                storyId = language.storyId,
                createdAt = language.createdAt,
                attempts = language.attempts,
                avgAttemptPerStage = language.avgAttemptPerStage,
                avgAnswerLength = language.avgAnswerLength,
                newWords = language.newWords,
                emotions = chartStats,
                summary = emotionData?.summary.orEmpty()
            )
        }
    }

    // 다음 스토리 분석 이동
    fun nextStory() {
        val state = _uiState.value
        if (state is DashboardUiState.Success) {
            val size = state.storyAnalysis.size
            if (size == 0) return

            _uiState.value = state.copy(
                storyIndex = (state.storyIndex + 1) % size
            )
        }
    }

    // 이전 스토리 분석 이동
    fun prevStory() {
        val state = _uiState.value
        if (state is DashboardUiState.Success) {
            val size = state.storyAnalysis.size
            if (size == 0) return

            _uiState.value = state.copy(
                storyIndex = (state.storyIndex - 1 + size) % size
            )
        }
    }
}