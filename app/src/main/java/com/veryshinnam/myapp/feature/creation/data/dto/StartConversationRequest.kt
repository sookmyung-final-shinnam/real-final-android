package com.veryshinnam.myapp.feature.creation.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StartConversationRequest(
    val themeNames: List<String>,
    val backgroundName: String,
    val characterName: String,
    val characterAge: Int,
    val gender: String,
    val eyeColor: String,
    val hairColor: String,
    val hairStyle: String
): Parcelable
