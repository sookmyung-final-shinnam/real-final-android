package com.veryshinnam.myapp.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.home.data.dto.FavoriteCharacter
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
            val data = HomeUiState.HomeData(
                username = "짱신남",
                points = 99,
                myCharacters = 5,
                favoriteCharacters = listOf(
                    FavoriteCharacter(id = 11, name = "유리", imageUrl = "https://ifh.cc/g/QP5O4d.png"),
                    FavoriteCharacter(id = 12, name = "파워", imageUrl = "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg"),
                    FavoriteCharacter(id = 13, name = "파파워", imageUrl = "https://ifh.cc/g/XTGSPy.png")
                )
            )
            _homeUiState.value = HomeUiState.Success(data)

            // 에러 케이스 테스트 용도
            // _homeUiState.value = HomeUiState.Error("홈 정보를 불러오지 못했어요.")
        }
    }

    // 홈 화면 다시 조회
    fun reload() {
        fetchHomeDummy()
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
