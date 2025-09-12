package com.veryshinnam.myapp.feature.creation.select.ui

import com.veryshinnam.myapp.feature.creation.model.Gender

data class SelectUiState (
    val customTheme: String = "",        // 직접추가 테마
    val themes: List<String> = emptyList(), // 선택된 테마(최대 3개)
    val customBackground: String = "",   // 직접추가 배경
    val background: String = "", // 선택된 배경
    val gender: Gender = Gender.NONE,    // 성별
    val age: Int = -1,          // 나이
    val name: String = "",      // 이름
    val eyeColor: String = "빨강색",  // 눈색
    val hairColor: String = "빨강색", // 머리색
    val hairStyle: String = ""  // 머리모양
)