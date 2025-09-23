package com.veryshinnam.myapp.feature.dashboard.ui

import com.veryshinnam.myapp.feature.dashboard.model.LanguageData
import com.veryshinnam.myapp.feature.dashboard.model.PlayData

sealed interface  DashboardUiState {
    data object Loading : DashboardUiState
    data class Error(val message: String) : DashboardUiState

    data class Success(
        val username: String,   // 유저 이름
        val interest: String, // 유저 관심사
        val playData: PlayData, // 활동 로그 데이터
        val languageData: LanguageData // 언어 발달 정도 데이터
    ) : DashboardUiState
}