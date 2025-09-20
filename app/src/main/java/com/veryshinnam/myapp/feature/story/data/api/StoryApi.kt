package com.veryshinnam.myapp.feature.story.data.api

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.story.data.dto.StoryResult
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryApi {

    // 동화 페이지별 내용 조회 api
    @GET("api/story/{storyId}/page/{pageId}")
    suspend fun getStoryPage(
        @Path("storyId") storyId: Long,
        @Path("pageId") pageId: Int
    ): BaseResponse<StoryResult>
}