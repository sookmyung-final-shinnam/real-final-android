package com.veryshinnam.myapp.feature.creation.ui.conversation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.veryshinnam.myapp.core.speech.tts.TtsManager
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationResponse
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    // repository 주입은 일단 안씀
    private val tts: TtsManager
) : ViewModel() {

    private val _convUiState = mutableStateOf(ConversationUiState())
    val convUiState: State<ConversationUiState> = _convUiState

    // ui에서 tts 바로 쓸 수 있도록 노출
    val isTtsReady: StateFlow<Boolean> = tts.isReady

    fun startConversationDummy(req: StartConversationRequest) {
        android.util.Log.d("ConversationStsrtScreen", "넘겨받은 req = $req")

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

    // tts 관련 제어
    // tts 시작
    fun startTts(story: String) {
        if (story.isNotBlank()) tts.speak(story, flush = true)
    }

    // tts 중단
    fun stopTts() {
        tts.stop()
    }
}
