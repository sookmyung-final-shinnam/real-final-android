package com.veryshinnam.myapp.feature.creation.content.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.common.model.DashboardInit
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionCustomInput
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionItemGrid
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionBottomButtons
import com.veryshinnam.myapp.feature.creation.model.SelectionInputError
import com.veryshinnam.myapp.feature.creation.model.validateSelectionInput

@Composable
fun SelectionBackgroundContent (
    background: String,              // 선택된 배경들
    customBackground: String,               // 직접추가된 배경
    isInputMode: Boolean,                    // 입력 모드
    onInputModeChange: (Boolean) -> Unit,    // 입력 모드 변경 콜백
    onCustomBackgroundInput: (String) -> Unit, // 배경 직접추가 콜백
    onBackgroundSelect: (String) -> Unit,    // 배경 선택 콜백
    onPrevClick: () -> Unit,                 // 이전 단계 이동 콜백
    onNextClick: () -> Unit,                 // 다음 단계 이동 콜백
    onSimpleWarning: (String) -> Unit,    // 경고 콜백
    modifier: Modifier,
    spacePadding: Dp = 24.dp
) {
    var customInput by remember { mutableStateOf("") }
    val initBackgrounds = DashboardInit.BACKGROUNDS

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // 사이 간격
    ) {
        if (isInputMode) { // 직접추가 버튼 누름에 따라 입력창 또는 그리드 셀 모드
            SelectionCustomInput(
                value = customInput,
                onValueChange = { customInput = it },
                onConfirm = { input ->
                    val error = validateSelectionInput(input)

                    when {
                        error == SelectionInputError.EMPTY ->
                            onSimpleWarning("아직 배경을 입력하지 않았어요!")

                        error == SelectionInputError.LENGTH ->
                            onSimpleWarning("배경은 1~15자로 입력해 주세요!")

                        error == SelectionInputError.EXIST_JAMO ->
                            onSimpleWarning("자음 또는 모음만 입력할 수 없어요!")

                        error == SelectionInputError.NUMBER_NOT_ALLOWED ->
                            onSimpleWarning("숫자는 사용할 수 없어요!")

                        error == SelectionInputError.SPECIAL_CHAR ->
                            onSimpleWarning("한글만 입력 가능해요!")

                        input.trim() in initBackgrounds ->
                            onSimpleWarning("이미 존재하는 배경이에요!")

                        else -> {
                            onCustomBackgroundInput(input.trim())
                            customInput = ""
                            onInputModeChange(false)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.85f)
             )
        } else {
            SelectionItemGrid(
                items = initBackgrounds,
                selectedItems = listOf(background).filter { it.isNotBlank() },
                customItem = customBackground,
                onItemClick = { onBackgroundSelect(it) },
                modifier = Modifier.fillMaxWidth().weight(8f)
            )

            Spacer(Modifier.height(spacePadding))

            // 하단 버튼 영역
            SelectionBottomButtons(
                isLeft = true,     // 이전 버튼
                isCenter = true,   // 직접추가 버튼
                isRight = true,    // 다음 버튼
                onLeftClick = { onPrevClick() },
                onCenterClick = {
                    // 직접추가 배경 값 비어있으면, 직접추가 입력창 열기
                    if (customBackground.isBlank()) onInputModeChange(true)
                },
                onRightClick = {
                    if (background.isNotBlank()) {
                        onNextClick()
                    } else {
                        onSimpleWarning("아직 배경을 선택하지 않았어요!")
                    }
                },
                modifier = Modifier.fillMaxWidth().weight(2f)
            )
        }
    }
}