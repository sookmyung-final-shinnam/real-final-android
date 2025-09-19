package com.veryshinnam.myapp.feature.character.data.dto

import com.veryshinnam.myapp.feature.collection.model.CollectionData
import com.veryshinnam.myapp.common.enums.Gender

fun CharacterDto.toCollectionData(): CollectionData =
    CollectionData(
        id = characterId,
        name = name,
        image = imageUrl,
        gender = Gender.valueOf(gender),
        isFavorite = important
    )
