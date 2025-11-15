package com.veryshinnam.myapp.feature.dashboard.data

import com.veryshinnam.myapp.feature.dashboard.model.DashboardResponse
import retrofit2.http.GET

interface DashboardApi {

    // 대시보드 조회 API
    @GET("/api/dashboard")
    suspend fun getDashboard(): DashboardResponse

}