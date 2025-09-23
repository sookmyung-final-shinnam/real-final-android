package com.veryshinnam.myapp.feature.home.model

data class HomeData (
    val username: String,   // 유저 이름
    val points: Int,        // 나침반 개수
    val favorites: List<FavoriteData> // 즐찾 캐릭 아이디 + 이름 + 이미지
)