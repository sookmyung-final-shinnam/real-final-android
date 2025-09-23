package com.veryshinnam.myapp.feature.story.data.repository

import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryData
import com.veryshinnam.myapp.feature.story.model.StoryType

interface StoryRepository {

    // 동화 프롤로그 조회 (page: 0)
    suspend fun getPrologue(storyId: Long, storyType: StoryType): StoryData

    // 동화 모든 페이지 조회 (page: 1~4)
    suspend fun getPages(
        storyId: Long,
        pageCount: Int = 4,
        storyType: StoryType
    ): List<PageData>
}