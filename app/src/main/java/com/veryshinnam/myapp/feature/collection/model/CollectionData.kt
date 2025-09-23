package com.veryshinnam.myapp.feature.collection.model

import com.veryshinnam.myapp.common.enums.Gender

data class CollectionData(
    val id: Long,       // 캐릭터 아이디
    val name: String,   // 캐릭터 이름
    val image: String?, // 캐릭터 이미지
    val gender: Gender, // 캐릭터 성별
    val isFavorite: Boolean // 캐릭터 즐찾 여부
)
