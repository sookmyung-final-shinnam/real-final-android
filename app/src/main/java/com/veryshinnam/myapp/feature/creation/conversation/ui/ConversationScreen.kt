package com.veryshinnam.myapp.feature.creation.conversation.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.feature.creation.conversation.component.ConversationProgressBar
import com.veryshinnam.myapp.feature.creation.model.CurrentStep
import com.veryshinnam.myapp.feature.home.ui.HomeMainScreen
import com.veryshinnam.myapp.feature.home.ui.HomeUiState

@Composable
fun ConversationScreen(
    onBack: () -> Unit,
    vm: ConversationViewModel
) {
    // 홈화면 상태 관리
    val uiState by vm.conversationUiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = { AppTopBar() }, // 상단 로고
        bottomBar = {
            Spacer( // 네비게이션 바만큼 여백
                modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars)
            )
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
                    LoadErrorView(
                        message = state.message,
                        onRetry = { }
                    )
                }
                // 조회 성공
                is ConversationUiState.Success -> {
                    Column(Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        // 진행바 (START, END 제외)
                        if (state.currentStep != CurrentStep.START && state.currentStep != CurrentStep.END) {
                            ConversationProgressBar(
                                progress = state.loopStep, // 현재 회차
                                total = 4,                  // 총 반복 횟수
                                modifier = Modifier.weight(0.2f)
                            )
                        } else Spacer(Modifier.weight(0.2f)) // 공간차지

                        when (state.currentStep) {
                            CurrentStep.START -> { // 대화 시작 (다음 이야기)
                                ConversationStoryContent(
                                    nextStory = state.nextStory,
                                    onNextClick = {  },
                                    modifier = Modifier.weight(0.8f)
                                )
                            }

                            CurrentStep.STORY -> { // 다음 이야기
                                ConversationStoryContent(
                                    nextStory = state.nextStory,
                                    onNextClick = {  },
                                    modifier = Modifier.weight(0.8f)
                                )
                            }

                            CurrentStep.QUESTION -> { // llm 질문
                                //                            QuestionContent(
                                //                                question = state.questionData?.question.orEmpty(),
                                //                                onAnswerClick = { vm.goToNextStep() }
                                //                            )
                            }

                            CurrentStep.ANSWER -> {
                                //                            AnswerInputContent(
                                //                                onAnswer = { answer -> vm.sendAnswer(answer) }
                                //                            )
                            }

                            CurrentStep.FEEDBACK -> {
                                //                            FeedbackContent(
                                //                                feedback = state.feedbackData!!,
                                //                                onNext = { vm.goToNextStep() }
                                //                            )
                            }

                            CurrentStep.END -> {
                                //                            EndingContent(onFinish = { /* 홈으로 이동 */ })
                            }
                        }
                    }
                }
            }
        }
    }
}