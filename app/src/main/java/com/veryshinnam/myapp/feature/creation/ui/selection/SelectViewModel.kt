package com.veryshinnam.myapp.feature.creation.ui.selection

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import com.veryshinnam.myapp.common.enums.Gender
import com.veryshinnam.myapp.feature.creation.model.SelectionData
import com.veryshinnam.myapp.feature.creation.model.SelectionStep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SelectViewModel @Inject constructor() : ViewModel() {

    // 선택 화면 상태 관리
    private val _selectUiState = MutableStateFlow(SelectUiState(selectionData = SelectionData()))
    val selectUiState: StateFlow<SelectUiState> = _selectUiState

    // 나이 스크롤 상태 기억
    val ageListState: LazyListState = LazyListState(
        firstVisibleItemIndex = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % 100) + 5
    )

    // 테마 선택/해제
    fun selectTheme(theme: String) {
        val current = _selectUiState.value.selectionData
        val updated = current.themes.toMutableList()

        if (updated.contains(theme)) {
            updated.remove(theme) // 이미 있으면 선택 해제

            // 만약 해제한 게 customTheme라면 customTheme도 초기화
            val updatedCustom = if (theme == current.customTheme) "" else current.customTheme
            _selectUiState.update {
                it.copy(
                    selectionData = current.copy(
                        themes = updated,
                        customTheme = updatedCustom
                    )
                )
            }
        } else if (updated.size < 3) {
            updated.add(theme)  // 개수 초과되지 않으면 추가
            _selectUiState.update {
                it.copy(selectionData = current.copy(themes = updated))
            }
        }
    }

    // 테마 직접추가
    fun addCustomTheme(value: String) {
        _selectUiState.update {
            it.copy(selectionData = it.selectionData.copy(customTheme = value))
        }
        selectTheme(value) // 추가 후 자동 선택
    }

    // 배경 선택/해제
    fun selectBackground(value: String) {
        val current = _selectUiState.value.selectionData

        _selectUiState.update {
            // 선택 해제
            if (current.background == value) {
                // 직접추가 배경 해제
                if (current.customBackground == value) {
                    it.copy(selectionData = current.copy(customBackground = "", background = ""))
                } else {
                    // 일반 배경 해제
                    it.copy(selectionData = current.copy(background = ""))
                }
            }
            // 선택
            else {
                it.copy(selectionData = current.copy(background = value))
            }
        }
    }

    // 배경 직접추가
    fun addCustomBackground(value: String) {
        _selectUiState.update {
            it.copy(
                selectionData = it.selectionData.copy(
                    customBackground = value,
                    background = value
                )
            )
        }
    }

    // 성별
    fun selectGender(value: Gender) {
        _selectUiState.update {
            it.copy(selectionData = it.selectionData.copy(gender = value))
        }
    }

    // 나이
    fun selectAge(value: Int) {
        _selectUiState.update {
            it.copy(selectionData = it.selectionData.copy(age = value.coerceIn(1, 100)))
        }
    }

    // 이름
    fun selectName(value: String) {
        _selectUiState.update {
            it.copy(selectionData = it.selectionData.copy(name = value.trim()))
        }
    }

    // 외형
    fun selectEyeColor(value: String) {
        _selectUiState.update {
            it.copy(selectionData = it.selectionData.copy(eyeColor = value))
        }
    }

    fun selectHairColor(value: String) {
        _selectUiState.update {
            it.copy(selectionData = it.selectionData.copy(hairColor = value))
        }
    }

    fun selectHairStyle(value: String) {
        _selectUiState.update {
            it.copy(selectionData = it.selectionData.copy(hairStyle = value))
        }
    }

    // 이전 단계 이동
    fun goToPrevStep() {
        _selectUiState.update { state ->
            val prevStep = when (state.selectionStep) {
                SelectionStep.BACKGROUND -> SelectionStep.THEME
                SelectionStep.GENDER -> SelectionStep.BACKGROUND
                SelectionStep.AGE -> SelectionStep.GENDER
                SelectionStep.NAME -> SelectionStep.AGE
                SelectionStep.FACE -> SelectionStep.NAME
                SelectionStep.THEME -> SelectionStep.THEME
            }
            state.copy(
                selectionStep = prevStep,
                currentStep = (state.currentStep - 1).coerceAtLeast(1)
            )
        }
    }

    // 다음 단계 이동
    fun goToNextStep() {
        _selectUiState.update { state ->
            val nextStep = when (state.selectionStep) {
                SelectionStep.THEME -> SelectionStep.BACKGROUND
                SelectionStep.BACKGROUND -> SelectionStep.GENDER
                SelectionStep.GENDER -> SelectionStep.AGE
                SelectionStep.AGE -> SelectionStep.NAME
                SelectionStep.NAME -> SelectionStep.FACE
                SelectionStep.FACE -> SelectionStep.FACE
            }

            state.copy(
                selectionStep = nextStep,
                currentStep = (state.currentStep + 1).coerceAtMost(6)
            )
        }
    }
}
