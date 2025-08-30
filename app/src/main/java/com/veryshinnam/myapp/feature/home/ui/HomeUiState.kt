package com.veryshinnam.myapp.feature.home.ui

import com.veryshinnam.myapplication.feature.home.data.dto.CharacterShortResult

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val nickname: String = "닉네임 없음",
    val points: Int = 0,
    val characterCnt: Int = 0,
    val storyCnt: Int = 0,
    val characters: List<CharacterShortResult> = emptyList()
)