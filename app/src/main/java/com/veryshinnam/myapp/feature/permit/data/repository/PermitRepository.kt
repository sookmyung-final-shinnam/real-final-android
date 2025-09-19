package com.veryshinnam.myapp.feature.permit.data.repository

import com.veryshinnam.myapp.feature.permit.data.dto.JwtResponse

interface PermitRepository {

    // 로그인 api
    suspend fun login(tempCode: String): JwtResponse
}