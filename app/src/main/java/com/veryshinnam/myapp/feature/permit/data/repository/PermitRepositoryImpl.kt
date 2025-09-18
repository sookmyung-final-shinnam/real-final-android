package com.veryshinnam.myapp.feature.permit.data.repository

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.permit.data.JwtResponse
import com.veryshinnam.myapp.feature.permit.data.api.PermitApi
import javax.inject.Inject

class PermitRepositoryImpl  @Inject constructor(
    private val api: PermitApi
) : PermitRepository {

    // 로그인 api 호출
    override suspend fun login(tempCode: String): JwtResponse {
        val response: BaseResponse<JwtResponse> = api.login(tempCode)

        if (!response.isSuccess || response.result == null) {
            throw Exception("로그인 실패: ${response.message}")
        }

        return response.result
    }
}