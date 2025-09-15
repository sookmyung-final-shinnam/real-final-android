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
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.AppTopBar
import com.veryshinnam.myapp.component.common.BackButton
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectCustomInput
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectItemGrid
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons

// 캐릭터 생성 - 배경 선택 진입점
@Composable
fun SelectBackgroundScreen (
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    vm: SelectViewModel
) {

    val uiState by vm.selectUiState.collectAsState()

    var isInputMode by remember { mutableStateOf(false) }
    var customInput by remember { mutableStateOf("") }

    val horizontalPadding = 16.dp
    val backgrounds = listOf("숲 속","바다","왕국","학교","집","우주")

    LaunchedEffect(uiState.background) {
        Log.d("SelectScreen", "현재 선택된 배경: ${uiState.background}")
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

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = horizontalPadding)
            ) {
                // 현재 스크린 설명
                SelectInfo(
                    text = if (isInputMode) {
                        "원하는 배경을 입력해 줘!" // 입력창 모드
                    } else {
                        "만들고 싶은 동화의 배경을 골라봐!" // 그리드 셀 모드
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
                            if (customInput.isNotBlank() && customInput !in backgrounds) {
                                vm.addCustomBackground(customInput)
                                customInput = ""
                                isInputMode = false
                            } else {
                                // TODO: 이미 있는 배경
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.75f)
                    )
                } else {
                    SelectItemGrid(
                        items = backgrounds,
                        selectedItems = listOf(uiState.background).filter { it.isNotBlank() },
                        customItem = uiState.customBackground,
                        onItemClick = { vm.selectBackground(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.55f)
                    )

                    // 하단 버튼 영역
                    SelectTripleButtons(
                        isLeft = true,     // 이전 버튼
                        isCenter = true,   // 직접추가 버튼
                        isRight = true,    // 다음 버튼
                        onLeftClick = { onBackClick() },
                        onCenterClick = {
                            // 직접추가 배경 값 비어있으면, 직접추가 입력창 열기
                            if (uiState.customBackground.isBlank()) isInputMode = true
                        },
                        onRightClick = {
                            if (uiState.background.isNotBlank()) {
                                onNextClick()
                            } else {
                                // TODO: 배경 선택하세요
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