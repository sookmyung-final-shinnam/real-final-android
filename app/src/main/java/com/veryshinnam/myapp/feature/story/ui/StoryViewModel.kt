package com.veryshinnam.myapp.feature.story.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.core.speech.tts.TtsManager
import com.veryshinnam.myapp.feature.character.ui.CharacterUiState
import com.veryshinnam.myapp.feature.story.data.repository.StoryRepository
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryData
import com.veryshinnam.myapp.feature.story.model.StoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val tts: TtsManager,
    private val repository: StoryRepository
) : ViewModel() {

    private val _storyUiState = MutableStateFlow<StoryUiState>(StoryUiState.Loading)
    val storyUiState = _storyUiState.asStateFlow()

    val isTtsReady = tts.isReady
    val isTtsSpeaking = tts.isSpeaking

    // 동화 프롤로그 + 페이지 불러오기
    fun fetchStory(storyId: Long, storyType: StoryType) {
        viewModelScope.launch {
            _storyUiState.value = StoryUiState.Loading
            try {
                // api 호출
                val prologue = repository.getPrologue(storyId, storyType)
                val pages = repository.getPages(storyId, 4, storyType)

                // 최종 State: 프롤로그 + 본문 4장
                _storyUiState.value = StoryUiState.Success(
                    storyData = prologue,
                    pagesData = pages,
                    isPrologue = true,
                    isTtsMode = true
                )

            } catch (e: Exception) {
                _storyUiState.value =
                    StoryUiState.Error("동화($storyId) 불러오기 실패: ${e.message}")
            }
        }
    }

    // 프롤로그 스크린 이동
    fun goToPrologue() {
        val current = _storyUiState.value
        if (current is StoryUiState.Success) {
            _storyUiState.value = current.copy(isPrologue = true)
        }
        stopSpeaking() // tts 재생 중단
    }

    // 동화보기 스크린 이동
    fun goToReader() {
        val current = _storyUiState.value
        if (current is StoryUiState.Success) {
            _storyUiState.value = current.copy(isPrologue = false)
        }
    }

    fun changeTtsMode() {
        val current = _storyUiState.value
        if (current is StoryUiState.Success) {
            val newState = current.copy(isTtsMode = !current.isTtsMode)
            _storyUiState.value = newState

            if (!newState.isTtsMode) {
                stopSpeaking() // OFF로 바뀌면 중단
            }
        }
    }

    fun speakPage(text: String) {
        Log.d("storyTTS", "speakPage 호출됨: $text")
        tts.speak(text, flush = true)
    }

    fun stopSpeaking() {
        tts.stop()
    }

    override fun onCleared() {
        super.onCleared()
        tts.stop()
    }
}