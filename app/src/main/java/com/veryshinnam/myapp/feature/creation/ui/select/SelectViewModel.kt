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

    // 테마 선택/해제
    fun selectTheme(theme: String) {
        val current = _selectUiState.value.selectedThemes.toMutableList()

        if (current.contains(theme)) {
            current.remove(theme)
        } else if (current.size < 3) {
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

    // 배경
    fun setBackground(value: String) {
        _selectUiState.update { it.copy(background = value) }
    }

    // 성별
    fun setGender(value: String) {
        _selectUiState.update { it.copy(gender = value) }
    }

    // 나이
    fun setAge(value: Int) {
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
            backgroundName = currentState.background,
            characterName = currentState.name,
            characterAge = currentState.age,
            gender = currentState.gender,
            eyeColor = currentState.eyeColor,
            hairColor = currentState.hairColor,
            hairStyle = currentState.hairStyle
        )
    }
}