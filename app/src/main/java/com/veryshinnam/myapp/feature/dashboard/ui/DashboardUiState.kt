package com.veryshinnam.myapp.feature.dashboard.ui

import com.veryshinnam.myapp.feature.dashboard.data.dto.DashboardResult
import com.veryshinnam.myapp.feature.dashboard.model.ChartStatData
import com.veryshinnam.myapp.feature.dashboard.model.DashboardData
import com.veryshinnam.myapp.feature.home.model.HomeData

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Error(val message: String) : DashboardUiState
    data class Success(
        val dashboardData: DashboardData, // 대시보드 정보
        val themeChart: List<ChartStatData>, // 테마용 차트 정보
        val backgroundChart: List<ChartStatData> // 배경용 차트 정보
    ) : DashboardUiState
}