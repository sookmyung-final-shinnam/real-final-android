package com.veryshinnam.myapp.core.speech.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.annotation.MainThread
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TtsManagerImpl @Inject constructor(
    @ApplicationContext private val app: Context
): TtsManager, TextToSpeech.OnInitListener {

    // 안드로이드 TTS 엔진 등록
    private var tts: TextToSpeech? = TextToSpeech(app, this)

    // TTS 작동을 위한 준비 상태 관리
    private val _isReady = MutableStateFlow(false)
    override val isReady = _isReady.asStateFlow()

    // TTS 재생 상태 관리
    private val _isSpeaking = MutableStateFlow(false)
    override val isSpeaking = _isSpeaking.asStateFlow()

    // TTS 초기화 전에 들어온 speak 요청 저장
    private var pendingText: Pair<String, Boolean>? = null

    // TTS 엔진 초기화
    override fun onInit(status: Int) {
        Log.d("storyTTS", "onInit 호출됨: status=$status")

        // SUCCESS 상태가 되어야 준비 완료 상태(_isReady)로 간주
        _isReady.value = (status == TextToSpeech.SUCCESS)
        if (_isReady.value) {
            setLanguage(Locale.KOREAN)
            setRate(1.0f)
            setPitch(1.0f)

            // 재생 상태 리스너
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    Log.d("storyTTS", "onStart 실행")
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    Log.d("storyTTS", "onDone 실행")
                    _isSpeaking.value = false
                }

                override fun onError(utteranceId: String?) {
                    Log.d("storyTTS", "onError 실행")
                    _isSpeaking.value = false
                }
            })

            // 대기 중인 텍스트 있으면 바로 재생
            pendingText?.let { (text, flush) ->
                Log.d("storyTTS", "pendingText 재생 실행")
                speak(text, flush)
                pendingText = null
            }
        }
    }

    // TTS 언어 설정
    override fun setLanguage(locale: Locale) {
        tts?.language = locale
    }

    // TTS 속도
    override fun setRate(rate: Float) { tts?.setSpeechRate(rate) }

    // TTS 톤
    override fun setPitch(pitch: Float) { tts?.setPitch(pitch) }

    // TTS 재생
    @MainThread
    override fun speak(text: String, flush: Boolean) {
        Log.d("storyTTS", "speak 호출됨: ready=${_isReady.value}, text=$text")

        //  빈 문자열이거나 아직 준비 완료 상태가 아니면 생략
        if (text.isBlank()) {
            Log.e("storyTTS", "speak 실행 안 함 > 빈 문자열")
            return
        }

        if (!_isReady.value) {
            Log.w("storyTTS", "엔진 준비 안됨 > pendingText 저장")
            pendingText = text to flush
            return
        }

        // flush가 true면 현재 재생을 끊고 새 텍스트부터 재생
        // false면 큐에 이어붙여서 순차 재생
        val mode = if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
        val utteranceId = System.currentTimeMillis().toString()
        val result = tts?.speak(text, mode, null, utteranceId)
        Log.d("storyTTS", "tts.speak 실행 결과=$result")
    }

    // TTS 즉시 중단 (TTS 엔진 유지)
    override fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    // TTS 종료 (TTS 엔진 반납)
    override fun close() {
        tts?.stop()
        tts?.shutdown() // TTS 엔진 연결 해제
        tts = null
        _isReady.value = false
        _isSpeaking.value = false
    }
}