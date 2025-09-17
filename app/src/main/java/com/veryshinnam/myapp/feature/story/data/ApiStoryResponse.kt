package com.veryshinnam.myapp.feature.story.data

data class ApiStoryResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ApiStoryResult
)

data class ApiStoryResult(
    val storyId: Long,
    val pageNumber: Int,
    val title: String,
    val storyThemes: List<String>,
    val storyBackgrounds: List<String>,
    val description: String,
    val storyContent: ApiStoryContent
)

data class ApiStoryContent(
    val content: String?,
    val imageUrl: String?,
    val videoUrl: String?
)