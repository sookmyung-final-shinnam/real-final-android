package com.veryshinnam.myapp.feature.creation.content.selection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionCustomInput
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionItemGrid
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionTripleButtons

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
    modifier: Modifier
) {
    var customInput by remember { mutableStateOf("") }
    val initBackgrounds = listOf("숲 속","바다","왕국","학교","집","우주")

    Column(modifier = modifier) {
        if (isInputMode) { // 직접추가 버튼 누름에 따라 입력창 또는 그리드 셀 모드
            SelectionCustomInput(
                value = customInput,
                onValueChange = { customInput = it },
                onConfirm = { input ->
                    when {
                        input.isBlank() -> onSimpleWarning("아직 배경을 입력하지 않았어요!")
                        input in initBackgrounds -> onSimpleWarning("이미 존재하는 배경이에요!")
                        else -> {
                            onCustomBackgroundInput(input)
                            customInput = ""
                            onInputModeChange(false)
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            SelectionItemGrid(
                items = initBackgrounds,
                selectedItems = listOf(background).filter { it.isNotBlank() },
                customItem = customBackground,
                onItemClick = { onBackgroundSelect(it) },
                modifier = Modifier.fillMaxWidth().weight(8f)
            )

            // 하단 버튼 영역
            SelectionTripleButtons(
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