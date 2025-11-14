package com.veryshinnam.myapp.feature.creation.ui.selection

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.StepProgressBar
import com.veryshinnam.myapp.common.component.UserInfo
import com.veryshinnam.myapp.common.component.WarningConfirmSheet
import com.veryshinnam.myapp.common.component.WarningSheet
import com.veryshinnam.myapp.core.orientation.OrientationManager
import com.veryshinnam.myapp.feature.creation.model.SelectionData
import com.veryshinnam.myapp.feature.creation.model.SelectionStep
import com.veryshinnam.myapp.feature.creation.content.selection.SelectionAgeContent
import com.veryshinnam.myapp.feature.creation.content.selection.SelectionBackgroundContent
import com.veryshinnam.myapp.feature.creation.content.selection.SelectionFaceContent
import com.veryshinnam.myapp.feature.creation.content.selection.SelectionGenderContent
import com.veryshinnam.myapp.feature.creation.content.selection.SelectionNameContent
import com.veryshinnam.myapp.feature.creation.content.selection.SelectionThemeContent


// 캐릭터 생성 > 선택 진입점
@Composable
fun SelectionScreen(
    onHome: () -> Unit,
    onFinish: (SelectionData) -> Unit,
    vm: SelectViewModel = hiltViewModel(),
    horizontalPadding: Dp = 16.dp,
    topPadding: Dp = 24.dp
) {
    val uiState by vm.selectUiState.collectAsState()             // 선택 화면 상태 관리
    val selectionStep = uiState.selectionStep                    // 현재 선택 단계
    var isInputMode by remember { mutableStateOf(false) } // 테마+배경 직접추가 입력 모드

    var isWarning by remember { mutableStateOf(false) }   // 경고창
    var warningText by remember { mutableStateOf("") }
    var confirmText by remember { mutableStateOf("") }
    var confirmAction by remember { mutableStateOf<() -> Unit>({}) }

    var isSimpleWarning by remember { mutableStateOf(false) } // 단순 경고창
    var SimpleWarningText by remember { mutableStateOf("") }

    // 뒤로 가기 로직
    fun handleBack() {
        if (isInputMode) {
            isInputMode = false // 입력모드 해제
        } else {
            if (uiState.selectionStep == SelectionStep.THEME) {
                warningText = "홈으로 돌아갈까요?\n지금까지 선택한 내역은 저장되지 않아요!"
                confirmText = "홈으로"
                confirmAction = { onHome() }
                isWarning = true
            } else {
                vm.goToPrevStep()
            }
        }
    }

    // 설명 텍스트 로직
    fun getInfoText(step: SelectionStep, isInputMode: Boolean): String {
        return if (isInputMode) {
            when (step) {
                SelectionStep.THEME -> "원하는 주제를 입력해 주세요!"
                SelectionStep.BACKGROUND -> "원하는 배경을 입력해 주세요!"
                else -> ""
            }
        } else {
            when (step) {
                SelectionStep.THEME -> "만들고 싶은 동화의 주제를 3개까지 골라 주세요!"
                SelectionStep.BACKGROUND -> "만들고 싶은 동화의 배경을 골라 주세요!"
                SelectionStep.GENDER -> "동화 속 주인공의 성별은 어떻게 할까요?"
                SelectionStep.AGE -> "동화 속 주인공의 나이는 어떻게 할까요?"
                SelectionStep.NAME -> "동화 속 주인공의 이름은 어떻게 할까요?"
                SelectionStep.FACE -> "동화 속 주인공의 모습은 어떻게 할까요?"
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
        Log.d("SelectScreen", "현재 선택된 머리 스타일: ${uiState.selectionData.hairStyle}")
    }

    // 세로 모드
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
    }

    // 뒤로 가기
    BackHandler { handleBack() }

    // ui 화면
    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = {
            // 상태바 만큼 여백 & 상단 로고
            Column {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                LogoBar(onLogoClick = {
                    handleBack()
                })
            }
        },
        bottomBar = {
            // 네비게이션바 만큼 여백
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopStart
        ) {
            // 뒤로 가기 버튼
            BackButton(
                onBackClick = {
                    handleBack()
                },
                modifier = Modifier.align(Alignment.TopStart)
            )

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = horizontalPadding),
            ) {
                // 단계 화면 설명 (+ 진행바)
//                SelectionInfo(
//                    text = getInfoText(selectionStep, isInputMode),
//                    currentStep = uiState.currentStep,
//                    modifier = Modifier.fillMaxWidth()
//                        .weight(0.25f)
//                )

                Box{
                    // 진행바
                    StepProgressBar(
                        steps = 6,
                        currentStep = uiState.currentStep,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(0.08f)
                            .padding(top = topPadding / 2)
                            .zIndex(1f)
                    )

                    UserInfo(
                        modifier = Modifier.align(Alignment.TopStart),
                        animalImage = painterResource(R.drawable.img_squirrel_cut),
                        animalDescription = "캐릭터 생성 설명 다람쥐 이미지",
                        cardColor = colorResource(R.color.main_orange),
                        cardText =  getInfoText(selectionStep, isInputMode),
                    )
                }

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
                            onSimpleWarning = { text ->  // 경고 문구
                                SimpleWarningText = text
                                isSimpleWarning = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = topPadding)
                                .weight(1f)
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
                            onSimpleWarning = { text ->  // 경고 문구
                                SimpleWarningText = text
                                isSimpleWarning = true
                            },
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = topPadding)
                                .weight(1f)
                        )
                    }

                    SelectionStep.GENDER -> {
                        SelectionGenderContent(
                            gender = uiState.selectionData.gender,
                            onSelectGender = { vm.selectGender(it) },
                            onPrevClick = { vm.goToPrevStep() },
                            onNextClick = { vm.goToNextStep() },
                            onSimpleWarning = { text ->  // 경고 문구
                                SimpleWarningText = text
                                isSimpleWarning = true
                            },
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = topPadding)
                                .weight(1f)
                        )
                    }

                    SelectionStep.AGE -> {
                        SelectionAgeContent(
                            age = uiState.selectionData.age,
                            listState = vm.ageListState,
                            onSelectAge = { vm.selectAge(it) },
                            onPrevClick = { vm.goToPrevStep() },
                            onNextClick = { vm.goToNextStep() },
                            onSimpleWarning = { text ->  // 경고 문구
                                SimpleWarningText = text
                                isSimpleWarning = true
                            },
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = topPadding)
                                .weight(1f)
                        )
                    }

                    SelectionStep.NAME -> {
                        SelectionNameContent(
                            name = uiState.selectionData.name,
                            onNameChange = { vm.selectName(it) },
                            onPrevClick = { vm.goToPrevStep() },
                            onNextClick = { vm.goToNextStep() },
                            onSimpleWarning = { text ->  // 경고 문구
                                SimpleWarningText = text
                                isSimpleWarning = true
                            },
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = topPadding)
                                .weight(1f)
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
                            onSimpleWarning = { text ->  // 경고 문구
                                SimpleWarningText = text
                                isSimpleWarning = true
                            },
                            onWarning = {  wText, cText -> // 테마에서 뒤로가기, 이야기 시작 직전
                                warningText = wText
                                confirmText = cText
                                confirmAction = { onFinish(uiState.selectionData) }
                                isWarning = true
                            },
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = topPadding)
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }

    if (isWarning) {
        WarningConfirmSheet(
            warningText  = warningText,
            confirmText = confirmText,
            onDismiss = { isWarning = false },
            onConfirm = {
                isWarning = false
                confirmAction()
            }
        )
    }

    if (isSimpleWarning) {
        WarningSheet(
            warningText = SimpleWarningText,
            onDismiss = { isSimpleWarning = false}
        )
    }
}

