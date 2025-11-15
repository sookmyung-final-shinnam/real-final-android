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
        _message.value = "스토릭터에 처음 오신 것 같은데, 스토릭터를 사용하는 방법을 들을래요?"
    }

    fun start() {
        _state.value = ManualState.START
    }

    fun update(msg: String) {
        _message.value = msg
    }

    fun finish() {
        _state.value = ManualState.FINISH
        _message.value = "지금까지 긴 설명을 듣느라 수고하셨어요!\n이제 스토릭터를 자유롭게 사용해 보세요!"
    }

    fun stop() {
        _state.value = ManualState.STOP
        _message.value = "그래요!\n나중에도 설명이 필요하다면 환경 설정에서 버튼을 통해 언제든지 다시 들을 수 있어요."
    }

    fun clear() {
        _state.value = ManualState.NONE
        _message.value = ""
    }
}