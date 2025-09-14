package com.veryshinnam.myapp.feature.dashboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.dashboard.model.LanguageData
import com.veryshinnam.myapp.feature.dashboard.model.PlayData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(

) : ViewModel() {
    private val _dashboardUiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val dashBoardUiState: StateFlow<DashboardUiState> = _dashboardUiState

    init {
        loadDashboardData()
    }

    // 더미
    fun loadDashboardData() {
        viewModelScope.launch {
            val username = "짱신남"
            val interest = "공룡"

            val dummyPlayData = PlayData(
                recentPlayTime = "3.1시간",
                mostSelectedTheme = "판타지",
                mostSelectedBackground = "바다",
                storyReplayCount = "2.5회"
            )

            val dummyLanguageData = LanguageData(
                recentSpokenWord = "밥",
                recentNewWord = "오비이락",
                recentSlangLevel = "24회",
                contextUnderstanding = "80%"
            )

            _dashboardUiState.value = DashboardUiState.Success(
                username = username,
                interest = interest,
                playData = dummyPlayData,
                languageData = dummyLanguageData
            )
        }
    }
}