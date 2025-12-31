package com.veryshinnam.myapp.feature.dashboard.data.repository

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.dashboard.data.api.DashboardApi
import com.veryshinnam.myapp.feature.dashboard.data.dto.DashboardResult
import com.veryshinnam.myapp.feature.dashboard.data.dto.toDashboardData
import com.veryshinnam.myapp.feature.dashboard.model.DashboardData
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val api: DashboardApi
): DashboardRepository {

    override suspend fun getDashboard(): DashboardData {
        val response: BaseResponse<DashboardResult> = api.getDashboard()

        if (!response.isSuccess || response.result == null) {
            throw Exception("대시보드 api 조회 실패: ${response.result}")
        }

        return response.result.toDashboardData()
    }
}