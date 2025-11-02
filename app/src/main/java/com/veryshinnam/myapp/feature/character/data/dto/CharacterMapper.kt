package com.veryshinnam.myapp.feature.character.data.dto

import com.veryshinnam.myapp.common.enums.Gender
import com.veryshinnam.myapp.feature.character.model.CharacterData
import com.veryshinnam.myapp.feature.character.model.StoriesData
import com.veryshinnam.myapp.feature.character.model.StoryStatus

fun CharacterDetailResult.toCharacterData(): CharacterData =
    CharacterData(
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

fun CharacterDetailResult.toStoriesData(): StoriesData =
    StoriesData(
        storyId = storyId,
        title = storyTitle,
        imageUrl = imageStoryUrl,   // 기본값
        storyStatus = StoryStatus.valueOf(videoStatus),
        videoUrl = videoStoryUrl
    )