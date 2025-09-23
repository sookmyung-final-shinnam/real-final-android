package com.veryshinnam.myapp.core.speech.tts

import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

interface TtsManager: AutoCloseable {
    val isReady: StateFlow<Boolean>  // TTS 엔진 준비 여부
    val isSpeaking: StateFlow<Boolean>   // TTS 재생 여부

    fun speak(text: String, flush: Boolean = true)
    fun stop()
    fun setLanguage(locale: Locale = Locale.KOREAN) // 언어 설정
    fun setRate(rate: Float = 1.0f)     // 속도 설정
    fun setPitch(pitch: Float = 1.0f)   // 톤 설정

    override fun close()
}