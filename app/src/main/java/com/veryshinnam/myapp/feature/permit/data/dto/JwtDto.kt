package com.veryshinnam.myapp.feature.permit.data.dto

data class JwtDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiredAt: String
)