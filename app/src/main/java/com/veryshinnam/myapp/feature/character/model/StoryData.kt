package com.veryshinnam.myapp.feature.character.model

data class StoryData(
    val title: String,      // 동화 제목
    val storyId: Long?,      // 동화 아이디
    val storyImage: String?, // 동화 이미지
    val videoId: Long?,      // 영상 아이디
    val videoUrl: String?    // 영상 링크
)