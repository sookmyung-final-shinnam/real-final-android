package com.veryshinnam.myapp.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.home.model.FavoriteData
import com.veryshinnam.myapp.feature.home.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
//    private val homeRepository: HomeRepository
) : ViewModel() {

    // 홈 화면 상태 관리
    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        fetchHomeDummy()
    }

    // 홈 화면 조회 더미 데이터
    private fun fetchHomeDummy() {
        _homeUiState.value = HomeUiState.Loading

        viewModelScope.launch {
            // 로딩 느낌만 살짝
            delay(300)

            // 성공 케이스 (더미)
            val user = getDummyUser()
            val favorites = getDummyFavorites()
            val randomMessage = HomeRandomMessages.messages.random()

            _homeUiState.value = HomeUiState.Success(
                userData = user,
                favoritesData = favorites,
                randomMessage = randomMessage
            )

            // 에러 케이스 테스트 용도
//             _homeUiState.value = HomeUiState.Error("홈 정보를 불러오지 못했어요.")
        }
    }

    // 홈 화면 다시 조회
    fun reload() {
        fetchHomeDummy()
    }

    private fun getDummyUser(): UserData {
        return UserData(
            username = "짱신남",
            points = 99,
            characters = 5
        )
    }

    private fun getDummyFavorites(): List<FavoriteData> {
        return listOf(
            FavoriteData(id = 11, name = "미니", image = "https://ifh.cc/g/QP5O4d.png"),
            FavoriteData(id = 20,  name = "미니", image = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_20.png"),
            FavoriteData(id = 19,  name = "미니", image = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_19.png"),
            FavoriteData(id = 18,  name = "민수", image = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png")
        )
    }

    // 마지막 선택 캐릭터 업데이트
    fun updateLastSelectedCharacter(id: Long) {
        val currentState = _homeUiState.value
        if (currentState is HomeUiState.Success) {
            _homeUiState.value = currentState.copy(lastSelectedCharacter = id)
        }
    }

    // 랜덤 문구 업데이트
    fun updateRandomMessage() {
        val currentState = _homeUiState.value
        if (currentState is HomeUiState.Success) {
            _homeUiState.value = currentState.copy(
                randomMessage = HomeRandomMessages.messages.random()
            )
        }
    }

    // 홈 화면 api 호출
//    fun fetchUserInfo() {
//        viewModelScope.launch {
//            _homeUiState.value = _homeUiState.value.copy(isLoading = true)
//
//            // 홈 조회 요청
//            homeRepository.getUserInfo()
//                .onSuccess { result ->
//                    _homeUiState.value = _homeUiState.value.copy(
//                        nickname = result.nickname,
//                        points = result.userPoint,
//                        characterCnt = result.userFairyNum,
//                        storyCnt = result.userFairyTaleNum,
//                        fairies = result.favoriteFairies,
//                        isLoading = false,
//                        errorMessage = null
//                    )
//                }
//                .onFailure { error ->
//                    _homeUiState.value = _homeUiState.value.copy(
//                        isLoading = false,        // 로딩 끝
//                        errorMessage = error.message // 에러 메시지 저장
//                    )
//                }
//        }
//    }
}
