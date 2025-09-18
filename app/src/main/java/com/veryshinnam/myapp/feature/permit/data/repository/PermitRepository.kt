package com.veryshinnam.myapp.feature.permit.data.repository

import com.veryshinnam.myapp.feature.permit.data.JwtResponse

interface PermitRepository {

    // 로그인 api
    suspend fun login(tempCode: String): JwtResponse
}