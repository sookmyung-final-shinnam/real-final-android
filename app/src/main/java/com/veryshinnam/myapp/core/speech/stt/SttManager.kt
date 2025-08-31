package com.veryshinnam.myapp.core.speech.stt

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

interface SttManager: AutoCloseable {

    //  STT 엔진이 초기화되어 사용 가능한지
    val isReady: StateFlow<Boolean>

    // 마이크 오픈 상태
    val isListening: StateFlow<Boolean>

    // 발생 가능한 stt 이벤트
    val events: SharedFlow<SttEvent>

    fun start(langTag: String = Locale.KOREAN.toLanguageTag())
    fun stop()
    override fun close()

    // STT는 비동기 이벤트 스트림 > 준비완료/부분결과/최종결과/오류/종료 발생
    sealed class SttEvent {
        data object Ready : SttEvent()
        data class Partial(val text: String) : SttEvent()
        data class Final(val text: String) : SttEvent()
        data class Error(val code: Int, val message: String?) : SttEvent()
        data object End : SttEvent()
    }
}

