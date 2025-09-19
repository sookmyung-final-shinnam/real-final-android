package com.veryshinnam.myapp.feature.home.data.dto

// 홈 화면 응답
data class HomeResponse(
    val userId: Long,
    val username: String,
//    val userPoint: Int,
    val profileImageUrl: String,
    val favorites: List<FavoritesDto>
)

data class FavoritesDto(
    val id: Long,
    val name: String,
    val imageUrl: String
)