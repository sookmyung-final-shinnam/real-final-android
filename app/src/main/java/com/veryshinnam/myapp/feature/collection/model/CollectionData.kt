package com.veryshinnam.myapp.feature.collection.model

data class CollectionData(
    val id: Long,       // 캐릭터 아이디
    val name: String,   // 캐릭터 이름
    val image: String?, // 캐릭터 이미지
    val gender: Filter, // 캐릭터 성별
    val isFavorite: Boolean // 캐릭터 즐찾 여부
)
