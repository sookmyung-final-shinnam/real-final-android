package com.veryshinnam.myapp.feature.story.data.repository

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.story.data.api.StoryApi
import com.veryshinnam.myapp.feature.story.data.dto.StoryResult
import com.veryshinnam.myapp.feature.story.data.dto.toPageData
import com.veryshinnam.myapp.feature.story.data.dto.toStoryData
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryData
import com.veryshinnam.myapp.feature.story.model.StoryType
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val api: StoryApi
): StoryRepository {

    // 동화 프롤로그 조회 (page: 0)
    override suspend fun getPrologue(storyId: Long, storyType: StoryType): StoryData {
        val response: BaseResponse<StoryResult> = api.getStoryPage(storyId, 0)

        if (!response.isSuccess || response.result == null) {
            throw Exception("동화 프롤로그 조회 실패: ${response.message}")
        }

        return response.result.toStoryData(storyType)
    }

    // 동화 모든 페이지 조회 (page: 1~4)
    override suspend fun getPages(
        storyId: Long,
        pageCount: Int,
        storyType: StoryType
    ): List<PageData> {
        return (1..pageCount).map { pageNumber ->
            val response: BaseResponse<StoryResult> = api.getStoryPage(storyId, pageNumber)

            if (!response.isSuccess || response.result == null) {
                throw Exception("동화 $pageNumber 페이지 조회 실패: ${response.message}")
            }

            response.result.toPageData(storyType)
        }
    }
}