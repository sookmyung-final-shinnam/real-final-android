package com.veryshinnam.myapp.feature.character.model

data class StoriesData(
    val storyId: Long,      // 동화 아이디
    val title: String,      // 동화 제목
    val imageUrl: String,   // 동화 이미지 표지 png
    val storyStatus: StoryStatus, // 동화 영상 제작 상태 (COMPLETED)
    val videoUrl: String?    // 동화 영상 표지 mp4
)