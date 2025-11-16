package com.veryshinnam.myapp.feature.character.data.dto

import com.veryshinnam.myapp.common.model.Gender
import com.veryshinnam.myapp.common.model.ImageType
import com.veryshinnam.myapp.feature.character.model.CharacterData
import com.veryshinnam.myapp.feature.character.model.StoriesData
import com.veryshinnam.myapp.feature.character.model.StoryStatus

fun CharacterDetailResult.toCharacterData(): CharacterData =
    CharacterData(
        id = characterId,
        name = name,
        gender = Gender.valueOf(gender),
        age = age,
        image = ImageType.Url(imageUrl),
        personality = personality,
        birth = createTime.substring(0, 10),
        isFavorite = important,
        stories = this.toStoriesData()
    )

fun CharacterDetailResult.toStoriesData(): StoriesData =
    StoriesData(
        storyId = storyId,
        title = storyTitle,
        imageUrl = ImageType.Url(imageStoryUrl), // 기본값
        storyStatus = StoryStatus.valueOf(videoStatus),
        videoUrl = videoStoryUrl,
        imageYLink = imageYoutubeLink,
        videoYLink = videoYoutubeLink
    )