package com.veryshinnam.myapp.feature.creation.conversation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.core.speech.stt.SttManager
import com.veryshinnam.myapp.core.speech.tts.TtsManager
import com.veryshinnam.myapp.feature.creation.data.dto.FeedbackResponse
import com.veryshinnam.myapp.feature.creation.data.dto.NextStoryResponse
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationResult
import com.veryshinnam.myapp.feature.creation.model.CurrentStep
import com.veryshinnam.myapp.feature.creation.model.FeedbackData
import com.veryshinnam.myapp.feature.creation.model.QuestionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private fun changeLoopStep(loopStep: Int): String {
    return when (loopStep) {
        1 -> "STEP_01"
        2 -> "STEP_02"
        3 -> "STEP_03"
        else -> "END"
    }
}

@HiltViewModel
class ConversationViewModel @Inject constructor(
    // repository 주입은 일단 안씀
    private val tts: TtsManager,
    private val stt: SttManager
) : ViewModel() {

    // 대화 화면 상태 관리
    private val _conversationUiState = MutableStateFlow<ConversationUiState>(ConversationUiState.Loading)
    val conversationUiState: StateFlow<ConversationUiState> = _conversationUiState.asStateFlow()


    // ui에서 바로 쓸 수 있도록 준비
    val isTtsReady: StateFlow<Boolean> = tts.isReady
    val isSttReady: StateFlow<Boolean> = stt.isReady
    val isSttListening: StateFlow<Boolean> = stt.isListening

    init {
        // ViewModel 생성 시, 기본 Loading
        _conversationUiState.value = ConversationUiState.Loading
    }

    fun startConversation(req: StartConversationRequest) {
        _conversationUiState.value = ConversationUiState.Loading

        viewModelScope.launch {
            try {
                delay(300)
//                val res = repository.startConversation(req) // API 호출
                val res = StartConversationResult(
                    sessionId = 20L,
                    nextStory = "빛나는 우주 속에서 분홍색 곱슬머리를 가진 히히가 흰색 눈을 반짝이며 하루를 시작했어요.",
                    currentStep = "START"
                )

                _conversationUiState.value = ConversationUiState.Success(
                    sessionId = res.sessionId,
                    nextStory = res.nextStory,
                )
            } catch (e: Exception) {
                _conversationUiState.value = ConversationUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    // 다음 단계 진행
    fun goToNextStep() {

        // ConversationUiState.Success 상태 아니면 무시
        val state = _conversationUiState.value as? ConversationUiState.Success ?: return
        val loop = state.loopStep

        when (state.currentStep) {
            CurrentStep.START -> {
                // START → STORY (첫 이야기, api 요청)
                viewModelScope.launch {
                    fetchNextStep(state)
                }
            }

            CurrentStep.STORY -> {
                // STORY → QUESTION (질문 표시, 단계만 바뀜)
                _conversationUiState.value = state.copy(currentStep = CurrentStep.QUESTION)
            }

            CurrentStep.QUESTION -> {
                // STORY → QUESTION (녹음 화면, 단계만 바뀜)
                _conversationUiState.value = state.copy(currentStep = CurrentStep.ANSWER)
            }

            CurrentStep.ANSWER -> {
                // ANSWER → FEEDBACK (피드백 표시)
                viewModelScope.launch {
                    fetchFeedback(state)
                }
            }
            else -> {}
        }
    }

    // 피드백 분기 진행
    fun goFromFeedback() {
        val state = _conversationUiState.value as? ConversationUiState.Success ?: return
        val feedback = state.feedbackData ?: return

        if (feedback.result == "NEEDS_CORRECTION" && feedback.tryNum < 3) {
            // 부정 → 다시 녹음 단계로
            _conversationUiState.value = state.copy(currentStep = CurrentStep.ANSWER)
        } else {
            // 긍정 → 다음 루프 or END
            val nextLoop = state.loopStep + 1
            if (nextLoop <= 4) {
                viewModelScope.launch {
                    fetchNextStep(
                        state.copy(
                            loopStep = nextLoop,
                            questionData = null,
                            feedbackData = null
                        )
                    )
                }
            } else {
                // 대화 끝 > complete 호출
                viewModelScope.launch {
                    fetchEndStory(state.sessionId)
                    _conversationUiState.value = state.copy(
                        currentStep = CurrentStep.END
                    )
                }
            }
        }
    }

    // 다음 이야기 불러오기
    private suspend fun fetchNextStep(state: ConversationUiState.Success) {
        try {
            // 실제 API
            // val res = repository.nextStep(state.sessionId, loopStepToApiStep(state.loopStep))

            // 더미 응답
            val res = when (state.loopStep) {
                1 -> NextStoryResponse(
                    messageId = 33,
                    nextStory = "히히의 친구인 푸른색 다람쥐가 나타났어요. 그들은 모험을 떠나기로 했죠.",
                    llmQuestion = "히히와 다람쥐는 어디로 모험을 떠날까요?"
                )
                2 -> NextStoryResponse(
                    messageId = 34,
                    nextStory = "히히와 다람쥐는 우주를 가로지르는 별의 다리를 건너 마법이 가득한 정원으로 향했어요. 그곳에서 신비한 생물들을 만났죠.",
                    llmQuestion = "그 신비한 생물들은 어떤 도움을 줄까요?"
                )
                3 -> NextStoryResponse(
                    messageId = 35,
                    nextStory = "아 다음 내용 까먹었다, 근데 다음 질문은 기억나",
                    llmQuestion = "히히와 다람쥐는 어떤 게임을 하며 마법을 부렸을까?"
                )
                else -> NextStoryResponse(
                    messageId = 36,
                    nextStory = "히히와 다람쥐는 가위바위보에서 서로를 믿으며 힘껏 외쳤어요. 결국, 그들은 신비한 생물들의 사랑스러운 도토리들을 가득 담았고, 즐거운 모험을 계속할 수 있게 되었어요!",
                    llmQuestion = "히히와 다람쥐는 어떻게 팀워크를 발휘했을까요?"
                )
            }

            // 상태 업데이트
            _conversationUiState.value = state.copy(
                nextStory = res.nextStory,
                currentStep = CurrentStep.STORY,
                questionData = QuestionData(
                    messageId = res.messageId,
                    question = res.llmQuestion
                )
            )
        } catch (e: Exception) {
            _conversationUiState.value = ConversationUiState.Error(e.message ?: "Next step error")
        }
    }

    // 뒤로 가기
    fun goToPreviousStep() {
        val state = _conversationUiState.value as? ConversationUiState.Success ?: return

        val res = when (state.currentStep) {
            CurrentStep.QUESTION -> {
                // 질문 → 스토리
                _conversationUiState.value = state.copy(
                    currentStep = CurrentStep.STORY
                )
            }
            CurrentStep.ANSWER -> {
                // 답변 → 질문
                _conversationUiState.value = state.copy(
                    currentStep = CurrentStep.QUESTION
                )
            }
            CurrentStep.FEEDBACK -> {
                // 피드백 → 질문
                _conversationUiState.value = state.copy(
                    currentStep = CurrentStep.QUESTION
                )
            }
            else -> { /* START, STORY, END는 뒤로가기 없음 */ }
        }
    }

    // 피드백 불러오기
    private suspend fun fetchFeedback(state: ConversationUiState.Success) {
        try {
            // 실제 API 호출 시
            // val res = repository.feedback(state.sessionId, userAnswer)

            val currentTry = (state.feedbackData?.tryNum ?: 0) + 1

            // 더미 응답
            val res = when (state.loopStep) {
                1 -> when (currentTry) {
                    1 -> FeedbackResponse(
                        feedbackResult = "NEEDS_CORRECTION",
                        feedbackText = "숨바꼭질은 재미있지만, 신비한 생물들이 내린 시험은 좀 더 특별해야 해.",
                        currentStep = "STEP_01",
                        tryNum = 1
                    )
                    2 -> FeedbackResponse(
                        feedbackResult = "NEEDS_CORRECTION",
                        feedbackText = "한줄 테스트?",
                        currentStep = "STEP_01",
                        tryNum = 2
                    )
                    else -> FeedbackResponse(
                        feedbackResult = "GOOD",
                        feedbackText = "최종적으로 아주 멋진 대답이야!",
                        currentStep = "STEP_01",
                        tryNum = 3
                    )
                }

                2 -> FeedbackResponse(
                    feedbackResult = "GOOD",
                    feedbackText = "완벽해! 이번 질문은 통과야.",
                    currentStep = "STEP_02",
                    tryNum = 1
                )

                3 -> when (currentTry) {
                    1 -> FeedbackResponse(
                        feedbackResult = "NEEDS_CORRECTION",
                        feedbackText = "게임의 규칙을 조금 더 창의적으로 말해줄래?",
                        currentStep = "STEP_03",
                        tryNum = 1
                    )
                    2 -> FeedbackResponse(
                        feedbackResult = "NEEDS_CORRECTION",
                        feedbackText = "좋아지고 있어! 하지만 조금 더 재미있게 해볼까?",
                        currentStep = "STEP_03",
                        tryNum = 2
                    )
                    else -> FeedbackResponse(
                        feedbackResult = "GOOD",
                        feedbackText = "정말 멋진 발상이야! 이제 충분해.",
                        currentStep = "STEP_03",
                        tryNum = 3
                    )
                }

                else -> when (currentTry) {
                    1 -> FeedbackResponse(
                        feedbackResult = "NEEDS_CORRECTION",
                        feedbackText = "팀워크를 \"어떻게\" 발휘했을까요?",
                        currentStep = "STEP_03",
                        tryNum = 1
                    )

                    else -> FeedbackResponse(
                        feedbackResult = "GOOD",
                        feedbackText = "맞아 서로의 손을 잡으며, 눈을 마주치며 기도를 했을거야.",
                        currentStep = "STEP_03",
                        tryNum = 2
                    )
                }
            }

            // 상태 업데이트 (Response → Data 변환)
            _conversationUiState.value = state.copy(
                currentStep = CurrentStep.FEEDBACK,
                feedbackData = FeedbackData(
                    result = res.feedbackResult,
                    text = res.feedbackText,
                    tryNum = res.tryNum
                )
            )
        } catch (e: Exception) {
            _conversationUiState.value = ConversationUiState.Error(e.message ?: "Feedback error")
        }
    }

    // 스토리 끝내기
    private suspend fun fetchEndStory(sessionId: Long) {
        try {
            // 실제 API
            // repository.complete(sessionId)

            // 더미 호출
            Log.d("ConversationEnding", "Complete API called with sessionId=$sessionId")
        } catch (e: Exception) {
            _conversationUiState.value = ConversationUiState.Error(e.message ?: "Complete error")
        }
    }
}
