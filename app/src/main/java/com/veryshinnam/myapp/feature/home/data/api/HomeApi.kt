package com.veryshinnam.myapp.feature.home.data.api

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.home.data.dto.HomeResponse
import retrofit2.http.GET

interface HomeApi {

    // 홈 화면 조회
    @GET("api/home")
    suspend fun getHome(): BaseResponse<HomeResponse>
}