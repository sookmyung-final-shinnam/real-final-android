package com.veryshinnam.myapp.feature.creation.conversation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.core.speech.stt.SttManager
import com.veryshinnam.myapp.core.speech.tts.TtsManager
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationResponse
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    // repository 주입은 일단 안씀
    private val tts: TtsManager,
    private val stt: SttManager
) : ViewModel() {

    private val _convUiState = mutableStateOf(ConversationUiState())
    val convUiState: State<ConversationUiState> = _convUiState

    // ui에서 바로 쓸 수 있도록 준비
    val isTtsReady: StateFlow<Boolean> = tts.isReady
    val isSttReady: StateFlow<Boolean> = stt.isReady
    val isSttListening: StateFlow<Boolean> = stt.isListening

    // STT 이벤트 수집
    init {
        viewModelScope.launch {
            stt.events.collect { ev ->
                when (ev) {
                    is SttManager.SttEvent.Ready -> {
                        _convUiState.value = _convUiState.value.copy(
                            phase = ConversationPhase.LISTENING,
                            errorMessage = null
                        )
                    }
                    is SttManager.SttEvent.Partial -> {
                        _convUiState.value = _convUiState.value.copy(partialAnswer = ev.text)
                    }
                    is SttManager.SttEvent.Final -> {
                        _convUiState.value = _convUiState.value.copy(
                            userAnswer = ev.text,
                            partialAnswer = null
                        )
                        // 피드백은 현재 요구 범위에서 제외
                    }
                    is SttManager.SttEvent.Error -> {
                        _convUiState.value = _convUiState.value.copy(
                            errorMessage = "STT 오류(${ev.code}): ${ev.message}",
                            phase = ConversationPhase.ASKING
                        )
                    }
                    is SttManager.SttEvent.End -> Unit
                }
            }
        }
    }

    // 캐릭터 선택 이후 대화 시작
    fun startConversationDummy(req: StartConversationRequest) {
        Log.d("ConversationStsrtScreen", "넘겨받은 req = $req")

        // 로딩 시작
        _convUiState.value = _convUiState.value.copy(isLoading = true)

        // 더미 데이터
        // TODO: api 연결
        val response = StartConversationResponse(
            isSuccess = true,
            code = "COMMON_200",
            message = "성공입니다.",
            result = StartConversationResult(
                sessionId = 17,
                nextStory = "우주의 끝자락에서 붉은 눈과 보라색 머리를 가진 테스트가 떠다니며 사랑과 건강을 찾기 위한 모험을 시작했어요.",
                currentStep = "START"
            )
        )

        if (response.isSuccess) {
            _convUiState.value = ConversationUiState(
                sessionId = response.result.sessionId,
                currentStep = response.result.currentStep,
                nextStory = response.result.nextStory,
                isLoading = false
            )
        } else {
            _convUiState.value = _convUiState.value.copy(
                isLoading = false,
                errorMessage = "대화 시작 실패"
            )
        }
    }

    // 줄거리 및 질문 진행
    fun processConversation(sessionId: Long, currentStep: String) {
        stopAll()

        // 현재 단계에 따른 다음 단계 준비
        val next = when (currentStep.uppercase()) {
            "START" -> "STEP_01"
            "STEP_01" -> "STEP_02"
            "STEP_02" -> "STEP_03"
            else -> "END"
        }

        if (next == "END") {}

        // 로딩 시작
        // llm 질문 텍스트는 유지해도 되지만 답변은 비우기
        _convUiState.value = _convUiState.value.copy(
            sessionId = sessionId,
            currentStep = next,
            isLoading = true,
            phase = ConversationPhase.FETCHING_QUESTION,
            userAnswer = null,
            partialAnswer = null,
            errorMessage = null
        )

        // 더미 실패 응답
        val simulatePending = false
        if (simulatePending) {
            _convUiState.value = _convUiState.value.copy(
                isLoading = false, phase = ConversationPhase.PENDING
            )
            return
        }

        // 더미 성공 응답: next에 따라 다른 메시지
        val (story, q) = when (next) {
            "STEP_01" -> "테스트는 반짝이는 별들 사이에서 이상한 소리를 들었어요. 그곳엔 요정 길버트가 있었어요." to
                    "길버트는 테스트에게 어떤 도움을 줄까요?"
            "STEP_02" -> "길버트는 반짝이는 가루를 뿌리며 테스트의 마음을 들여다보았어요." to
                    "테스트의 마음 속 가장 큰 소원은 무엇일까요?"
            else      -> "하늘길이 열리고 새로운 행성이 모습을 드러났어요." to
                    "그 행성에서 테스트는 무엇을 배울까요?"
        }

        _convUiState.value = _convUiState.value.copy(
            nextStory = story, llmQuestion = q, isLoading = false, phase = ConversationPhase.ASKING
        )

        // 스토리 → 질문 읽기
        if (story.isNotBlank()) tts.speak(story, flush = true)
        if (q.isNotBlank())     tts.speak(q,     flush = false)
    }


    // tts 관련 제어
    // tts 시작
    fun startTts(story: String) {
        if (story.isNotBlank()) tts.speak(story, flush = true)
    }

    // tts 다시 시작
    fun replayTts() {
        val s = _convUiState.value.nextStory.orEmpty()
        val q = _convUiState.value.llmQuestion.orEmpty()
        if (s.isNotBlank()) tts.speak(s, flush = true)   // 끊고 시작
        if (q.isNotBlank()) tts.speak(q, flush = false)  // 큐에 이어서
    }

    // tts 중단
    fun stopTts() {
        tts.stop()
    }

    // stt 제어
    // stt 시작
    fun startStt(lang: Locale = Locale.KOREAN) {
        tts.stop()
        _convUiState.value = _convUiState.value.copy(
            phase = ConversationPhase.LISTENING,
            partialAnswer = null,
            errorMessage = null
        )
        // 메인에서 호출되는게 보장
        stt.start(lang.toLanguageTag())
    }

    fun stopAll() { tts.stop(); stt.stop() }
}
