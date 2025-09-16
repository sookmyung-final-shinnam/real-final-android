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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.AppTopBar
import com.veryshinnam.myapp.component.common.BackButton
import com.veryshinnam.myapp.feature.creation.model.SelectionData
import com.veryshinnam.myapp.feature.creation.model.SelectionStep
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectInfo


// 캐릭터 생성 > 선택 진입점
@Composable
fun SelectionScreen(
    onHome: () -> Unit,
    onFinish: (SelectionData) -> Unit,
    vm: SelectViewModel,
    horizontalPadding: Dp = 16.dp
) {
    val uiState by vm.selectUiState.collectAsState()             // 선택 화면 상태 관리
    val selectionStep = uiState.selectionStep                    // 현재 선택 단계
    var isInputMode by remember { mutableStateOf(false) } // 테마+배경 직접추가 입력 모드

    // 뒤로 가기 로직
    fun handleBack() {
        if (isInputMode) {
            isInputMode = false // 입력모드 해제
        } else {
            if (uiState.selectionStep == SelectionStep.THEME) {
                onHome() // 테마 선택 화면은 홈으로
                // TODO: 저장 X
            } else {
                vm.goToPrevStep()
            }
        }
    }

    // 설명 텍스트 로직
    fun getInfoText(step: SelectionStep, isInputMode: Boolean): String {
        return if (isInputMode) {
            when (step) {
                SelectionStep.THEME -> "원하는 주제를 입력해줘!"
                SelectionStep.BACKGROUND -> "원하는 배경을 입력해줘!"
                else -> ""
            }
        } else {
            when (step) {
                SelectionStep.THEME -> "만들고 싶은 동화의 주제를 3개까지 골라봐!"
                SelectionStep.BACKGROUND -> "만들고 싶은 동화의 배경을 골라봐!"
                SelectionStep.GENDER -> "동화 속 주인공의 성별은 어떻게 할까?"
                SelectionStep.AGE -> "동화 속 주인공의 나이는 어떻게 할까?"
                SelectionStep.NAME -> "동화 속 주인공의 이름은 어떻게 할까?"
                SelectionStep.FACE -> "동화 속 주인공의 모습은 어떻게 할까?"
            }
        }
    }

    LaunchedEffect(uiState.selectionData.themes) {
        Log.d("SelectScreen", "현재 선택된 테마: ${uiState.selectionData.themes}")
    }
    LaunchedEffect(uiState.selectionData.background) {
        Log.d("SelectScreen", "현재 선택된 배경: ${uiState.selectionData.background}")
    }
    LaunchedEffect(uiState.selectionData.gender) {
        Log.d("SelectScreen", "현재 선택된 성별: ${uiState.selectionData.gender}")
    }
    LaunchedEffect(uiState.selectionData.age) {
        Log.d("SelectScreen", "현재 선택된 나이: ${uiState.selectionData.age}")
    }
    LaunchedEffect(uiState.selectionData.name) {
        Log.d("SelectScreen", "현재 선택된 이름: ${uiState.selectionData.name}")
    }
    LaunchedEffect(uiState.selectionData.eyeColor) {
        Log.d("SelectScreen", "현재 선택된 눈색: ${uiState.selectionData.eyeColor}")
    }
    LaunchedEffect(uiState.selectionData.hairColor) {
        Log.d("SelectScreen", "현재 선택된 머리색: ${uiState.selectionData.hairColor}")
    }
    LaunchedEffect(uiState.selectionData.hairStyle) {
        Log.d("SelectScreen", "현재 선택된 머리스탈: ${uiState.selectionData.hairStyle}")
    }

    // 뒤로가기 동작 제어
    BackHandler { handleBack() }

    // ui 화면
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
                    handleBack()
                },
                modifier = Modifier.align(Alignment.TopStart)
            )

            Column(
                Modifier.fillMaxSize().padding(horizontal = horizontalPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 단계 화면 설명 (+ 진행바)
                SelectInfo(
                    text = getInfoText(selectionStep, isInputMode),
                    currentStep = uiState.currentStep,
                    modifier = Modifier.fillMaxWidth()
                        .weight(0.25f)
                )

                // 단계별 Content 분기
                when (selectionStep) {
                    SelectionStep.THEME -> {
                        SelectionThemeContent(
                            themes = uiState.selectionData.themes,
                            customTheme = uiState.selectionData.customTheme,
                            isInputMode = isInputMode,
                            onInputModeChange = { isInputMode = it },
                            onCustomThemeInput = { vm.addCustomTheme(it) },
                            onThemeSelect = { vm.selectTheme(it) },
                            onNextClick = { vm.goToNextStep() },
                            modifier =  Modifier.fillMaxWidth()
                                .weight(0.75f)
                        )
                    }

                    SelectionStep.BACKGROUND -> {
                        SelectionBackgroundContent(
                            background = uiState.selectionData.background,
                            customBackground = uiState.selectionData.customBackground,
                            isInputMode = isInputMode,
                            onInputModeChange = { isInputMode = it },
                            onCustomBackgroundInput = { vm.addCustomBackground(it) },
                            onBackgroundSelect = { vm.selectBackground(it) },
                            onPrevClick = { vm.goToPrevStep() },
                            onNextClick = { vm.goToNextStep() },
                            modifier =  Modifier.fillMaxWidth()
                                .weight(0.75f)
                        )
                    }

                    SelectionStep.GENDER -> {
                        SelectionGenderContent(
                            gender = uiState.selectionData.gender,
                            onSelectGender = { vm.selectGender(it) },
                            onPrevClick = { vm.goToPrevStep() },
                            onNextClick = { vm.goToNextStep() },
                            modifier =  Modifier.fillMaxWidth()
                                .weight(0.75f)
                        )
                    }

                    SelectionStep.AGE -> {
                        SelectionAgeContent(
                            age = uiState.selectionData.age,
                            listState = vm.ageListState,
                            onSelectAge = { vm.selectAge(it) },
                            onPrevClick = { vm.goToPrevStep() },
                            onNextClick = { vm.goToNextStep() },
                            modifier =  Modifier.fillMaxWidth()
                                .weight(0.75f)
                        )
                    }

                    SelectionStep.NAME -> {
                        SelectionNameContent(
                            name = uiState.selectionData.name,
                            onNameChange = { vm.selectName(it) },
                            onPrevClick = { vm.goToPrevStep() },
                            onNextClick = { vm.goToNextStep() },
                            modifier =  Modifier.fillMaxWidth()
                                .weight(0.75f)
                        )
                    }

                    SelectionStep.FACE -> {
                        SelectionFaceContent(
                            eyeColor = uiState.selectionData.eyeColor,
                            hairColor = uiState.selectionData.hairColor,
                            hairStyle = uiState.selectionData.hairStyle,
                            onSelectEyeColor = { vm.selectEyeColor(it) },
                            onSelectHairColor = { vm.selectHairColor(it) },
                            onSelectHairStyle = { vm.selectHairStyle(it) },
                            onPrevClick = { vm.goToPrevStep() },
                            onNextClick = { onFinish(uiState.selectionData) },
                            modifier =  Modifier.fillMaxWidth()
                                .weight(0.75f)
                        )
                    }
                }
            }
        }
    }
}

