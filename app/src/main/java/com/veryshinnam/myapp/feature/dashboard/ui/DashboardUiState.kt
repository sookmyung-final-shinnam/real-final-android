package com.veryshinnam.myapp.feature.dashboard.ui

import com.veryshinnam.myapp.feature.dashboard.data.dto.DashboardResult
import com.veryshinnam.myapp.feature.dashboard.model.ChartStatData
import com.veryshinnam.myapp.feature.dashboard.model.DashboardData
import com.veryshinnam.myapp.feature.dashboard.model.StatData
import com.veryshinnam.myapp.feature.dashboard.model.StoryAnalysisData
import com.veryshinnam.myapp.feature.dashboard.model.WordsData
import com.veryshinnam.myapp.feature.home.model.HomeData

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Error(val message: String) : DashboardUiState
    data class Success(
        val themeChart: List<ChartStatData>, // 테마용 차트 정보
        val themeList: List<StatData>,
        val backgroundChart: List<ChartStatData>, // 배경용 차트 정보
        val backgroundList: List<StatData>, // 배경용 차트 정보

        val storyAnalysis: List<StoryAnalysisData>, // 개별 스토리 분석 (시도 + 감정)
        val storyIndex: Int,
        val wordsAnalysis: List<WordsData>,          // 전체 스토리 분석 (언어 획득)

        val advice: String                           // 부모 조언
    ) : DashboardUiState
}