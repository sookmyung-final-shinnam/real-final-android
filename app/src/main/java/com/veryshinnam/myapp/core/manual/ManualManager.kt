package com.veryshinnam.myapp.core.manual

import com.veryshinnam.myapp.common.model.ManualState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ManualManager @Inject constructor() {

    private val _state = MutableStateFlow(ManualState.NONE)
    val state = _state.asStateFlow()     // 매뉴얼 상태

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow() // 매뉴얼 문구 (전역)

    private val _step = MutableStateFlow(0)
    val step = _step.asStateFlow()  // 매뉴얼 진행 단계 (전역)

    // 신규 회원 > 매뉴얼 진행 요청
    fun request() {
        _state.value = ManualState.REQUEST
        _message.value = "스토릭터에 처음 오셨네요!\n사용하는 방법을 같이 알아볼까요?"
    }

    // 매뉴얼 진행 시작
    fun start() {
        _state.value = ManualState.START
        _step.value = 1
    }

    // 매뉴얼 문구 업데이트
    fun update(msg: String) {
        _message.value = msg
    }

    // 매뉴얼 진행 종료 (홈 스크린 다시)
    fun finish() {
        _state.value = ManualState.FINISH
        _message.value = "지금까지 긴 설명을 따라오느라 수고했어요!"
    }

    // 매뉴얼 진행 강제 중지
    fun stop() {
        _state.value = ManualState.STOP
        _message.value = "알았어요!\n사용 방법은 홈 화면의 설정에서 언제든 다시 볼 수 있어요!"
    }

    // 매뉴얼 초기화
    fun clear() {
        _state.value = ManualState.NONE
        _message.value = ""
        _step.value = 0
    }

    // 매뉴얼 진행 단계 (전역)
    fun nextStep() {
        if(_step.value >= 49) return
        _step.value += 1
    }
}