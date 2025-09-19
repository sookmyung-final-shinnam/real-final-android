package com.veryshinnam.myapp.feature.story.model

data class StoryData(
    val storyId: Long,      // 동화 아이디
    val title: String,      // 동화 제목
    val tags: String,       // 동화 태그들
    val description: String,// 동화 줄거리
    val thumbnail: String   // 동화 썸네일
)
