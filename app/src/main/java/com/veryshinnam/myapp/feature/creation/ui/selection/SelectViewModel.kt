package com.veryshinnam.myapp.feature.creation.ui.selection

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import com.veryshinnam.myapp.common.model.Gender
import com.veryshinnam.myapp.common.model.ManualData
import com.veryshinnam.myapp.common.model.ManualTarget
import com.veryshinnam.myapp.core.manual.ManualManager
import com.veryshinnam.myapp.feature.creation.model.SelectionData
import com.veryshinnam.myapp.feature.creation.model.SelectionStep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SelectViewModel @Inject constructor(
    private val manualManager: ManualManager
) : ViewModel() {

    // 화면 전체 ui 상태
    private val _selectUiState = MutableStateFlow(SelectUiState(selectionData = SelectionData()))
    val selectUiState: StateFlow<SelectUiState> = _selectUiState

    // 나이 스크롤 상태
    private val _ageListState = LazyListState(
        firstVisibleItemIndex = Int.MAX_VALUE / 2
    )
    val ageListState: LazyListState = _ageListState

    // ManualManager 구독
    val manualState = manualManager.state
    val manualMessage = manualManager.message

    // 매뉴얼 진행 단계 상태
    private val _manualStep = MutableStateFlow(0)
    val manualStep = _manualStep.asStateFlow()

    // --- ui 이벤트 관련 ---
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

    // 나이 증가
    fun increaseAge() {
        val current = _selectUiState.value.selectionData.age
        selectAge(if (current >= 100) 1 else current + 1)
    }

    // 나이 감소
    fun decreaseAge() {
        val current = _selectUiState.value.selectionData.age
        selectAge(if (current <= 1) 100 else current - 1)
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

    // --- 매뉴얼 관련 ---
    // 생성 전 선택 화면 사용 매뉴얼
    val manuals = listOf(
        ManualData("이곳에서 동화와 캐릭터를 만들 수 있어요.", ManualTarget.NONE),
        ManualData("동화의 주제·배경부터 캐릭터의 성별·나이·이름·외형까지 고를 수 있어요!", ManualTarget.PROGRESSBAR),
        ManualData("함께 떠나는 여정 이야기를 만들어 보고 싶어서 이 주제를 골라볼게요!", ManualTarget.BUTTON),
        ManualData("만약 주제를 직접 선택하고 싶다면 아래 키보드를 눌러 입력해도 돼요!", ManualTarget.CUSTOM),
    )

    fun startManual() {
        _manualStep.value = 0
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

    fun clearManual() = manualManager.clear()
}
