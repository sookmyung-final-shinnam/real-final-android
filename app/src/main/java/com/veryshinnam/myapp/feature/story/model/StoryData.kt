package com.veryshinnam.myapp.feature.story.model

data class StoryData(
    val story: StoryType,   // 동화 타입
    val storyId: Long,      // 스토리 아이디
    val thumbnail: String,  // 동화 썸네일
    val title: String,      // 동화 이미지
    val tags: String,       // 동화 태그들
    val description: String // 동화 줄거리
)
