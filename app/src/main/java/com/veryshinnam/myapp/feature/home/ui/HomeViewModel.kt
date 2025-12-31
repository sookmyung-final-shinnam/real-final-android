package com.veryshinnam.myapp.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.common.model.ManualData
import com.veryshinnam.myapp.common.model.ManualTarget
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
//        viewModelScope.launch {
//            val newUser = sessionManager.isNewUser()
//            _isNewUser.value = newUser
//        }
        _isNewUser.value = true
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
    // 홈 화면 사용 매뉴얼
    val manuals = listOf(
        ManualData("반가워요 {username}!\n스토릭터에 온 걸 환영해요.", ManualTarget.NONE),
        ManualData("여기 캐릭터 생성 버튼에서 직접 동화의 내용을 하나씩 만들어가며 나만의 동화와 캐릭터를 만들 수 있어요!", ManualTarget.IMAGE),
        ManualData("보관함에서는 만든 동화와 캐릭터를 다시 볼 수 있어요!", ManualTarget.IMAGE),
        ManualData("대시보드에선 동화를 만들며 발견되었던 {username}만의 특징을 확인할 수 있고", ManualTarget.IMAGE),
        ManualData("출석 체크를 통해 스토릭터를 사용하며 다양한 도움이 되는 포인트를 얻을 수 있어요.", ManualTarget.IMAGE)
    )

    fun showManual() = manualManager.request()

    fun startManual() {
        _manualStep.value = 0
        manualManager.start()
        manualManager.update(manuals[0].message)
    }

    fun nextManual() {
        val current = _manualStep.value

        if (current < manuals.lastIndex) {
            val next = current + 1
            _manualStep.value = next
            manualManager.update(manuals[next].message)
        } else if (current == manuals.lastIndex) {
            _manualStep.value = manuals.size
        }
    }

    fun stopManual() = manualManager.stop()

    fun hideManual() = manualManager.clear()
}
