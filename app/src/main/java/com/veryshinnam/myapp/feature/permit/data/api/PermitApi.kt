package com.veryshinnam.myapp.feature.permit.data.api

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.permit.data.dto.JwtResponse
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface PermitApi {

    // 사용자 활성화 및 토큰 조회 (로그인) api
    @PATCH("/api/permit/login")
    suspend fun login(
        @Query("tempCode") tempCode: String
    ): BaseResponse<JwtResponse>
}