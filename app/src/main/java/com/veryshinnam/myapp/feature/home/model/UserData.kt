package com.veryshinnam.myapp.feature.home.model

data class UserData(
    val username: String,   // 유저 이름
    val points: Int,      // 나침반 개수
    val characters: Int,  // 생성한 캐릭터(스토리) 수
)