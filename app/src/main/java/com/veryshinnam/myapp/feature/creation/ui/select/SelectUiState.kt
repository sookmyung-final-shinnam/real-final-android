package com.veryshinnam.myapp.feature.creation.ui.select

data class SelectUiState (

    // 테마
    val customThemes: Map<Int, String> = emptyMap(),    // 직접추가 테마(최대 2개)
    val selectedThemes: List<String> = emptyList(),     // 선택된 테마(최대 3개)

    val customBackground: String? = null,   // 직접추가 배경
    val selectedBackground: String = "",            // 선택된 배경
    val gender: String = "",
    val age: Int = 10,
    val name: String = "",
    val eyeColor: String = "",
    val hairColor: String = "",
    val hairStyle: String = ""
)