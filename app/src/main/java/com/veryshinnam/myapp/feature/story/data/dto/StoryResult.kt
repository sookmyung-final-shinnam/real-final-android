package com.veryshinnam.myapp.feature.story.data.dto

// // 동화 페이지별 내용 조회 응답 결과
data class StoryResult(
    val storyId: Long,
    val pageNumber: Int,
    val title: String?,
    val storyThemes: List<String>?,
    val storyBackgrounds: List<String>?,
    val description: String?,
    val storyContent: PageResult
)

data class PageResult(
    val content: String?,
    val imageUrl: String?,
    val videoUrl: String?
)