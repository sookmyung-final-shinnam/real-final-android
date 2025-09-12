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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectCustomInput
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectItemGrid
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons


// 캐릭터 생성 - 테마 선택 진입점
@Composable
fun SelectThemeScreen(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    vm: SelectViewModel = hiltViewModel()
) {
    val uiState by vm.selectUiState.collectAsState()

    var isInputMode by remember { mutableStateOf(false) }
    var customInput by remember { mutableStateOf("") }

    val horizontalPadding = 16.dp
    val themes = listOf("로맨스", "모험", "일상", "공포", "우정", "추리")
    LaunchedEffect(uiState.selectedThemes) {
        Log.d("SelectScreen", "현재 선택된 테마: ${uiState.selectedThemes}")
    }

    // 뒤로가기 동작 제어
    BackHandler {
        if (isInputMode) {
            // 입력 모드일 때는 모드만 해제
            isInputMode = false
        } else {
            // 일반 모드일 때는 원래 뒤로가기 동작
            onBackClick()
        }
    }

    // 화면
    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = { AppTopBar() }, // 상단 로고
        contentWindowInsets = WindowInsets.navigationBars // 네비게이션 여백
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            BackButton(
                onBackClick = {
                    if (isInputMode) {
                        isInputMode = false // 입력 모드 해제
                    } else {
                        onBackClick()       // 원래 뒤로가기
                    }},
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
            )

            // TODO: 프로그레스바

            Column(modifier = Modifier.fillMaxSize()
                .padding(horizontal = horizontalPadding)
            ) {
                // 현재 스크린 설명
                SelectInfo(
                    text = if (isInputMode) {
                        "원하는 주제를 입력해줘!" // 입력창 모드
                    } else {
                        "만들고 싶은 동화의 주제를 3개까지 골라봐!" // 그리드 셀 모드
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25f)
                )

                // 직접추가 버튼 누름에 따라 입력창 또는 그리드 셀 모드
                if (isInputMode) {
                    SelectCustomInput(
                        value = customInput,
                        onValueChange = { customInput = it },
                        onConfirm = {
                            if (customInput.isNotBlank() && customInput !in themes) {
                                vm.addCustomTheme(customInput)
                                customInput = ""
                                isInputMode = false
                            } else {
                                // TODO: 이미 있는 주제
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.75f)
                    )
                } else {
                    SelectItemGrid(
                        items = themes,
                        selectedItems = uiState.selectedThemes,
                        customItem = uiState.customTheme, // vm 상태 read
                        maxSelectCount = 3,
                        onItemClick = { vm.selectTheme(it) }, // vm 상태 write
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.55f)
                    )

                    // 하단 버튼 영역
                    SelectTripleButtons(
                        isLeft = false,    // 첫 화면이니 이전 버튼 없음
                        isCenter = true,   // 직접추가 버튼
                        isRight = true,    // 다음 버튼
                        onCenterClick = {
                            if (uiState.customTheme.isBlank() && uiState.selectedThemes.size < 3) {
                                isInputMode = true // 직접추가 입력창 열기
                            } else {
                                // TODO: 3개까지만 선택 가능
                            }
                        },
                        onRightClick = {
                            if (uiState.selectedThemes.isNotEmpty()) {
                                onNextClick() // 다음 단계로 이동
                            } else {
                                // TODO: 주제를 하나 이상 선택
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.2f),
                    )
                }
            }
        }
    }
}