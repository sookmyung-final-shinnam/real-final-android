package com.veryshinnam.myapp.feature.character.data.dto

import com.veryshinnam.myapp.feature.collection.model.CollectionData
import com.veryshinnam.myapp.feature.collection.model.Filter

fun CharacterDto.toCollectionData(): CollectionData {
    return CollectionData(
        id = characterId,
        name = name,
        image = imageUrl,
        gender = when (gender.uppercase()) {
            "MALE" -> Filter.MALE
            "FEMALE" -> Filter.FEMALE
            else -> Filter.ALL // 기본값
        },
        isFavorite = important
    )
}