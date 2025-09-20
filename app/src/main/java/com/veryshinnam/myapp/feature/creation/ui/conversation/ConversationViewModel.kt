package com.veryshinnam.myapp.feature.creation.ui.conversation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.core.speech.stt.SttManager
import com.veryshinnam.myapp.core.speech.tts.TtsManager
import com.veryshinnam.myapp.feature.creation.data.dto.FeedbackResult
import com.veryshinnam.myapp.feature.creation.data.dto.NextStepResult
import com.veryshinnam.myapp.feature.creation.data.dto.StartRequest
import com.veryshinnam.myapp.feature.creation.data.dto.StartResult
import com.veryshinnam.myapp.feature.creation.data.repository.ConversationRepository
import com.veryshinnam.myapp.feature.creation.model.AnswerData
import com.veryshinnam.myapp.feature.creation.model.ConversationStep
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
    private val repository: ConversationRepository,
    private val tts: TtsManager,
    private val stt: SttManager
) : ViewModel() {

    // 대화 화면 상태 관리
    private val _conversationUiState = MutableStateFlow<ConversationUiState>(ConversationUiState.Loading)
    val conversationUiState: StateFlow<ConversationUiState> = _conversationUiState.asStateFlow()

    // isSpeaking 추가
    val isTtsSpeaking = tts.isSpeaking

    init {
        // ViewModel 생성 > 기본 Loading + stt event collector 한 번만 실행
        _conversationUiState.value = ConversationUiState.Loading
        viewModelScope.launch {
            stt.events.collect { event -> handleSttEvent(event) }
        }
    }

    fun startConversation(req: StartRequest) {
        Log.d("startConversation: ", "$req")
        _conversationUiState.value = ConversationUiState.Loading

        viewModelScope.launch {
            try {
                val res = repository.startConversation(req) // api 호출
                _conversationUiState.value = ConversationUiState.Success(
                    sessionId = res.sessionId,
                    nextStory = res.nextStory,
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

        when (state.conversationStep) {
            ConversationStep.START -> {
                // START > STORY (첫 이야기, api 요청)
                viewModelScope.launch {  fetchNextStep(state) }
            }

            ConversationStep.STORY -> {
                // STORY > QUESTION (질문 표시, 단계만 바뀜)
                _conversationUiState.value = state.copy(conversationStep = ConversationStep.QUESTION)
            }

            ConversationStep.QUESTION -> {
                // STORY > QUESTION (녹음 화면, 단계만 바뀜)
                _conversationUiState.value = state.copy(conversationStep = ConversationStep.ANSWER)
            }

            ConversationStep.ANSWER -> {
                // ANSWER > FEEDBACK (피드백 표시)

                viewModelScope.launch {     fetchFeedback(state) }

            }
            else -> {}
        }
    }

    // 피드백 분기 진행
    fun goFromFeedback(context: Context? = null) {
        val state = _conversationUiState.value as? ConversationUiState.Success ?: return
        val feedback = state.feedbackData

        if (!feedback.isPositive && feedback.tryNum < 3) {
            // 부정 → 다시 녹음 단계로
            _conversationUiState.value = state.copy(
                conversationStep = ConversationStep.ANSWER,
                answerData = AnswerData("", "") // 답변 초기화
            )
        } else {
            // 긍정 → 다음 루프 or END
            val nextLoop = state.loopStep + 1
            if (nextLoop <= 4) {
                viewModelScope.launch {
                    fetchNextStep(
                        state.copy(
                            loopStep = nextLoop,
                            answerData = AnswerData("", "") // 답변 초기화
                        )
                    )
                }
            } else {
                // 대화 끝 > complete 호출
                viewModelScope.launch { fetchEndStory(state.sessionId) }
                _conversationUiState.value = state.copy(conversationStep = ConversationStep.END)
            }
        }
    }

    // 다음 이야기 불러오기
    private suspend fun fetchNextStep(state: ConversationUiState.Success) {
        try {
            val res = repository.getNextStep(state.sessionId, changeLoopStep(state.loopStep))

            _conversationUiState.value = state.copy(
                nextStory = res.nextStory,
                conversationStep = ConversationStep.STORY,
                questionData = QuestionData(
                    messageId = res.messageId,
                    question = res.llmQuestion
                )
            )
        } catch (e: Exception) {
            _conversationUiState.value =
                ConversationUiState.Error(e.message ?: "Next step error")
        }
    }

    // 뒤로 가기
    fun goToPreviousStep() {
        val state = _conversationUiState.value as? ConversationUiState.Success ?: return

        val res = when (state.conversationStep) {
            ConversationStep.QUESTION -> {
                // 질문 → 스토리
                _conversationUiState.value = state.copy(
                    conversationStep = ConversationStep.STORY
                )
            }
            ConversationStep.ANSWER -> {
                // 답변 → 질문
                _conversationUiState.value = state.copy(
                    conversationStep = ConversationStep.QUESTION
                )
            }
            ConversationStep.FEEDBACK -> {
                // 피드백 → 질문
                _conversationUiState.value = state.copy(
                    conversationStep = ConversationStep.QUESTION
                )
            }
            else -> { /* START, STORY, END는 뒤로가기 없음 */ }
        }
    }

    // 피드백 불러오기
    private suspend fun fetchFeedback(state: ConversationUiState.Success) {
        try {
            val res = repository.feedbackConversation( // api 호출
                messageId = state.questionData.messageId,
                userAnswer = state.answerData.userAnswer
            )

            _conversationUiState.value = state.copy(
                conversationStep = ConversationStep.FEEDBACK,
                feedbackData = res
            )
        } catch (e: Exception) {
            _conversationUiState.value =
                ConversationUiState.Error(e.message ?: "Feedback error")
        }
    }

    // 스토리 끝내기
    private suspend fun fetchEndStory(sessionId: Long) {
        try {
            repository.completeConversation(sessionId) // api 호출
            Log.d("ConversationEnding", "Complete API called with sessionId=$sessionId")
        } catch (e: Exception) {
            _conversationUiState.value =
                ConversationUiState.Error(e.message ?: "Complete error")
        }
    }

    // tts 시작
    fun startTts() {
        val state = _conversationUiState.value as? ConversationUiState.Success ?: return

        when (state.conversationStep) {
            ConversationStep.START, ConversationStep.STORY -> tts.speak(state.nextStory, flush = true)
            ConversationStep.QUESTION -> tts.speak(state.questionData.question, flush = true)
            ConversationStep.FEEDBACK -> tts.speak(state.feedbackData.text, flush = true)
            else -> return
        }
    }

    fun startStt(context: Context) {
        stt.start(context)
    }

    // stt event 처리
    private fun handleSttEvent(event: SttManager.SttEvent) {
        val state = _conversationUiState.value as? ConversationUiState.Success ?: return
        when (event) {
            is SttManager.SttEvent.Partial -> {
                Log.d("STT test", "Partial: ${event.text}")

                // 부분 답변 업데이트
                _conversationUiState.value = state.copy(
                    answerData = state.answerData.copy(
                        partialAnswer = event.text
                    )
                )
            }
            is SttManager.SttEvent.Final -> {
                Log.d("STT test", "Final: ${event.text}")

                val updated = state.copy(
                    answerData = state.answerData.copy(
                        userAnswer = event.text
                    )
                )
                _conversationUiState.value = updated
            }
            is SttManager.SttEvent.Error -> {
                Log.e("STT test", "Error: code=${event.code}, message=${event.message}")
                _conversationUiState.value = state.copy(
                    answerData = state.answerData.copy(
                        partialAnswer = "",
                        userAnswer = ""
                    )
                )
            }
            else -> {}
        }
    }

    fun stopStt() {
        if (stt.isListening.value) {
            stt.stop()
        }
    }
}
