package com.veryshinnam.myapp.feature.creation.ui.conversation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.common.model.ManualState
import com.veryshinnam.myapp.core.manual.ManualManager
import com.veryshinnam.myapp.core.speech.stt.SttManager
import com.veryshinnam.myapp.core.speech.tts.TtsManager
import com.veryshinnam.myapp.feature.creation.data.dto.NextStepResult
import com.veryshinnam.myapp.feature.creation.data.dto.StartRequest
import com.veryshinnam.myapp.feature.creation.data.dto.StartResult
import com.veryshinnam.myapp.feature.creation.data.repository.ConversationRepository
import com.veryshinnam.myapp.feature.creation.model.AnswerData
import com.veryshinnam.myapp.feature.creation.model.ConversationStep
import com.veryshinnam.myapp.feature.creation.model.FeedbackData
import com.veryshinnam.myapp.feature.creation.model.ManualScriptData
import com.veryshinnam.myapp.feature.creation.model.QuestionData
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val ttsManger: TtsManager,
    private val sttManager: SttManager,
    private val manualManager: ManualManager
) : ViewModel() {

    // 화면 전체 ui 상태
    private val _conversationUiState = MutableStateFlow<ConversationUiState>(ConversationUiState.Loading)
    val conversationUiState: StateFlow<ConversationUiState> = _conversationUiState.asStateFlow()

    // ttsManger 구독
    val isTtsSpeaking = ttsManger.isSpeaking

    // ManualManager 구독
    val manualState = manualManager.state
    val manualMessage = manualManager.message

    // 매뉴얼 진행 단계 상태
    private val _manualStep = MutableStateFlow(0)
    val manualStep = _manualStep.asStateFlow()

    init {
        // ViewModel 생성 > 기본 Loading
        _conversationUiState.value = ConversationUiState.Loading

        // STT 이벤트 처리
        viewModelScope.launch {
            sttManager.events.collect { event -> handleSttEvent(event) }
        }

        // 매뉴얼에선 tts X
        viewModelScope.launch {
            manualManager.state.collect { state ->
                if (state != ManualState.NONE) {
                    ttsManger.stop()
                }
            }
        }
    }

    fun startConversation(req: StartRequest) {
        Log.d("startConversation: ", "$req")
        _conversationUiState.value = ConversationUiState.Loading

        viewModelScope.launch {
            try {
                val res = repository.startConversation(req) // api 호출
//                val res = dummyStart()

                // 초기화
                _conversationUiState.value = ConversationUiState.Success(
                    sessionId = res.sessionId,
                    nextStory = res.nextStory,
                    questionData = QuestionData(messageId = -1, question = ""),
                    answerData = AnswerData(userAnswer = "", partialAnswer = ""),
                    feedbackData = FeedbackData(isPositive = false, text = ""),
                    conversationStep = ConversationStep.START, // 대화 시작 단계
                    loopStep = 1
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
                ttsManger.stop() // tts 중지
                _conversationUiState.value = state.copy(conversationStep = ConversationStep.ANSWER)
            }

            ConversationStep.ANSWER -> {
                // ANSWER > FEEDBACK (피드백 표시)

                viewModelScope.launch { fetchFeedback(state) }

            }
            else -> {}
        }
    }

    // 피드백 분기 진행
    fun goFromFeedback() {
        val state = _conversationUiState.value as? ConversationUiState.Success ?: return
        val feedback = state.feedbackData

        if (!feedback.isPositive && feedback.tryNum < 3) {
            // 부정 → 다시 녹음 단계로
            ttsManger.stop() // tts 중지
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
//            val res = dummyNextStep(state.loopStep)

            Log.d("ConversationAPI", "nextStory: ${res.nextStory},messageId:  ${res.messageId}, question: ${res.llmQuestion}")

            _conversationUiState.value = state.copy(
                nextStory = res.nextStory,
                questionData = QuestionData(res.messageId, res.llmQuestion),
                feedbackData = state.feedbackData.copy(tryNum = 0),
                conversationStep = ConversationStep.STORY
            )
        } catch (e: Exception) {
            Log.e("ConversationaAPIERROR", "Next API ${e.message}")
            _conversationUiState.value =
                ConversationUiState.Error(e.message ?: "Next step error")
        }
    }

    // 뒤로 가기
    fun goToPreviousStep() {
        val state = _conversationUiState.value as? ConversationUiState.Success ?: return

        _conversationUiState.value = when (state.conversationStep) {
            ConversationStep.QUESTION -> state.copy(conversationStep = ConversationStep.STORY)
            ConversationStep.ANSWER   -> state.copy(conversationStep = ConversationStep.QUESTION)
            ConversationStep.FEEDBACK -> state.copy(conversationStep = ConversationStep.QUESTION)
            else -> state
        }
    }

    // 피드백 불러오기
    private suspend fun fetchFeedback(state: ConversationUiState.Success) {
        try {
            val res = repository.feedbackConversation( // api 호출
                messageId = state.questionData.messageId,
                userAnswer = state.answerData.userAnswer
            )

//            val res = dummyFeedback(
//                step = state.loopStep,
//                tryNum = state.feedbackData.tryNum + 1
//            )

            _conversationUiState.value = state.copy(
                conversationStep = ConversationStep.FEEDBACK,
                feedbackData = res
            )
        } catch (e: Exception) {
            Log.e("ConversationaAPIERROR", "FEEDBABK API ${e.message}")
            _conversationUiState.value =
                ConversationUiState.Error(e.message ?: "Feedback error")
        }
    }

    // 스토리 끝내기
    private suspend fun fetchEndStory(sessionId: Long) {
        try {
            repository.completeConversation(sessionId) // api 호출
//            Log.d("ConversationEnding", "Complete API called with sessionId=$sessionId")
        } catch (e: Exception) {
            Log.e("ConversationaAPIERROR", "Complete API ${e.message}")

            _conversationUiState.value =
                ConversationUiState.Error(e.message ?: "Complete error")
        }
    }

    // tts 시작
    fun startTts() {
        val state = _conversationUiState.value as? ConversationUiState.Success ?: return

        when (state.conversationStep) {
            ConversationStep.START, ConversationStep.STORY -> ttsManger.speak(state.nextStory, flush = true)
            ConversationStep.QUESTION -> ttsManger.speak(state.questionData.question, flush = true)
            ConversationStep.FEEDBACK -> ttsManger.speak(state.feedbackData.text, flush = true)
            else -> return
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManger.stop()   // 화면 dispose 시 tts, stt 중지
        sttManager.stop()
    }

    fun startStt(context: Context) {
        sttManager.start(context)
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
        if (sttManager.isListening.value) {
            sttManager.stop()
        }
    }


    // 더미 테스트
    fun dummyStart():StartResult {
        return StartResult(
            sessionId = 1L,
            nextStory = "더미",
//            nextStory = "옛날옛날, 아주 넓은 사막 한 가운데에" +
//                    "‘숙명’이라는 12살 소녀가 살고 있었어요." +
//                    "숙명이는 모래바람이 불어도 웃음을 잃지 않는 밝은 아이였지요." +
//                    "어느 날, 숙명이는 모래 언덕 너머에서 반짝이는 빛을 발견했어요.",
            currentStep = "STEP_01"
        )
    }

    fun dummyNextStep(step: Int): NextStepResult {
        return when (step) {
            1 -> NextStepResult(
                messageId = 101L,
                nextStory = "옛날옛날, " ,
//                        + "아주 넓은 사막 한 가운데에" +
//                        "‘숙명’이라는 12살 소녀가 살고 있었어요." +
//                        "숙명이는 모래바람이 불어도 웃음을 잃지 않는 밝은 아이였이 불어도 웃음을 잃지 않는 밝은 아이였이 불어도 웃음을 잃지 않는 밝은 아이였이 불어도 웃음을 잃지 않는 밝은 아이였이 불어도 웃음을 잃지 않는 밝은 아이였이 불어도 웃음을 잃지 않는 밝은 아이였이 불어도 웃음을 잃지 않는 밝은 아이였이 불어도 웃음을 잃지 않는 밝은 아이였지요." +
//                        "어느 날, 숙명이는 모래 언덕 너머에서 반짝이는 빛을 발견했어요.",
                llmQuestion = "숙"
//                        llmQuestion = "숙명이는 빛을 보고 어떻게 했을까?숙명숙명이는 빛을 보고 어떻게 했을까?숙명숙명이는 빛을 보고 어떻게 했을까?숙명"
            )
            2 -> NextStepResult(
                messageId = 102L,
                nextStory = "긍",
                llmQuestion = "명"
            )
            3 -> NextStepResult(
                messageId = 103L,
                nextStory = "용.",
                llmQuestion = "문"
            )
            else -> NextStepResult(
                messageId = 104L,
                nextStory = "이",
                llmQuestion = "?"
            )
        }
    }

    fun dummyFeedback(step: Int, tryNum: Int): FeedbackData {
        return when (step) {
            1 -> when (tryNum) {
                1 -> FeedbackData(
                    isPositive = false,
                    text = "원투쓰리포원투쓰리포원투쓰리포원투쓰리포원투쓰리포" ,
                    tryNum = tryNum
                )
                2 -> FeedbackData(
                    isPositive = true,
                    text = "원투쓰리포원투쓰리포원투쓰리포원투쓰리포원투쓰리포",
                    tryNum = tryNum
                )
                else -> FeedbackData(
                    isPositive = true,
                    text = "아!",
                    tryNum = tryNum
                )
            }

            2 -> FeedbackData(
                isPositive = true,
                text = "어.",
                tryNum = tryNum
            )

            3 -> when (tryNum) {
                1 -> FeedbackData(
                    isPositive = true,
                    text = "조",
                    tryNum = tryNum
                )
                2 -> FeedbackData(
                    isPositive = false,
                    text = "좋아요! 올바른 선택이에요.",
                    tryNum = tryNum
                )
                else -> FeedbackData(
                    isPositive = true,
                    text = "잘했습니다!",
                    tryNum = tryNum
                )
            }
            else -> when (tryNum) {
                1-> FeedbackData(
                    isPositive = true,
                    text = "부정",
                    tryNum = tryNum
                )
                else -> FeedbackData(
                    isPositive = true,
                    text = "이야기의 마지막까지 잘 해냈습니다!",
                    tryNum = tryNum
                )
            }
        }
    }

    // --- 매뉴얼 관련 ---
    // 생성 전 선택 화면 사용 매뉴얼
    val manuals = listOf(
        ManualScriptData(step = ConversationStep.START, nextStory = "동화를 만들기 위한 선택이 모두 끝나면,\n지금까지 고른 내용으로 제가 4번에 걸쳐 이야기를 만들어 줄 거예요." +
                "\n\n제 질문에 하나씩 답하면서 우리 함께 재미있는 동화를 만들어 봐요!"),
        ManualScriptData(step = ConversationStep.STORY, nextStory = "옛날 옛적 헌트릭스와 사자보이즈의 전쟁 이후 한국은 평화로운 나날이 이어지고 있었어요. 하지만 장신남은 고민이 있었답니다."),
        ManualScriptData(step = ConversationStep.STORY, nextStory = "장신남은 이마트24에서 신라면을 먹을지 삼양 라면을 먹을지 고민하고 있었어요. 거기서 죽은 장신남은 닯은 사람을 발견했어요."),
        ManualScriptData(step = ConversationStep.QUESTION,  question = "장신남은 그 사람을 보고 어떻게 행동했을까?"),
        ManualScriptData(step = ConversationStep.QUESTION, question = "제 질문에 대한 답을 생각했다면\n아래의 마이크를 눌러서 대답해 주세요!"),
        ManualScriptData(step = ConversationStep.ANSWER),
        ManualScriptData(step = ConversationStep.FEEDBACK, feedback = FeedbackData(true, "맞아요, 앞으로도 그렇게 대답 해주면 돼요.", 1)),
        ManualScriptData(step = ConversationStep.FEEDBACK, feedback = FeedbackData(false, "만약 동화 내용과 안맞는 답변이나 나쁜 말을 사용하면 다시 답변해야 해요.", 2)),
        ManualScriptData(step = ConversationStep.FEEDBACK, feedback = FeedbackData(true, "답변은 총 세번이니 잘 생각해보고 답변해 주세요!", 3)),
    )

    private fun updateManual(index: Int) {

        val script = manuals[index]

        val message = when (script.step) {
            ConversationStep.STORY -> script.nextStory
            ConversationStep.QUESTION -> script.question
            ConversationStep.FEEDBACK -> script.feedback.text
            else -> ""
        }
        manualManager.update(message)

        _conversationUiState.value = ConversationUiState.Success(
            sessionId = -1L,
            nextStory = message,
            questionData = QuestionData(
                messageId = -1,
                question = message
            ),
            answerData = AnswerData("", ""),
            feedbackData = script.feedback,
            conversationStep = script.step,
            loopStep = 1
        )
    }

    fun startManual() {
        _manualStep.value = 0
        manualManager.update(manuals[0].nextStory)
    }

    fun nextManual() {
        val current = _manualStep.value

        if (current < manuals.lastIndex) {
            val next = current + 1

            _manualStep.value = next
            updateManual(next)
        } else if (current == manuals.lastIndex) {
            _manualStep.value = manuals.size
        }
    }

    fun stopManual() = manualManager.stop()

    fun hideManual() = manualManager.clear()
}
