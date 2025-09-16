package com.veryshinnam.myapp.feature.creation.select.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.AppTopBar
import com.veryshinnam.myapp.component.common.BackButton
import com.veryshinnam.myapp.component.common.LoadErrorView
import com.veryshinnam.myapp.feature.creation.conversation.ui.ConversationUiState
import com.veryshinnam.myapp.feature.creation.model.SelectionData
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectCustomInput
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectItemGrid
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons


// 캐릭터 생성 > 선택 진입점
@Composable
fun SelectionThemeContent(
    themes: List<String>,              // 선택된 테마들
    customTheme: String,               // 직접 추가된 테마
    isInputMode: Boolean,                   // 입력 모드
    onInputModeChange: (Boolean) -> Unit,   // 입력 모드 변경 콜백
    onCustomThemeInput: (String) -> Unit,     // 테마 직접 추가 콜백
    onThemeSelect: (String) -> Unit,        // 테마 선택 콜백
    onNextClick: () -> Unit,                // 다음 단계 이동 콜백
    modifier: Modifier
) {
    var customInput by remember { mutableStateOf("") }
    val initThemes = listOf("로맨스", "모험", "일상", "공포", "우정", "추리")

    Column(modifier = modifier) {
        if (isInputMode) { // 직접추가 버튼 누름에 따라 입력창 또는 그리드 셀 모드
            SelectCustomInput(
                value = customInput,
                onValueChange = { customInput = it },
                onConfirm = {
                    if (customInput.isNotBlank() && customInput !in initThemes) {
                        onCustomThemeInput(customInput)
                        customInput = ""
                        onInputModeChange(false)
                    } else {
                        // TODO: 이미 있는 주제
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            SelectItemGrid(
                items = initThemes,
                selectedItems = themes,
                customItem = customTheme, // read
                onItemClick = { onThemeSelect(it) }, // write
                modifier = Modifier.fillMaxWidth().weight(8f)
            )

            // 하단 버튼 영역
            SelectTripleButtons(
                isLeft = false,    // 첫 화면이니 이전 버튼 없음
                isCenter = true,   // 직접추가 버튼
                isRight = true,    // 다음 버튼
                onCenterClick = {
                    if (customTheme.isBlank() && themes.size < 3) {
                        onInputModeChange(true) // 직접추가 입력창 열기
                    } else {
                        // TODO: 3개까지만 선택 가능
                    }
                },
                onRightClick = {
                    if (themes.isNotEmpty()) {
                        onNextClick() // 다음 단계로 이동
                    } else {
                        // TODO: 주제를 하나 이상 선택
                    }
                },
                modifier = Modifier.fillMaxWidth().weight(2f)
            )
        }
    }
}