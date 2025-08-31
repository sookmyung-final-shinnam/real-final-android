package com.veryshinnam.myapp.core.speech.tts

import android.content.Context
import android.speech.tts.TextToSpeech
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

    // TTS 작동을 위한 준비 완료 상태를 관리
    private val _isReady = MutableStateFlow(false)
    override val isReady = _isReady.asStateFlow()

    // TTS 엔진 초기화
    override fun onInit(status: Int) {

        // SUCCESS 상태가 되어야 준비 완료 상태(_isReady)로 간주
        _isReady.value = (status == TextToSpeech.SUCCESS)
        if (_isReady.value) setLanguage(Locale.KOREAN)
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

        // 아직 준비 완료 상태가 아니거나 빈 문자열이면 생략
        if (!_isReady.value || text.isBlank()) return

        // flush가 true면 현재 재생을 끊고 새 텍스트부터 재생
        // false면 큐에 이어붙여서 순차 재생
        val mode = if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
        tts?.speak(text, mode, null, System.currentTimeMillis().toString())
    }

    // TTS 즉시 중단 (TTS 엔진 유지)
    override fun stop() { tts?.stop() }

    // TTS 종료 (TTS 엔진 반납)
    override fun close() {
        tts?.stop()
        tts?.shutdown() // TTS 엔진 연결 해제
        tts = null
        _isReady.value = false
    }
}