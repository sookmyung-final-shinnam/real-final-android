package com.veryshinnam.myapp.feature.creation.data.dto

import android.os.Parcelable
import com.veryshinnam.myapp.common.enums.Gender
import kotlinx.parcelize.Parcelize

// 대화 세션 시작 요청
@Parcelize
data class StartRequest(
    val themeNames: List<String>,
    val backgroundName: String,
    val characterName: String,
    val characterAge: Int,
    val gender: Gender,
    val eyeColor: String,
    val hairColor: String,
    val hairStyle: String
): Parcelable
