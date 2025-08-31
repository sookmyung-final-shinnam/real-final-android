package com.veryshinnam.myapp.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.home.data.dto.CharacterShortResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
//    private val homeRepository: HomeRepository
) : ViewModel() {

    // 홈 화면 상태 관리
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        fetchUserDummy()
    }

    // 홈 화면 더미 데이터
    private fun fetchUserDummy() {
        viewModelScope.launch {
            _homeUiState.value = HomeUiState(
                isLoading = false,
                nickname = "미니마니목스",
                points = 999,
                characterCnt = 5,
                storyCnt = 10,
                characters = listOf(
                    CharacterShortResult(id = 11, firstImage = "https://ifh.cc/g/QP5O4d.png"),
                    CharacterShortResult(id = 12, firstImage = "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg"),
                    CharacterShortResult(id = 13, firstImage = "https://ifh.cc/g/XTGSPy.png")
                )
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