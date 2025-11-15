package com.veryshinnam.myapp.feature.dashboard.data

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.dashboard.model.DashboardResult
import retrofit2.http.GET

interface DashboardApi {

    // 대시보드 조회 API
    @GET("/api/dashboard")
    suspend fun getDashboard(): BaseResponse<DashboardResult>

}