package com.veryshinnam.myapp.feature.character.data.dto

// 캐릭터 상세조회 응답
data class CharacterDetailResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: CharacterDetailResult
)

// 캐릭터 상세조회 결과
data class CharacterDetailResult(
    val characterId: Long,
    val name: String,
    val gender: String,
    val age: Int,
    val imageUrl: String,
    val personality: String,
    val important: Boolean,
    val createTime: String,
    val storyId: Long,
    val storyTitle: String
)