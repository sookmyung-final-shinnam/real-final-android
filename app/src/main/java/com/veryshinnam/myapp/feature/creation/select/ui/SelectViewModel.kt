package com.veryshinnam.myapp.feature.creation.select.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest
import com.veryshinnam.myapp.feature.creation.model.Gender
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

    // 나이 스크롤 상태
    val ageListState: LazyListState = LazyListState(
        firstVisibleItemIndex = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % 100) + 5
    )

    // 테마 선택/해제
    fun selectTheme(theme: String) {
        val current = _selectUiState.value
        val updated = current.themes.toMutableList()

        if (updated.contains(theme)) {
            updated.remove(theme) // 이미 있으면 선택 해제

            // 만약 해제한 게 customTheme라면 customTheme도 초기화
            val updatedCustom = if (theme == current.customTheme) "" else current.customTheme
            _selectUiState.update { it.copy(
                themes = updated,
                customTheme = updatedCustom
            ) }
        } else if (updated.size < 3) {
            updated.add(theme)  // 개수 초과되지 않으면 추가
            _selectUiState.update { it.copy(themes = updated) }
        }
    }

    // 테마 직접추가
    fun addCustomTheme(value: String) {
        _selectUiState.update { current -> current.copy(customTheme = value) }
        selectTheme(value) // 추가 후 자동 선택
    }

    // 배경 선택/해제
    fun selectBackground(value: String) {
        _selectUiState.update { state ->
            // 선택 해제
            if (state.background == value) {
                // 직접추가 배경 해제
                if (state.customBackground == value) {
                    state.copy(
                        customBackground = "",
                        background = ""
                    )
                } else {
                    // 일반 배경 해제
                    state.copy(background = "")
                }
            }
            // 선택
            else {
                state.copy(background = value)
            }
        }
    }

    // 배경 직접추가
    fun addCustomBackground(value: String) {
        _selectUiState.update {
            it.copy(
                customBackground = value,
                background = value
            )
        }
    }

    // 성별
    fun selectGender(value: Gender) { _selectUiState.update { it.copy(gender = value) } }

    // 나이
    fun selectAge(value: Int) { _selectUiState.update { it.copy(age = value.coerceIn(1, 100)) } }

    // 이름
    fun selectName(value: String) { _selectUiState.update { it.copy(name = value.trim()) } }

    // 외형
    fun selectEyeColor(value: String)  { _selectUiState.update { it.copy(eyeColor = value) } }
    fun selectHairColor(value: String) { _selectUiState.update { it.copy(hairColor = value) } }
    fun selectHairStyle(value: String) { _selectUiState.update { it.copy(hairStyle = value) } }

    // 대화 시작 (동화 생성)
    fun createStartDialogRequest(): StartConversationRequest {
        val currentState = _selectUiState.value
        return StartConversationRequest(
            themeNames = currentState.themes,
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