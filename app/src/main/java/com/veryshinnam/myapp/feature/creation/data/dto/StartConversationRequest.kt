package com.veryshinnam.myapp.feature.creation.data.dto

import android.os.Parcelable
import com.veryshinnam.myapp.feature.creation.model.Gender
import kotlinx.parcelize.Parcelize

@Parcelize
data class StartConversationRequest(
    val themeNames: List<String>,
    val backgroundName: String,
    val characterName: String,
    val characterAge: Int,
    val gender: Gender,
    val eyeColor: String,
    val hairColor: String,
    val hairStyle: String
): Parcelable
