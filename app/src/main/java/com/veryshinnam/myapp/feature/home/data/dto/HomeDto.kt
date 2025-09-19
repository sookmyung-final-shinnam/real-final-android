package com.veryshinnam.myapp.feature.home.data.dto

// 홈 화면 조회 응답
data class HomeDto(
    val userId: Long,
    val username: String,
//    val userPoint: Int,
    val profileImageUrl: String,
    val favorites: List<FavoriteDto>
)

data class FavoriteDto(
    val id: Long,
    val name: String,
    val imageUrl: String
)