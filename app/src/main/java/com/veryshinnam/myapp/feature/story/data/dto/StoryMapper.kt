package com.veryshinnam.myapp.feature.story.data.dto

import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryData
import com.veryshinnam.myapp.feature.story.model.StoryType
import kotlin.String
import kotlin.collections.List

fun StoryResult.toStoryData(storyType: StoryType): StoryData =
    StoryData(
        id = storyId,
        title = title.orEmpty(),
        tags = createHashtags(storyThemes, storyBackgrounds),
        description = description.orEmpty(),
        thumbnail = when (storyType) {
            StoryType.IMAGE -> storyContent.imageUrl.orEmpty()
            StoryType.VIDEO -> storyContent.videoUrl.orEmpty()
        }
    )

fun StoryResult.toPageData(storyType: StoryType): PageData =
    PageData(
        id = pageNumber,
        url = when (storyType) {
            StoryType.IMAGE -> storyContent.imageUrl.orEmpty()
            StoryType.VIDEO -> storyContent.videoUrl.orEmpty()
        },
        content = storyContent.content.orEmpty()
    )

private fun createHashtags(themes: List<String>?, backgrounds: List<String>?): String {
    return (themes.orEmpty() + backgrounds.orEmpty())
        .joinToString(" ") { "#$it" }
}