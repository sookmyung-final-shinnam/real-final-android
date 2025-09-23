package com.veryshinnam.myapp.feature.permit.data.dto

// 로그인 응답 결과
data class JwtResult(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiredAt: String
)