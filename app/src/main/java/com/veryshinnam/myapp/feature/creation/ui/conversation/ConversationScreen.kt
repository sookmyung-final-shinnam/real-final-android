package com.veryshinnam.myapp.feature.creation.ui.conversation

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    horizontalPadding: Dp = 16.dp,
    vm: ConversationViewModel
) {

    val context = LocalContext.current
    val recordAudioPermission = Manifest.permission.RECORD_AUDIO

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "마이크 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    val uiState by vm.conversationUiState.collectAsStateWithLifecycle() // 대화 화면 상태 관리
    val isTtsSpeaking by vm.isTtsSpeaking.collectAsStateWithLifecycle() // tts 재생 상태 관리

    var isWarning by remember { mutableStateOf(false) }   // 경고창

    // 세로 모드 고정
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
    }

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = {
            // 상태바 만큼 여백 & 상단 로고
            Column {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                LogoBar(onLogoClick = {
                    isWarning = true      // 경고창
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
            contentAlignment = Alignment.Center
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
                        vm.startTts() // 자동 읽기
                    }

                    // 진행바 (START, END 제외)
                    if (state.conversationStep != ConversationStep.START && state.conversationStep != ConversationStep.END) {
                        StepProgressBar(
                            steps = 4,                        // 총 반복 횟수
                            currentStep = state.loopStep,     // 현재 진행 단계
                            modifier = Modifier
                                .fillMaxWidth(0.7f)  // 진행 바 길이
                                .fillMaxHeight(0.15f)
                                .zIndex(2f)
                                .align(Alignment.TopCenter),
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = horizontalPadding)
                    )  {
                        when (state.conversationStep) {
                            ConversationStep.START -> { // 대화 시작 (다음 이야기)
                                BackHandler {  isWarning = true } // 홈으로
                                ConversationStoryContent(
                                    nextStory = state.nextStory,
                                    isTtsSpeaking = isTtsSpeaking,
                                    onReplayClick = { vm.startTts() },
                                    onNextClick = { vm.goToNextStep() },
                                    nextEnabled = !isTtsSpeaking,
                                    modifier = Modifier
                                )
                            }

                            ConversationStep.STORY -> { // 다음 이야기
                                BackHandler { isWarning = true } // 홈으로
                                ConversationStoryContent(
                                    nextStory = state.nextStory,
                                    isTtsSpeaking = isTtsSpeaking,
                                    onReplayClick = { vm.startTts() },
                                    onNextClick = { vm.goToNextStep() },
                                    nextEnabled = !isTtsSpeaking,
                                    modifier = Modifier
                                )
                            }

                            ConversationStep.QUESTION -> { // llm 질문 (STORY 단계 이동 가능)
                                BackHandler { vm.goToPreviousStep() }
                                ConversationQuestionContent(
                                    question = state.questionData.question,
                                    isTtsSpeaking = isTtsSpeaking,
                                    onReplayClick = { vm.startTts() },
                                    onRecordClick = {
                                        val granted =
                                            ContextCompat.checkSelfPermission(context, recordAudioPermission) == PackageManager.PERMISSION_GRANTED

                                        if (granted) {  // 이미 권한 있으면 녹음 시작
                                            vm.goToNextStep()
                                        } else { // 권한 없으면 런처 실행
                                            launcher.launch(recordAudioPermission)
                                        }
                                    },
                                    nextEnabled = !isTtsSpeaking,
                                    modifier = Modifier
                                )
                            }

                            ConversationStep.ANSWER -> { // 사용자 대답 (QUESTION 단계 이동 가능)
                                BackHandler { vm.goToPreviousStep() }

                                LaunchedEffect(state.conversationStep) {
                                    vm.startStt(context)
                                }

                                ConversationAnswerContent(
                                    answerData = state.answerData,
                                    onRecordStop = { vm.stopStt() },
                                    onFeedback = { vm.goToNextStep() },
                                    modifier = Modifier
                                )
                            }

                            ConversationStep.FEEDBACK -> { // llm 피드백 (QUESTION 단계 이동 가능)

                                BackHandler { // 긍정 >  홈으로, 부정 > QUESTION 단계 이동 가능
                                    if (state.feedbackData.isPositive) {
                                        isWarning = true
//                                        onBack()
                                    }
                                    else vm.goToPreviousStep()
                                }

                                ConversationFeedbackContent(
                                    feedback = state.feedbackData,
                                    isTtsSpeaking = isTtsSpeaking,
                                    onReplayClick = { vm.startTts() },
                                    onButtonClick = { vm.goFromFeedback() }, // 재녹음 → Answer 또는 성공 Story로
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
}