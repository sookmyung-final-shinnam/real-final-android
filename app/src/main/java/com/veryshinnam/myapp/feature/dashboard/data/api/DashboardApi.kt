package com.veryshinnam.myapp.feature.dashboard.data.api

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.dashboard.data.dto.DashboardResult
import retrofit2.http.GET

interface DashboardApi {

    // 대시보드 조회 API
    @GET("/api/dashboard")
    suspend fun getDashboard(): BaseResponse<DashboardResult>

}