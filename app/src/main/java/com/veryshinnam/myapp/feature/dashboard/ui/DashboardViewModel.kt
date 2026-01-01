package com.veryshinnam.myapp.feature.dashboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.common.model.DashboardInit
import com.veryshinnam.myapp.common.model.Gender
import com.veryshinnam.myapp.common.model.ManualData
import com.veryshinnam.myapp.common.model.ManualTarget
import com.veryshinnam.myapp.core.manual.ManualManager
import com.veryshinnam.myapp.feature.collection.ui.CollectionUiState
import com.veryshinnam.myapp.feature.dashboard.data.repository.DashboardRepository
import com.veryshinnam.myapp.feature.dashboard.model.Attempt
import com.veryshinnam.myapp.feature.dashboard.model.ChartStatData
import com.veryshinnam.myapp.feature.dashboard.model.Emotion
import com.veryshinnam.myapp.feature.dashboard.model.EmotionData
import com.veryshinnam.myapp.feature.dashboard.model.LanguageData
import com.veryshinnam.myapp.feature.dashboard.model.StatData
import com.veryshinnam.myapp.feature.dashboard.model.StoryAnalysisData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository,
    private val manualManager: ManualManager
) : ViewModel() {

    // 화면 전체 ui 상태
    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState = _uiState.asStateFlow()

    // 유저 닉네임
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    // 분석 리스트 비어 있는지
    private val _isEmpty = MutableStateFlow(false)
    val isEmpty: StateFlow<Boolean> = _isEmpty

    // ManualManager 구독
    val manualState = manualManager.state
    val manualMessage = manualManager.message

    // 매뉴얼 진행 단계 상태
    private val _manualStep = MutableStateFlow(0)
    val manualStep = _manualStep.asStateFlow()

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
                    username = dashboard.username,
                    maxTheme = dashboard.maxTheme,
                    maxBackground = dashboard.maxBackground,
                    themeChart = toChartStats(themeChart),
                    themeList = dashboard.themeStats,
                    backgroundChart = toChartStats(backgroundChart),
                    backgroundList = dashboard.backgroundStats,

                    storyAnalysis = storyAnalysis,
                    storyIndex = 0,

                    advice = dashboard.parentAdvice
                )

                // 유저 닉네임 저장
                _username.value = dashboard.username

                // 빈 리스트 여부 확인
                _isEmpty.value = storyAnalysis.isEmpty()
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
                storyTitle = language.storyName,
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

    // --- 매뉴얼 관련 ---
    // 홈 화면 사용 매뉴얼
    val manuals = listOf(
        ManualData("와~ 이제 마지막 설명이에요!\n여기는 대시보드예요", ManualTarget.NONE),
        ManualData("여기에서는 좋아했던 테마와 배경이 기록되고,", ManualTarget.NONE),
        ManualData("동화를 만들며 도전한 횟수와 그때의 기분도 남아요.", ManualTarget.NONE),
        ManualData("이건 미리 보여주는 화면이에요.\n앞으로 ${username}가 만든 동화로 바뀔 거예요!", ManualTarget.NONE),
        ManualData("이 버튼들을 누르면 더 자세한 설명을 볼 수 있으니,\n궁금할 때 언제든 눌러보세요~", ManualTarget.NONE),
    )

    // 매뉴얼용 더미 더미테이터
    val dummyTCharts = listOf(
        ChartStatData(name = "일상", ratio = 0.27f),
        ChartStatData(name = "사랑", ratio = 0.20f),
        ChartStatData(name = "우정", ratio = 0.13f),
        ChartStatData(name = "추리", ratio = 0.13f),
        ChartStatData(name = "직접 추가", ratio = 0.27f)
    )
    val dummyTStats = listOf(
        StatData(name = "일상", count = 4),
        StatData(name = "사랑", count = 3),
        StatData(name = "우정", count = 2),
        StatData(name = "추리", count = 2),
        StatData(name = "가족", count = 2),
        StatData(name = "자유", count = 2)
    )
    val dummyBCharts = listOf(
        ChartStatData(name = "숲 속", ratio = 0.57f),
        ChartStatData(name = "바다", ratio = 0.29f),
        ChartStatData(name = "직접 추가", ratio = 0.14f)
    )
    val dummyBStats = listOf(
        StatData(name = "숲 속", count = 4),
        StatData(name = "바다", count = 2),
        StatData(name = "사막", count = 1)
    )
    val dummyStory = listOf(
        StoryAnalysisData(
            storyId = -1L,
            storyTitle = "비밀의 학교와 친구들",
            createdAt = "2025-01-01",
            attempts = mapOf(
                Attempt.GI to 2,
                Attempt.SEUNG to 1,
                Attempt.JEON to 3,
                Attempt.GYEOL to 1
            ),
            avgAttemptPerStage = 1.75,
            avgAnswerLength = 18,
            newWords = listOf("용감한", "탐험", "숲속"),
            emotions = listOf(
                ChartStatData("기쁨", 0.15f),
                ChartStatData("슬픔", 0.10f),
                ChartStatData("화남", 0.12f),
                ChartStatData("두려움", 0.18f),
                ChartStatData("놀람", 0.30f),
                ChartStatData("평온", 0.15f),
            ),
            summary = ""
        ),
    )

    fun startManual() {
        _manualStep.value = 0
        manualManager.update(manuals[0].message)

        _uiState.value = DashboardUiState.Success(
            username = "더미",
            maxTheme = "",
            maxBackground = "",
            themeChart = dummyTCharts,
            themeList = dummyTStats,
            backgroundChart = dummyBCharts,
            backgroundList = dummyBStats,

            storyAnalysis = dummyStory,
            storyIndex = 0,

            advice = ""
        )
    }

    fun nextManual() {
        val current = _manualStep.value

        if (current < manuals.lastIndex) {
            val next = current + 1
            _manualStep.value = next
            manualManager.update(manuals[next].message)
        } else if (current == manuals.lastIndex) {
            _manualStep.value = manuals.size
        }
    }

    fun stopManual() = manualManager.stop()

    fun hideManual() = manualManager.clear()
}