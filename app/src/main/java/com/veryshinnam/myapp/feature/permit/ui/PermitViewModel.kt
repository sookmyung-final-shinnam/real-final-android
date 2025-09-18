package com.veryshinnam.myapp.feature.permit.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    // 액세스 토큰 만료 확인
    fun checkAccessToken() {
        viewModelScope.launch {
            _permitUiState.value = PermitUiState.Loading
            try {
                val token = sessionManager.getToken() // 액세스 토큰 조회
                val isExpired = sessionManager.isTokenExpired() // 토큰 만료 확인
                if (token != null && !isExpired) {
                    _permitUiState.value = PermitUiState.Success
                } else {
                    _permitUiState.value = PermitUiState.Error("토큰 만료") // 재로그인 필요
                }
            } catch (e: Exception) {
                _permitUiState.value = PermitUiState.Error("토큰 확인 실패: ${e.message}")
            }
        }
    }

    fun login(tempCode: String) {
        viewModelScope.launch {
            _permitUiState.value = PermitUiState.Loading
            try {
                val jwt = repository.login(tempCode) // 로그인 api 요청
                Log.d("PermitViewModel", "로그인 성공: $jwt")

                // 세션 저장
                sessionManager.saveToken(
                    jwt.accessToken,
                    jwt.refreshToken,
                    jwt.accessTokenExpiredAt
                )
                _permitUiState.value = PermitUiState.Success
            } catch (e: Exception) {
                Log.e("PermitViewModel", "로그인 실패", e)
                _permitUiState.value = PermitUiState.Error("로그인 실패: ${e.message}")
            }
        }
    }
}