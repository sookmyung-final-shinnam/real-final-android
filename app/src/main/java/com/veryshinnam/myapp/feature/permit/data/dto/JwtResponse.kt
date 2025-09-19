package com.veryshinnam.myapp.feature.permit.data.dto

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiredAt: String
)