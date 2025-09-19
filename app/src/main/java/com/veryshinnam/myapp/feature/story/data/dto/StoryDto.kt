package com.veryshinnam.myapp.feature.story.data.dto

data class StoryDto(
    val storyId: Long,
    val pageNumber: Int,
    val title: String?,
    val storyThemes: List<String>?,
    val storyBackgrounds: List<String>?,
    val description: String?,
    val storyContent: PageDto
)

data class PageDto(
    val content: String?,
    val imageUrl: String?,
    val videoUrl: String?
)