package com.veryshinnam.myapp.feature.permit.data.repository

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.permit.data.dto.JwtDto
import com.veryshinnam.myapp.feature.permit.data.api.PermitApi
import javax.inject.Inject

class PermitRepositoryImpl  @Inject constructor(
    private val api: PermitApi
) : PermitRepository {

    // 로그인 api 호출
    override suspend fun login(tempCode: String): JwtDto {
        val response: BaseResponse<JwtDto> = api.login(tempCode)

        if (!response.isSuccess || response.result == null) {
            throw Exception("로그인 실패: ${response.message}")
        }

        return response.result
    }
}