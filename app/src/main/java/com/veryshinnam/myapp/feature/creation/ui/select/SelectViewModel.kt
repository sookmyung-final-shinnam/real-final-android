package com.veryshinnam.myapp.feature.creation.ui.select

import androidx.lifecycle.ViewModel
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SelectViewModel @Inject constructor() : ViewModel() {
    
    // 선택 화면 상태 관리
    private val _selectUiState = MutableStateFlow(SelectUiState())
    val selectUiState: StateFlow<SelectUiState> = _selectUiState

    // 테마 선택 업데이트
    fun selectTheme(theme: String) {
        val current = _selectUiState.value.selectedThemes.toMutableList()

        if (current.contains(theme)) {
            // 이미 있으면 선택 제거
            current.remove(theme)
        } else if (current.size < 3) {
            // 개수 초과되지 않으면 추가
            current.add(theme)
        }

        _selectUiState.update { it.copy(selectedThemes = current) }
    }

    // 직접추가 테마 추가
    fun addCustomTheme(index: Int, value: String) {
        _selectUiState.update { current ->
            val updated = current.customThemes.toMutableMap()
            updated[index] = value
            current.copy(customThemes = updated)
        }

        selectTheme(value)
    }

    // 직접추가 테마 삭제
    fun removeCustomTheme(value: String) {
        _selectUiState.update { state ->
            val updatedCustoms = state.customThemes.filterValues { it != value }
            val updatedThemes = state.selectedThemes.filterNot { it == value }
            state.copy(customThemes = updatedCustoms, selectedThemes = updatedThemes)
        }
    }

    // 배경 선택/해제
    fun selectBackground(value: String) {
        _selectUiState.update { state ->
            // 선택 해제
            if (state.selectedBackground == value) {
                // 직접추가 배경 해제
                if (state.customBackground == value) {
                    state.copy(
                        selectedBackground = "",
                        customBackground = null
                    )
                } else {
                    // 일반 배경 해제
                    state.copy(selectedBackground = "")
                }
            }
            // 선택
            else {
                state.copy(selectedBackground = value)
            }
        }
    }

    // 직접추가 배경 추가
    fun addCustomBackground(value: String) {
        _selectUiState.update {
            it.copy(
                customBackground = value,
                selectedBackground = value 
            )
        }
    }

    // 성별
    fun selectGender(value: String) {
        _selectUiState.update { it.copy(gender = value) }
    }

    // 나이
    fun selectAge(value: Int) {
        _selectUiState.update { it.copy(age = value.coerceIn(1, 100)) }
    }

    // 이름
    fun setName(value: String) {
        _selectUiState.update { it.copy(name = value.trim()) }
    }

    // 외형
    fun setEyeColor(value: String)  { _selectUiState.update { it.copy(eyeColor = value) } }
    fun setHairColor(value: String) { _selectUiState.update { it.copy(hairColor = value) } }
    fun setHairStyle(value: String) { _selectUiState.update { it.copy(hairStyle = value) } }

    // 대화 시작 (동화 생성)
    fun createStartDialogRequest(): StartConversationRequest {
        val currentState = _selectUiState.value
        return StartConversationRequest(
            themeNames = currentState.selectedThemes,
            backgroundName = currentState.selectedBackground,
            characterName = currentState.name,
            characterAge = currentState.age,
            gender = currentState.gender,
            eyeColor = currentState.eyeColor,
            hairColor = currentState.hairColor,
            hairStyle = currentState.hairStyle
        )
    }
}