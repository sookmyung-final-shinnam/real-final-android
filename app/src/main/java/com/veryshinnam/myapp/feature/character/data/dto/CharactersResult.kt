package com.veryshinnam.myapp.feature.character.data.dto

// 캐릭터 전체 조회 응답 결과
data class CharactersResult(
    val characters: List<CharacterResult>
)

data class CharacterResult(
    val characterId: Long,
    val name: String,
    val gender: String,
    val imageUrl: String?,
    val important: Boolean,
    val createTime: String,
)