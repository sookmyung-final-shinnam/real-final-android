package com.veryshinnam.myapp.feature.creation.ui.select

import com.veryshinnam.myapp.feature.creation.model.Gender

data class SelectUiState (
    val customTheme: String? = null,      // 직접추가 테마
    val selectedThemes: List<String> = emptyList(), // 선택된 테마(최대 3개)

    val customBackground: String? = null, // 직접추가 배경
    val selectedBackground: String = "",  // 선택된 배경

    val gender: Gender? = null,
    val age: Int = 10,
    val name: String = "",
    val eyeColor: String = "",
    val hairColor: String = "",
    val hairStyle: String = ""
)