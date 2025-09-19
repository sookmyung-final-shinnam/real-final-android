package com.veryshinnam.myapp.feature.character.data.dto

import com.veryshinnam.myapp.common.enums.Gender
import com.veryshinnam.myapp.feature.character.model.CharacterData
import com.veryshinnam.myapp.feature.character.model.StoriesData
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

fun CharacterDetailResponse.toCharacterData(): CharacterData {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

    return CharacterData(
        id = characterId,
        name = name,
        gender = Gender.valueOf(gender),
        age = age,
        image = imageUrl,
        personality = personality,
        birth = createTime.substring(0, 10),
        isFavorite = important,
        stories = this.toStoriesData()
    )
}

fun CharacterDetailResponse.toStoriesData(): StoriesData =
    StoriesData(
        storyId = storyId,
        title = storyTitle,
        imageUrl = imageStoryUrl,   // 기본값
        videoUrl = videoStoryUrl  // 기본값
    )