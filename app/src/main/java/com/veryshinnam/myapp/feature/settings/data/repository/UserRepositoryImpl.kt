package com.veryshinnam.myapp.feature.settings.data.repository

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.settings.data.api.UserApi
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
): UserRepository {

    // 로그아웃
    override suspend fun logout(): Boolean {
        val response: BaseResponse<String> = api.logout()

        if (!response.isSuccess || response.result == null) {
            throw Exception("로그아웃 실패: ${response.result}")
        }

        return true
    }

    // 회원 탈퇴
    override suspend fun withdraw(): Boolean {
        val response: BaseResponse<String> = api.withdraw()

        if (!response.isSuccess || response.result == null) {
            throw Exception("회원 탈퇴 실패: ${response.result}")
        }

        return true
    }
}