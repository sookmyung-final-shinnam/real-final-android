package com.veryshinnam.myapp.feature.permit.data.repository

import com.veryshinnam.myapp.feature.permit.data.dto.JwtResult

interface PermitRepository {

    // 로그인
    suspend fun login(tempCode: String): JwtResult
}