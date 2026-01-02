package com.veryshinnam.myapp.core.manual

import com.veryshinnam.myapp.common.model.ManualState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ManualManager @Inject constructor() {

    private val _state = MutableStateFlow(ManualState.NONE)
    val state = _state.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun request() {
        _state.value = ManualState.REQUEST
        _message.value = "스토릭터에 처음 오셨네요!\n사용하는 방법을 같이 알아볼까요?"
    }

    fun start() {
        _state.value = ManualState.START
    }

    fun update(msg: String) {
        _message.value = msg
    }

    fun finish() {
        _state.value = ManualState.FINISH
        _message.value = "지금까지 긴 설명을 따라오느라 수고했어요!"
    }

    fun stop() {
        _state.value = ManualState.STOP
        _message.value = "알았어요!\n사용 방법은 홈 화면의 설정에서 언제든 다시 볼 수 있어요!"
    }

    fun clear() {
        _state.value = ManualState.NONE
        _message.value = ""
    }
}