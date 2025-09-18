package com.veryshinnam.myapp.feature.character.model

data class StoryVideoData(
    val title: String,      // 동화 제목
    val storyId: Long?,      // 동화 아이디
    val storyUrl: String, // 동화 표지 png
    val videoUrl: String?    // 영상 표지 mp4
)