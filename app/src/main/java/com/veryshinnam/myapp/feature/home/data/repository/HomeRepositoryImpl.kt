package com.veryshinnam.myapp.feature.home.data.repository

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.home.data.api.HomeApi
import com.veryshinnam.myapp.feature.home.data.dto.HomeResult
import com.veryshinnam.myapp.feature.home.data.dto.toHomeData
import com.veryshinnam.myapp.feature.home.model.HomeData
import javax.inject.Inject

class HomeRepositoryImpl  @Inject constructor(
    private val api: HomeApi
) : HomeRepository {

    // 홈 화면 조회
    override suspend fun getHome(): HomeData {
        val response: BaseResponse<HomeResult> = api.getHome()

        if (!response.isSuccess || response.result == null) {
            throw Exception("홈 화면 조회 실패: ${response.message}")
        }

        return response.result.toHomeData()
    }
}