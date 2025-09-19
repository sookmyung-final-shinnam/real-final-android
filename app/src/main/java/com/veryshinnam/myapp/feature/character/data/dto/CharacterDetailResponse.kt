package com.veryshinnam.myapp.feature.character.data.dto

// 캐릭터 상세 조회 응답
data class CharacterDetailResponse (
    val characterId: Long,
    val name: String,
    val gender: String,
    val age: Int,
    val imageUrl: String,
    val personality: String,
    val important: Boolean,
    val createTime: String,
    val storyId: Long,
    val storyTitle: String,
    val imageStoryUrl: String,
    val videoStoryUrl: String?
)
