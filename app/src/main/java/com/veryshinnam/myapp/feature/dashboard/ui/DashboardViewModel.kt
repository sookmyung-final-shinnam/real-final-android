package com.veryshinnam.myapp.feature.dashboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.common.model.DashboardInit
import com.veryshinnam.myapp.feature.dashboard.data.repository.DashboardRepository
import com.veryshinnam.myapp.feature.dashboard.model.ChartStatData
import com.veryshinnam.myapp.feature.dashboard.model.StatData
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
                    stats = dashboard.backgroundStats,
                    initList = DashboardInit.THEMES,
                    customLabel = DashboardInit.CUSTOM)

                // 배경용 차트 정보 변환
                val backgroundChart = groupByCustom(
                        stats = dashboard.backgroundStats,
                        initList = DashboardInit.BACKGROUNDS,
                        customLabel = DashboardInit.CUSTOM
                    )

                _uiState.value = DashboardUiState.Success(
                    dashboardData = dashboard,
                    themeChart = toChartStats(themeChart),
                    backgroundChart = toChartStats(backgroundChart)
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
}