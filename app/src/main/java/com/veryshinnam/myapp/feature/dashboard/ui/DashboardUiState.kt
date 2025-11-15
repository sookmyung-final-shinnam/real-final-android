package com.veryshinnam.myapp.feature.dashboard.ui

import com.veryshinnam.myapp.feature.dashboard.model.DashboardResult

data class DashboardUiState(
    val isLoading: Boolean = true,
    val data: DashboardResult? = null,
    val errorMessage: String? = null
)