package com.veryshinnam.myapp.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.common.model.ManualState
import com.veryshinnam.myapp.common.model.WarningState
import com.veryshinnam.myapp.core.manual.ManualManager
import com.veryshinnam.myapp.core.session.SessionManager
import com.veryshinnam.myapp.feature.admin.data.repository.AdminStoryRepository
import com.veryshinnam.myapp.feature.home.data.repository.HomeRepository
import com.veryshinnam.myapp.feature.home.model.HomeRandomMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val adminRepository: AdminStoryRepository,
    private val sessionManager: SessionManager,
    private val manualManager: ManualManager
) : ViewModel() {

    // 화면 전체 ui 상태
    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState = _homeUiState.asStateFlow()

    // 관리자 여부 상태 관리
    private val _isAdmin = MutableStateFlow<Boolean?>(null)
    val isAdmin = _isAdmin.asStateFlow()

    // 신규 유저 여부 상태
    private val _isNewUser = MutableStateFlow(false)
    val isNewUser = _isNewUser.asStateFlow()

    // 유저 닉네임
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    // 단순 경고창 상태
    private val _warningState = MutableStateFlow(WarningState())
    val warningState = _warningState.asStateFlow()

    // ManualManager 구독
    val manualState = manualManager.state
    val manualMessage = manualManager.message

    // 매뉴얼 진행 단계 상태
    private val _manualStep = MutableStateFlow(0)
    val manualStep = _manualStep.asStateFlow()

    // vm 초기화
    init {
        fetchHome()
        checkNewUser()
    }

    // --- ui 이벤트 관련 ---
    // 신규 유저 확인
    private fun checkNewUser() {
        viewModelScope.launch {
            val newUser = sessionManager.isNewUser()
            _isNewUser.value = newUser
        }
    }

    // 신규 유저 업데이트
    fun updateNewUser() {
        viewModelScope.launch {
            sessionManager.removeNewUser()
            _isNewUser.value = false
        }
    }

    fun checkAdminStatus() {
        viewModelScope.launch {
            try {
                val response = adminRepository.checkIsAdmin()
                if (response.isSuccess) {
                    _isAdmin.value = response.result
                } else {
                    _isAdmin.value = false
                }
            } catch (e: Exception) {
                _isAdmin.value = false
            }
        }
    }

    // 단순 경고창 열기
    fun showWarning(warningText: String) {
        _warningState.value = WarningState(
            isVisible = true,
            warningText = warningText
        )
    }

    // 단순 경고창 닫기
    fun hideWarning() {
        _warningState.value = WarningState()
    }


    // --- api 호출 관련 ---
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

                _username.value = homeData.username
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

    // --- 매뉴얼 관련 ---
    // 시작 문구
    val firstManuals = listOf(
        "반가워요 {username}!\n스토릭터에 온 걸 환영해요.",
        "캐릭터와 동화 생성 버튼을 누르면, ",
        "{username}만의 캐릭터와 동화를 만들 수 있고",
        "만든 캐릭터와 동화는 보관함 버튼을 누르면 확인할 수 있어요!",
        "가장 왼쪽의 대시보드 버튼을 누르면, ",
        "동화를 만들며 알게 된 {username}만의 특징이 보이고",
        "출석 체크 버튼을 누르면 포인트인 도토리를 모을 수 있어요!"
    )

    // 마지막 문구
    val lastManuals = listOf(
        "스토릭터에 대한 설명은 홈 화면의 환경설정에서 다시 볼 수 있어요.",
        "혹시 놓친 설명이 있었나요?",
        "걱정마요! 홈화면의 '설정 '버튼을 누르면,",
        "앱 사용설명 다시보기 버튼으로 다시 볼 수 있어요.",
        "그럼 이제 스토릭터를 자유롭게 즐겨보세요!"
    )

    fun requestManual() = manualManager.request()

    fun startManual() = manualManager.start()

    fun loadFirstManual() {
        _manualStep.value = 0
        manualManager.update(firstManuals[0])
    }

    fun nextManual() {
        val current = _manualStep.value

        when (manualManager.state.value) {

            ManualState.START -> {
                if (current < firstManuals.lastIndex) {
                    val next = current + 1
                    _manualStep.value = next
                    manualManager.update(firstManuals[next])
                } else {
                    _manualStep.value = firstManuals.size   // 다음 스크린
                }
            }

            ManualState.FINISH -> {
                if (current < lastManuals.lastIndex) {
                    val next = current + 1
                    _manualStep.value = next
                    manualManager.update(lastManuals[next])
                } else {
                    clearManual()   // 매뉴얼 종료
                }
            }

            else -> {}
        }
    }

    fun stopManual() = manualManager.stop()

    fun clearManual() = manualManager.clear()
}
