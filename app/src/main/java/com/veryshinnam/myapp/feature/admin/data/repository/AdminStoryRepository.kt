package com.veryshinnam.myapp.feature.admin.data.repository

import com.veryshinnam.myapp.feature.admin.data.api.AdminStoryApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminStoryRepository @Inject constructor(
    private val api: AdminStoryApi
) {
    suspend fun getIncompleteStories() = api.getIncompleteStories()

    suspend fun uploadImageYoutubeLink(id: Long, youtubeLink: String) =
        api.uploadImageYoutubeLink(id, youtubeLink)

    suspend fun uploadVideoYoutubeLink(id: Long, youtubeLink: String) =
        api.uploadVideoYoutubeLink(id, youtubeLink)

    suspend fun checkIsAdmin() = api.checkIsAdmin()

}