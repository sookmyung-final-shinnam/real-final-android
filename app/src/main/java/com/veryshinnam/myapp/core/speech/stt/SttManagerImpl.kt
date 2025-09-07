package com.veryshinnam.myapp.core.speech.stt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SttManagerImpl @Inject constructor(
    @ApplicationContext private val app: Context
): SttManager {

    // 안드로이드 음성 인식 엔진 등록
    private var recognizer: SpeechRecognizer? = null

    // 준비 상태
    private val _isReady = MutableStateFlow(false)
    override val isReady = _isReady.asStateFlow()

    // 녹음 상태
    private val _isListening = MutableStateFlow(false)
    override val isListening = _isListening.asStateFlow()

    // STT 진행시 발생 가능한 상태
    // extraBufferCapacity: 수신 측이 느릴 때 임시 버퍼
    private val _events = MutableSharedFlow<SttManager.SttEvent>(extraBufferCapacity = 8)
    override val events = _events.asSharedFlow()

    private fun ensureRecognizer() {
        if (recognizer != null) return
        recognizer = SpeechRecognizer.createSpeechRecognizer(app).apply {
            setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    _events.tryEmit(SttManager.SttEvent.Ready)
                    _isListening.value = true
                }

                override fun onEvent(eventType: Int, params: Bundle?) {}
                override fun onRmsChanged(rmsdB: Float) {} // 음량 변화
                override fun onBufferReceived(buffer: ByteArray?) {}

                // 사용자 발화 시작
                override fun onBeginningOfSpeech() {}

                // 사용자 발화 끝
                override fun onEndOfSpeech() {}

                // 사용자 발화 부분 인식 결과 (스트리밍)
                override fun onPartialResults(results: Bundle?) {
                    val text = results?.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                    )?.firstOrNull().orEmpty()
                    if (text.isNotBlank()) _events.tryEmit(SttManager.SttEvent.Partial(text))
                }

                // 최종 음성 인식 결과 (문장 단위)
                override fun onResults(results: Bundle?) {
                    val text = results?.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                    )?.firstOrNull().orEmpty()
                    _events.tryEmit(SttManager.SttEvent.Final(text))
                    _events.tryEmit(SttManager.SttEvent.End)
                    _isListening.value = false
                }

                // 음성 인식 에러
                override fun onError(error: Int) {
                    _events.tryEmit(SttManager.SttEvent.Error(error, error.toString()))
                    _events.tryEmit(SttManager.SttEvent.End)
                    _isListening.value = false
                }
            })
        }
        _isReady.value = true
    }

    // 음성 인식 시작
    override fun start(langTag: String) {

        // 기기가 음석 인식 가능한지 확인
        if (!SpeechRecognizer.isRecognitionAvailable(app)) {
            _events.tryEmit(SttManager.SttEvent.Error(-1, "음성 인식을 이용할 수 없습니다."))
            _isReady.value = false
            return
        }

        ensureRecognizer()

        // 음성 인식 세부 설정
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, langTag) // 인식할 언어
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true) // 부분 인식 결과 표시
            putExtra(
                // 언어 모델
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            // 음성 인식 반환 결과
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        // 음성 인식 시작
        try {
            recognizer?.startListening(intent)
        } catch (e: Exception) {
            _events.tryEmit(SttManager.SttEvent.Error(-2, e.message))
            _events.tryEmit(SttManager.SttEvent.End)
            _isListening.value = false
        }
    }

    // 음성 인식 즉시 중단 (엔진 유지)
    override fun stop() {
        recognizer?.stopListening()
        _isListening.value = false
    }

    // 음성 인식 종료 (엔진 반납)
    override fun close() {
        recognizer?.destroy()
        recognizer = null
        _isListening.value = false
        _isReady.value = false
    }
}