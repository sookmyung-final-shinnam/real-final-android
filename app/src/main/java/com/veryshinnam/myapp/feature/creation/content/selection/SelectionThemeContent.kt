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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.common.model.DashboardInit
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionCustomInput
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionItemGrid
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionBottomButtons


// 캐릭터 생성 > 선택 진입점
@Composable
fun SelectionThemeContent(
    modifier: Modifier,
    themes: List<String>,              // 선택된 테마들
    customTheme: String,               // 직접 추가된 테마
    isInputMode: Boolean,                   // 입력 모드
    onInputModeChange: (Boolean) -> Unit,   // 입력 모드 변경 콜백
    onCustomThemeInput: (String) -> Unit,     // 테마 직접 추가 콜백
    onThemeSelect: (String) -> Unit,        // 테마 선택 콜백
    onNextClick: () -> Unit,                // 다음 단계 이동 콜백
    onSimpleWarning: (String) -> Unit,    // 경고 콜백
    onFirstBRect: (Rect) -> Unit,
    onCustomBRect: (Rect) -> Unit,
    spacePadding: Dp = 24.dp
) {
    var customInput by remember { mutableStateOf("") }
    val initThemes = DashboardInit.THEMES

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (isInputMode) { // 직접추가 버튼 누름에 따라 입력창 또는 그리드 셀 모드
            SelectionCustomInput(
                value = customInput,
                onValueChange = { customInput = it },
                onConfirm = { input ->
                    when {
                        input.isBlank() -> onSimpleWarning("아직 주제를 입력하지 않았어요!")
                        input in initThemes -> onSimpleWarning("이미 존재하는 주제예요!")
                        else -> {
                            onCustomThemeInput(input)
                            customInput = ""
                            onInputModeChange(false)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.85f)
            )
        } else {
            SelectionItemGrid(
                items = initThemes,
                selectedItems = themes,
                customItem = customTheme, // read
                onItemClick = { onThemeSelect(it) }, // write
                onFirstBRect = onFirstBRect,
                modifier = Modifier.fillMaxWidth().weight(8f)
            )

            Spacer(Modifier.height(spacePadding))

            // 하단 버튼 영역
            SelectionBottomButtons(
                isLeft = false,    // 첫 화면이니 이전 버튼 없음
                isCenter = true,   // 직접추가 버튼
                isRight = true,    // 다음 버튼
                onCenterClick = {
                    if (customTheme.isBlank() && themes.size < 3) {
                        onInputModeChange(true) // 직접추가 입력창 열기
                    } else if (themes.size == 3) {
                        onSimpleWarning("주제는 3개까지 선택이 가능해요!")

                    } else {
                        onSimpleWarning("이미 원하는 주제를 입력했어요!\n\n다른 주제를 추가하려면 선택 해제 후 다시 입력해 주세요.")
                    }
                },
                onRightClick = {
                    if (themes.isNotEmpty()) {
                        onNextClick() // 다음 단계로 이동
                    } else {
                        onSimpleWarning("아직 주제를 선택하지 않았어요!")
                    }
                },
                onCustomBRect = onCustomBRect,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            )
        }
    }
}