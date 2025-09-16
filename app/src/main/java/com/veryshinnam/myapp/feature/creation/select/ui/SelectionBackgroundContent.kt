package com.veryshinnam.myapp.feature.creation.select.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.veryshinnam.myapp.feature.creation.model.SelectionData
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectCustomInput
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectItemGrid
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons

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
    modifier: Modifier
) {
    var customInput by remember { mutableStateOf("") }
    val initBackgrounds = listOf("숲 속","바다","왕국","학교","집","우주")

    Column(modifier = modifier) {
        if (isInputMode) { // 직접추가 버튼 누름에 따라 입력창 또는 그리드 셀 모드
            SelectCustomInput(
                value = customInput,
                onValueChange = { customInput = it },
                onConfirm = {
                    if (customInput.isNotBlank() && customInput !in initBackgrounds) {
                        onCustomBackgroundInput(customInput)
                        customInput = ""
                        onInputModeChange(false)
                    } else {
                        // TODO: 이미 있는 배경
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            SelectItemGrid(
                items = initBackgrounds,
                selectedItems = listOf(background).filter { it.isNotBlank() },
                customItem = customBackground,
                onItemClick = { onBackgroundSelect(it) },
                modifier = Modifier.fillMaxWidth().weight(8f)
            )

            // 하단 버튼 영역
            SelectTripleButtons(
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
                        // TODO: 배경 선택하세요
                    }
                },
                modifier = Modifier.fillMaxWidth().weight(2f)
            )
        }
    }
}