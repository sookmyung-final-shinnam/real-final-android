package com.veryshinnam.myapp.feature.dashboard.ui

import com.veryshinnam.myapp.feature.dashboard.model.ChartStatData
import com.veryshinnam.myapp.feature.dashboard.model.StatData
import com.veryshinnam.myapp.feature.dashboard.model.StoryAnalysisData

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Error(val message: String) : DashboardUiState
    data class Success(
        val username: String,       // 유저 닉네임
        val maxTheme: String,       // 최대 관심 테마
        val maxBackground: String,  // 최대 관심 배경

        val themeChart: List<ChartStatData>,        // 테마용 차트 비율 재계산
        val themeChartText: String,                 // 테마용 차트 비율 대체 텍스트 용
        val themeList: List<StatData>,              // 모든 테마 리스트
        val backgroundChart: List<ChartStatData>,   // 배경용 차트 비율 재계산
        val backgroundChartText: String,            // 배경용 차트 비율 대체 텍스트 용
        val backgroundList: List<StatData>,         // 모든 배경 리스트

        val storyAnalysis: List<StoryAnalysisData>, // 개별 스토리 분석 리스트 (시도 + 감정)
        val storyIndex: Int,                        // 분석 리스트 인덱스

        val advice: String                           // 부모 조언
    ) : DashboardUiState
}