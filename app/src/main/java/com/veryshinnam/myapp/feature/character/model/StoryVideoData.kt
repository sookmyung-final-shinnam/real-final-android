package com.veryshinnam.myapp.feature.character.model

data class StoryVideoData(
    val title: String,      // 동화 제목
    val storyId: Long?,      // 동화 아이디
    val storyImage: String?, // 동화 표지 이미지
    val videoImage: String?    // 영상 표지 이미지
)