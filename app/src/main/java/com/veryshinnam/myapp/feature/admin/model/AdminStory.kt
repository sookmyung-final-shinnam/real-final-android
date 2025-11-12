package com.veryshinnam.myapp.feature.admin.model

data class AdminStory(
    val id: Long,
    val title: String,
    val status: String,
    val videoStatus: String?,
    val imageYoutubeLink: String?,
    val videoYoutubeLink: String?,
    val needsImageLink: Boolean,
    val needsVideoLink: Boolean
)