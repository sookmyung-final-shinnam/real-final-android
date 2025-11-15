package com.veryshinnam.myapp.feature.dashboard.data

import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val api: DashboardApi
) {
    suspend fun fetchDashboard() = api.getDashboard()
}