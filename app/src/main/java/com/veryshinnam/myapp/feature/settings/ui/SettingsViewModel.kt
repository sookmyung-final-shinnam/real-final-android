package com.veryshinnam.myapp.feature.settings.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.common.model.WarningConfirmState
import com.veryshinnam.myapp.common.model.WarningState
import com.veryshinnam.myapp.core.session.SessionManager
import com.veryshinnam.myapp.feature.settings.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    // 화면 전체 ui 상태
    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    // 확인 버튼이 있는 경고창 상태
    private val _confirmWarning = MutableStateFlow(WarningConfirmState())
    val confirmWarning = _confirmWarning.asStateFlow()

    // 단순 경고창 상태
    private val _warning = MutableStateFlow(WarningState())
    val warning = _warning.asStateFlow()

    // --- ui 이벤트 관련 ---
    // 확인 버튼이 있는 경고창 열기
    fun showConfirmWarning(
        warningText: String,
        confirmText: String,
        onConfirm: () -> Unit
    ) {
        _confirmWarning.value = WarningConfirmState(
            isVisible = true,
            warningText = warningText,
            confirmText = confirmText,
            onConfirm = onConfirm
        )
    }

    // 확인 버튼이 있는 경고창 닫기
    fun hideConfirmWarning() {
        _confirmWarning.value = WarningConfirmState()
    }

    // 단순 경고창 열기
    fun showWarning(warningText: String) {
        _warning.value = WarningState(
            isVisible = true,
            warningText = warningText
        )
    }

    // 단순 경고창 닫기
    fun hideWarning() {
        _warning.value = WarningState()
    }

    // --- api 호출 관련 ---
    // 로그아웃
    fun logout() {
        viewModelScope.launch {
            try {
                repository.logout() // api 호출
            } catch (e: Exception) {
                Log.e("user error", "user > logout error > ${e.message}")
                _uiState.value = SettingsUiState.Error("로그아웃 실패: ${e.message}")
            }  finally {
                _uiState.value = SettingsUiState.Success
                delay(3000)
                sessionManager.clearKakaoWebViewSession()
                sessionManager.clearToken() // 토큰 삭제 + _requireLogin 트리거
            }
        }
    }

    // 회원 탈퇴
    fun withdraw() {
        viewModelScope.launch {
            try {
                repository.withdraw() // api 호출
            } catch (e: Exception) {
                Log.e("user error", "user > withdraw error > ${e.message}")
                _uiState.value = SettingsUiState.Error("회원 탈퇴 실패: ${e.message}")
            } finally {

                _uiState.value = SettingsUiState.Success
                delay(3000)
                sessionManager.clearKakaoWebViewSession()
                sessionManager.clearToken() // 토큰 삭제 + _requireLogin 트리거
            }
        }
    }
}
