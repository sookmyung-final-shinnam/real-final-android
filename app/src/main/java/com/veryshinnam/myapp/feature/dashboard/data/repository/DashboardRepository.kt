package com.veryshinnam.myapp.feature.dashboard.data.repository

import com.veryshinnam.myapp.feature.dashboard.model.DashboardData

interface DashboardRepository {

    // 대시보드 조회
    suspend fun getDashboard(): DashboardData
}