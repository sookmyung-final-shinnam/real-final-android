package com.veryshinnam.myapp.feature.creation.conversation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.AppTopBar
import com.veryshinnam.myapp.component.common.LoadErrorView
import com.veryshinnam.myapp.component.common.StepProgressBar
import com.veryshinnam.myapp.feature.creation.conversation.component.ConversationEndContent
import com.veryshinnam.myapp.feature.creation.model.ConversationStep

// 캐릭터 생성 > 대화 진입점
@Composable
fun ConversationScreen(
    onBack: () -> Unit,
    vm: ConversationViewModel
) {
    // 대화 화면 상태 관리
    val uiState by vm.conversationUiState.collectAsStateWithLifecycle()
    val isTtsSpeaking by vm.isTtsSpeaking.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = { AppTopBar() }, // 상단 로고
        contentWindowInsets = WindowInsets.navigationBars // 네비게이션 여백
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
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
                    LoadErrorView(
                        message = state.message,
                        onRetry = { }
                    )
                }
                // 조회 성공
                is ConversationUiState.Success -> {
                    LaunchedEffect(state.conversationStep) {
                        vm.replayText() // 자동 읽기
                    }

                    Column(Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // 진행바 (START, END 제외)
                        if (state.conversationStep != ConversationStep.START && state.conversationStep != ConversationStep.END) {
                            StepProgressBar(
                                steps = 4,                        // 총 반복 횟수
                                currentStep = state.loopStep,     // 현재 진행 단계
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)  // 진행 바 길이
                                    .weight(0.2f),
                            )
                        } else Spacer(Modifier.weight(0.2f)) // 공간 차지

                        when (state.conversationStep) {
                            ConversationStep.START -> { // 대화 시작 (다음 이야기)
                                BackHandler { onBack() } // 홈으로
                                ConversationStoryContent(
                                    nextStory = state.nextStory,
                                    isTtsSpeaking = isTtsSpeaking,
                                    onReplayClick = { vm.replayText() },
                                    onNextClick = {  vm.goToNextStep() },
                                    modifier = Modifier.weight(0.8f)
                                )
                            }

                            ConversationStep.STORY -> { // 다음 이야기
                                BackHandler { onBack() } // 홈으로
                                ConversationStoryContent(
                                    nextStory = state.nextStory,
                                    isTtsSpeaking = isTtsSpeaking,
                                    onReplayClick = { vm.replayText() },
                                    onNextClick = { vm.goToNextStep() },
                                    modifier = Modifier.weight(0.8f)
                                )
                            }

                            ConversationStep.QUESTION -> { // llm 질문 (STORY 단계 이동 가능)
                                BackHandler { vm.goToPreviousStep() }
                                ConversationQuestionContent(
                                    question = state.questionData!!.question,
                                    isTtsSpeaking = isTtsSpeaking,
                                    onReplayClick = { vm.replayText() },
                                    onRecordClick = { vm.goToNextStep() },
                                    modifier =  Modifier.weight(0.8f)
                                )
                            }

                            ConversationStep.ANSWER -> { // 사용자 대답 (QUESTION 단계 이동 가능)
                                BackHandler { vm.goToPreviousStep() }
                                ConversationAnswerContent(
                                    onRecordDone = { vm.goToNextStep() },
                                    modifier =  Modifier.weight(0.8f)
                                )
                            }

                            ConversationStep.FEEDBACK -> { // llm 피드백 (QUESTION 단계 이동 가능)
                                val feedback = state.feedbackData!!
                                val isGoodFeedback = feedback.result == "GOOD"

                                BackHandler { // 긍정 >  홈으로, 부정 > QUESTION 단계 이동 가능
                                    if (isGoodFeedback) onBack()
                                    else vm.goToPreviousStep()
                                }

                                ConversationFeedbackContent(
                                    feedback = feedback,
                                    isGoodFeedback = isGoodFeedback,
                                    isTtsSpeaking = isTtsSpeaking,
                                    onReplayClick = { vm.replayText() },
                                    onButtonClick = { vm.goFromFeedback() }, // 재녹음 → Answer 또는 성공 Story로
                                    modifier = Modifier.weight(0.8f)
                                )
                            }

                            ConversationStep.END -> { // 대화 종료
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