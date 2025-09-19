package com.veryshinnam.myapp.feature.home.ui

import com.veryshinnam.myapp.feature.home.model.FavoriteData
import com.veryshinnam.myapp.feature.home.model.HomeData

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState

    data class Success(
        val homeData: HomeData,   // 홈 정보
        val lastSelectedCharacter: Long? = null, // 마지막 선택 캐릭터
        val randomMessage: String // 랜덤 메시지
    ) : HomeUiState
}

