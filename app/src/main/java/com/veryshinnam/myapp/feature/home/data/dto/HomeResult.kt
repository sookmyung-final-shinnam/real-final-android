package com.veryshinnam.myapp.feature.home.data.dto

// 홈 화면 조회 응답 결과
data class HomeResult(
    val userId: Long,
    val username: String,
    val points: Int,
    val profileImageUrl: String,
    val favorites: List<FavoriteResult>
)

data class FavoriteResult(
    val id: Long,
    val name: String,
    val imageUrl: String
)