package com.veryshinnam.myapp.feature.home.ui

import android.net.http.HttpException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.home.data.repository.HomeRepository
import com.veryshinnam.myapp.feature.home.model.FavoriteData
import com.veryshinnam.myapp.feature.home.model.HomeRandomMessages
import com.veryshinnam.myapp.feature.permit.ui.PermitUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    // 홈 화면 상태 관리
    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init { fetchHome() }

    // 홈 화면 불러오기
    private fun fetchHome() {
        _homeUiState.value = HomeUiState.Loading

        viewModelScope.launch {
            try {
                val homeData = repository.getHome() // api 호출
                val randomMessage = HomeRandomMessages.getRandomMessage()

                _homeUiState.value = HomeUiState.Success(
                    homeData = homeData,
                    randomMessage = randomMessage
                )

                // 에러 케이스 테스트 용도
//             _homeUiState.value = HomeUiState.Error("홈 정보를 불러오지 못했어요.")
            } catch (e: Exception) {
                    _homeUiState.value = HomeUiState.Error("홈 화면 불러오기 실패: ${e.message}")
            }
        }
    }

    // 홈 화면 다시 조회
    fun reload() {
        val currentLast = (homeUiState.value as? HomeUiState.Success)?.lastSelectedCharacter
        viewModelScope.launch {
            try {
                val data = repository.getHome()
                val randomMessage = HomeRandomMessages.getRandomMessage()

                _homeUiState.value = HomeUiState.Success(
                    homeData = data,
                    lastSelectedCharacter = currentLast, // 마지막 선택 캐릭터 유지
                    randomMessage = randomMessage
                )
            } catch (e: Exception) {
                _homeUiState.value = HomeUiState.Error(e.message ?: "에러")
            }
        }
    }

    // 마지막 선택 캐릭터 업데이트
    fun updateLastSelected(id: Long) {
        _homeUiState.update { state ->
            if (state is HomeUiState.Success) {
                state.copy(lastSelectedCharacter = id)
            } else state
        }
    }

    // 랜덤 문구 업데이트
    fun changeMessage() {
        val currentState = _homeUiState.value
        if (currentState is HomeUiState.Success) {
            _homeUiState.value = currentState.copy(
                randomMessage = HomeRandomMessages.messages.random()
            )
        }
    }

    fun updatePoint(newPoint: Int) {
        val currentState = _homeUiState.value
        if (currentState is HomeUiState.Success) {
            val updatedHomeData = currentState.homeData.copy(
                points = newPoint
            )
            _homeUiState.value = currentState.copy(
                homeData = updatedHomeData
            )
        }
    }
//    private fun getDummyUser(): UserData {
//        return UserData(
//            username = "짱신남",
//            points = 99,
//            characters = 5
//        )
//    }

//    private fun getDummyFavorites(): List<FavoriteData> {
//        return listOf(
//            FavoriteData(id = 11, name = "지우", image = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_1.png"),
//            FavoriteData(id = 20,  name = "미니", image = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_20.png"),
//            FavoriteData(id = 19,  name = "미니", image = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_19.png"),
//            FavoriteData(id = 18,  name = "민수", image = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png")
//        )
//    }
}
