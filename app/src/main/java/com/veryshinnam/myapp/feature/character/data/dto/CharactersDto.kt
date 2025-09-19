package com.veryshinnam.myapp.feature.character.data.dto

// 캐릭터 전체 조회 응답
data class CharactersDto(
    val characters: List<CharacterDto>
)

data class CharacterDto(
    val characterId: Long,
    val name: String,
    val gender: String,
    val imageUrl: String?,
    val important: Boolean,
    val createTime: String,
)