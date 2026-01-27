package com.veryshinnam.myapp.feature.permit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.core.session.ReviewToken.REVIEW_ACCESS_TOKEN
import com.veryshinnam.myapp.core.session.ReviewToken.REVIEW_EXPIRE_AT
import com.veryshinnam.myapp.core.session.SessionManager
import com.veryshinnam.myapp.feature.permit.data.repository.PermitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermitViewModel @Inject constructor(
    private val repository: PermitRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _permitUiState = MutableStateFlow<PermitUiState>(PermitUiState.Idle)
    val permitUiState: StateFlow<PermitUiState> = _permitUiState

    // 액세스 토큰 존재-만료 확인
    fun checkAccessToken() {
        viewModelScope.launch {
            _permitUiState.value = PermitUiState.Loading

            // 심사 기간용
            if (sessionManager.isReviewPeriod()) {
                sessionManager.saveToken(
                    access = REVIEW_ACCESS_TOKEN,
                    refresh = "",
                    expired = REVIEW_EXPIRE_AT
                )
                _permitUiState.value = PermitUiState.Success
                return@launch
            }

            try {
                val token = sessionManager.getToken() // 액세스 토큰 조회
                val isExpired = sessionManager.isTokenExpired() // 토큰 만료 확인
                if (token != null && !isExpired) {
                    _permitUiState.value = PermitUiState.Success
                } else {
                    _permitUiState.value = PermitUiState.Error("토큰 만료") // 로그인 필요
                }
            } catch (e: Exception) {
                _permitUiState.value = PermitUiState.Error("토큰 확인 실패: ${e.message}")
            }
        }
    }

    // 회원가입
    fun signup(tempCode: String) {
        saveToken(tempCode, isNewUser = true)
    }

    // 로그인
    fun login(tempCode: String) {
        saveToken(tempCode, isNewUser = false)
    }

    // 공통 로직
    fun saveToken(tempCode: String, isNewUser: Boolean) {
        viewModelScope.launch {
            _permitUiState.value = PermitUiState.Loading

            try {
                val jwt = repository.login(tempCode) // api 호출

                // 세션 저장
                sessionManager.saveToken(
                    jwt.accessToken,
                    jwt.refreshToken,
                    jwt.accessTokenExpiredAt
                )

                // 신규 유저인 경우 플래그 설정
                if (isNewUser) sessionManager.saveNewUser(true)

                _permitUiState.value = PermitUiState.Success
            } catch (e: Exception) {
                _permitUiState.value = PermitUiState.Error("로그인 실패: ${e.message}")
            }
        }
    }

    // 상태 초기화
    fun resetState() {
        _permitUiState.value = PermitUiState.Idle
    }
}