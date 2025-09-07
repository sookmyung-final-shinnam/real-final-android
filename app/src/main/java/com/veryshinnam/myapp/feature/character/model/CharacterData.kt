package com.veryshinnam.myapp.feature.character.model

data class CharacterData(
    val id: Long,          // 캐릭터 아이디
    val name: String,      // 캐릭터 이름
    val gender: String,    // 캐릭터 성별
    val age: Int,          // 캐릭터 나이
    val image: String,     // 캐릭터 이미지
    val personality: String, // 캐릭터 성격
    val birth: String,     // 캐릭터 생성일
    val isFavorite: Boolean,        // 캐릭터 즐찾 여부
)
