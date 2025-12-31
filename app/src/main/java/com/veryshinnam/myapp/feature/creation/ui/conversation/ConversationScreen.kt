package com.veryshinnam.myapp.feature.creation.ui.conversation

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.common.component.StepProgressBar
import com.veryshinnam.myapp.common.component.WarningConfirmSheet
import com.veryshinnam.myapp.common.model.ManualState
import com.veryshinnam.myapp.core.orientation.OrientationManager
import com.veryshinnam.myapp.feature.creation.content.conversation.ConversationEndContent
import com.veryshinnam.myapp.feature.creation.content.conversation.ConversationAnswerContent
import com.veryshinnam.myapp.feature.creation.content.conversation.ConversationFeedbackContent
import com.veryshinnam.myapp.feature.creation.content.conversation.ConversationQuestionContent
import com.veryshinnam.myapp.feature.creation.content.conversation.ConversationStoryContent
import com.veryshinnam.myapp.feature.creation.model.ConversationStep

// 캐릭터 생성 > 대화 진입점
@Composable
fun ConversationScreen(
    onBack: () -> Unit,
    goToNextManual: () -> Unit,
    horizontalPadding: Dp = 16.dp,
    vm: ConversationViewModel
) {
    val density = LocalDensity.current

    val context = LocalContext.current
    val recordAudioPermission = Manifest.permission.RECORD_AUDIO

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "마이크 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 상태 구독
    val uiState by vm.conversationUiState.collectAsStateWithLifecycle() // 대화 화면 상태 관리
    val isTtsSpeaking by vm.isTtsSpeaking.collectAsStateWithLifecycle() // tts 재생 상태 관리
    val manualState by vm.manualState.collectAsStateWithLifecycle()
    val manualStep by vm.manualStep.collectAsStateWithLifecycle()
    val manualMessage by vm.manualMessage.collectAsStateWithLifecycle()

    // ui 변수
    var isWarning by remember { mutableStateOf(false) }   // 경고창

    //  매뉴얼
    val isManual = manualState != ManualState.NONE
    val onStopManual: () -> Unit = { vm.clearManual(); onBack() }
    var logoHeight by remember { mutableStateOf(0.dp) }   // 로고바 높이

    // 세로 모드 고정
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
    }

    LaunchedEffect(manualStep) {
        if (manualStep == vm.manuals.size) {
            goToNextManual()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.background_yellow))
            .then(
                if (manualStep == 6) {
                        Modifier // answer 3초 대기
                    } else {
                    // 매뉴얼일 때만 터치 가로채기
                    when (manualState) {
                        ManualState.START -> Modifier.pointerInput(Unit) {
                            detectTapGestures { vm.nextManual() }
                        }

                        ManualState.STOP -> Modifier.pointerInput(Unit) {
                            detectTapGestures { onStopManual() }
                        }

                        else -> Modifier
                    }
                }
            )
    ) {
        Column {
            // 1. 상태바 만큼 여백 & 상단 로고
            Box(
                Modifier.fillMaxWidth()
                    .onGloballyPositioned {
                        // px > dp
                        logoHeight = with(density) { it.boundsInRoot().top.toDp() }
                    }
            ) {
                Column {
                    Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                    LogoBar(
                        onLogoClick = { isWarning = true }, // 경고창
                        modifier = Modifier
                    )
                }

                if (isManual && (manualStep == 0 || manualStep == 1 || manualStep == 5)) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .zIndex(1f)
                    )
                }
            }

            // 2. content 별 화면
            Column(
                modifier = Modifier.weight(1f) // 1, 3 제외 나머지 공간 차지
            ) {
                when (val state = uiState) {
                    // 조회 로딩 중
                    is ConversationUiState.Loading -> {
                        CircularProgressIndicator(
                            color = colorResource(id = R.color.main_orange), // 주황색
                            trackColor = Color.Gray.copy(alpha = 0.5f),
                            strokeWidth = 4.dp
                        )
                    }
                    // 조회 오류
                    is ConversationUiState.Error -> {
                        BackHandler { onBack() }
                        LoadErrorView(
                            message = state.message,
                            onRetry = { }
                        )
                    }
                    // 조회 성공
                    is ConversationUiState.Success -> {
                        LaunchedEffect(state.conversationStep) {
                            if (manualState == ManualState.NONE) {
                                vm.startTts()
                            }
                        }

                        Column {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // 진행바 (START, END는 공간만 차지)
                                StepProgressBar(
                                    radius = 0.068f,
                                    line = 24f,
                                    steps = 4,                        // 총 반복 횟수
                                    currentStep = state.loopStep,     // 현재 진행 단계
                                    modifier = Modifier
                                        .padding(top= logoHeight)
                                        .fillMaxWidth(0.75f)  // 진행 바 길이
                                        .fillMaxHeight(0.15f)
                                        .zIndex(2f)
                                        .align(Alignment.TopCenter)
                                        .alpha(
                                            if (
                                                state.conversationStep != ConversationStep.START &&
                                                state.conversationStep != ConversationStep.END
                                            ) 1f else 0f
                                        )
                                )

                                if (isManual && (manualStep == 0 || manualStep == 1 || manualStep == 5)) {
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .background(Color.Black.copy(alpha = 0.5f))
                                            .zIndex(10f)
                                    )
                                }
                            }

                            Box (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .then(
                                        // 매뉴얼일 때, 5 강조
                                        if (isManual && manualStep == 0 || manualStep == 1 || manualStep == 5)
                                            Modifier.background(Color.Black.copy(alpha = 0.5f))
                                        else Modifier
                                    )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = horizontalPadding)
                                ) {
                                    when (state.conversationStep) {
                                        ConversationStep.START -> { // 대화 시작 (다음 이야기)
                                            BackHandler(
                                                enabled = manualState == ManualState.NONE
                                            ) { isWarning = true } // 홈으로

                                            ConversationStoryContent(
                                                nextStory = state.nextStory,
                                                isTtsSpeaking = isTtsSpeaking,
                                                onReplayClick = {
                                                    if (isManual) vm.nextManual()
                                                    else vm.startTts()
                                                },
                                                onNextClick = {
                                                    if (isManual) vm.nextManual()
                                                    else vm.goToNextStep()
                                                },
                                                nextEnabled = !isTtsSpeaking,
                                                modifier = Modifier
                                            )
                                        }

                                        ConversationStep.STORY -> { // 다음 이야기
                                            BackHandler(
                                                enabled = manualState == ManualState.NONE
                                            ) { isWarning = true } // 홈으로
                                            ConversationStoryContent(
                                                nextStory = state.nextStory,
                                                isTtsSpeaking = isTtsSpeaking,
                                                onReplayClick = {
                                                    if (isManual) vm.nextManual()
                                                    else vm.startTts()
                                                },
                                                onNextClick = {
                                                    if (isManual) vm.nextManual()
                                                    else vm.goToNextStep()
                                                },
                                                nextEnabled = !isTtsSpeaking,
                                                modifier = Modifier
                                            )
                                        }

                                        // llm 질문 (STORY 단계에서 이동 가능)
                                        ConversationStep.QUESTION -> {
                                            BackHandler(
                                                enabled = manualState == ManualState.NONE
                                            ) { vm.goToPreviousStep() }
                                            ConversationQuestionContent(
                                                question = state.questionData.question,
                                                isTtsSpeaking = isTtsSpeaking,
                                                onReplayClick = {
                                                    if (isManual) vm.nextManual()
                                                    else vm.startTts()
                                                },
                                                onRecordClick = {
                                                    val granted =
                                                        ContextCompat.checkSelfPermission(
                                                            context,
                                                            recordAudioPermission
                                                        ) == PackageManager.PERMISSION_GRANTED

                                                    if (granted) {  // 이미 권한 있으면 녹음 시작
                                                        if (isManual) vm.nextManual()
                                                        else vm.goToNextStep()
                                                    } else { // 권한 없으면 런처 실행
                                                        launcher.launch(recordAudioPermission)
                                                    }
                                                },
                                                nextEnabled = !isTtsSpeaking,
                                                modifier = Modifier
                                            )
                                        }

                                        ConversationStep.ANSWER -> { // 사용자 대답 (QUESTION 단계 이동 가능)
                                            BackHandler(
                                                enabled = manualState == ManualState.NONE
                                            ) { vm.goToPreviousStep() }

                                            LaunchedEffect(state.conversationStep) {
                                                vm.startStt(context)
                                            }

                                            ConversationAnswerContent(
                                                answerData = state.answerData,
                                                onRecordStop = { vm.stopStt() },
                                                onFeedback = {
                                                    if (isManual) vm.nextManual()
                                                    else vm.goToNextStep()
                                                },
                                                modifier = Modifier
                                            )
                                        }

                                        ConversationStep.FEEDBACK -> { // llm 피드백 (QUESTION 단계 이동 가능)
                                            BackHandler(
                                                enabled = manualState == ManualState.NONE
                                            ) { // 긍정 > 홈으로, 부정 > QUESTION 단계로 back
                                                if (state.feedbackData.isPositive) {
                                                    isWarning = true
                                                } else vm.goToPreviousStep()
                                            }

                                            ConversationFeedbackContent(
                                                feedback = state.feedbackData,
                                                isTtsSpeaking = isTtsSpeaking,
                                                onReplayClick = {
                                                    if (isManual) vm.nextManual()
                                                    else vm.startTts()
                                                },
                                                onButtonClick = {
                                                    if (isManual) vm.nextManual()
                                                    else vm.goFromFeedback()
                                                }, // Answer 또는 Next Story(또는 종료)
                                                nextEnabled = !isTtsSpeaking,
                                                modifier = Modifier
                                            )
                                        }

                                        ConversationStep.END -> { // 대화 종료
                                            BackHandler { onBack() }
                                            ConversationEndContent(
                                                { onBack() }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 3. 네비게이션바 만큼 여백
            Box(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                if (isManual && (manualStep == 0 || manualStep == 1 || manualStep == 5)) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .zIndex(1f)
                    )
                }
            }
        }

        // 매뉴얼일 때
        if (isManual) {
            Text(
                text = "그만 들을래요.",
                color = colorResource(R.color.main_orange),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(30.dp)
                    .clickable {
                        when (manualState) {
                            ManualState.START -> vm.stopManual()
                            ManualState.STOP -> vm.clearManual()
                            else -> {}
                        }
                    }
                    .zIndex(11f)
            )
        }
    }

    if (isWarning) {
        WarningConfirmSheet(
            warningText  = "대화를 그만 진행할까요?\n지금까지 이야기한 내용은 저장되지 않아요!",
            confirmText = "그만하기",
            onDismiss = { isWarning = false },
            onConfirm = {
                isWarning = false
                onBack()
            }
        )
    }

//    if (manualState != ManualState.NONE) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .zIndex(2f)
//                .background(
//                    if (manualStep == 0 || manualStep == 1 || manualStep == 5)
//                        Color.Black.copy(alpha = 0.5f)
//                    else
//                        Color.Transparent
//                )
//                대.then(
////                    if (manualStep == 6) {
////                        Modifier // answer 기
//                    } else {
//                        when (manualState) {
//                            ManualState.START -> Modifier.pointerInput(Unit) {
//                                detectTapGestures { vm.nextManual() }
//                            }
//
//                            ManualState.STOP -> Modifier.pointerInput(Unit) {
//                                detectTapGestures { onStopManual() }
//                            }
//
//                            else -> Modifier
//                        }
//                    }
//                )
//        ) {
//
//            Text(
//                text = "그만 들을래요.",
//                color = colorResource(R.color.main_orange),
//                modifier = Modifier
//                    .align(Alignment.TopEnd)
//                    .padding(30.dp)
//                    .clickable {
//                        when (manualState) {
//                            ManualState.START -> {
//                                vm.stopManual()
//                            }
//
//                            ManualState.STOP -> {
//                                onStopManual()
//                            }
//
//                            else -> {}
//                        }
//                    }
//                    .zIndex(11f)
//            )
//
//            when (manualStep) {
//                0, 1 -> {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .systemBarsPadding()
//                            .padding(horizontal = horizontalPadding),
//                    ) {
//                        Spacer(Modifier.height(logoHeight))
//                        ConversationStoryContent(
//                            nextStory = manualMessage,
//                            isTtsSpeaking = false,
//                            onReplayClick = { vm.nextManual() },
//                            onNextClick = { vm.nextManual() },
//                            nextEnabled = true,
//                            modifier = Modifier
//                                .clickable(
//                                    indication = null,
//                                    interactionSource = remember { MutableInteractionSource() }
//                                ) { vm.nextManual() }
//                        )
//                    }
//                }
//
//                5 -> {
//                    textRect?.let { it ->
//                        TargetConvText(
//                            it,
//                            density, onClick = { vm.nextManual() }
//                            , text = manualMessage
//                        )
//                    }
//                    imageRect?.let { it ->
//                        TargetImage(it, painterResource(R.drawable.img_llm_question))
//                    }
//                    recordRect?.let { it ->
//                        TargetConvRecord(it, density, onClick = { vm.nextManual() })
//                    }
//                }
//            }
//        }
//    }
}