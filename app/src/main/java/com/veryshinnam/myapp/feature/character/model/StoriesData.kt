package com.veryshinnam.myapp.feature.character.model

import com.veryshinnam.myapp.common.model.ImageType

data class StoriesData(
    val storyId: Long,       // 동화 아이디
    val title: String,       // 동화 제목
    val imageUrl: ImageType, // 종이 동화 표지 png
    val videoUrl: String?,        // 움직이는 동화 표지 mp4
    val storyStatus: StoryStatus, // 움직이는 동화 제작 상태 (COMPLETED)
    val imageYLink: String?, // 종이 동화 유튜브 링크
    val videoYLink: String?  // 움직이는 동화 유튜브 링크
)