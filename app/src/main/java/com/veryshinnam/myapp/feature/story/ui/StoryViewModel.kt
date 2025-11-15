package com.veryshinnam.myapp.feature.story.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.core.speech.tts.TtsManager
import com.veryshinnam.myapp.feature.story.data.repository.StoryRepository
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryData
import com.veryshinnam.myapp.feature.story.model.StoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val ttsManager: TtsManager,
    private val repository: StoryRepository
) : ViewModel() {

    private val _storyUiState = MutableStateFlow<StoryUiState>(StoryUiState.Loading)
    val storyUiState = _storyUiState.asStateFlow()

    val isTtsReady = ttsManager.isReady
    val isTtsSpeaking = ttsManager.isSpeaking

    // 동화 프롤로그 + 페이지 불러오기
    fun fetchStory(storyId: Long, storyType: StoryType) {
        viewModelScope.launch {
            _storyUiState.value = StoryUiState.Loading
            try {
                // api 호출
                val prologue = repository.getPrologue(storyId, storyType)
                val pages = repository.getPages(storyId, 4, storyType)
//
//                val prologue = dummyPrologue()
//                val pages = dummyPages()

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
                stopSpeaking() // tts 중단
            }
        }
    }

    fun speakPage(text: String) {
        Log.d("storyTTS", "speakPage 호출됨: $text")
        ttsManager.speak(text, flush = true)
    }

    fun stopSpeaking() {
        ttsManager.stop()
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.stop()
    }


    // 더미
    fun dummyPrologue(): StoryData =
     StoryData(
            id = 16L,
            title = "사막의 빛과 우정",
            tags = "모험, 판타지, 숲",
            description = "옛날 옛적, 작은 마을에 사는 아이가 신비한 숲으로 모험을 떠나는 이야기입니다.",
            thumbnail = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/16/page_4.png"
        )

    val dummyPages = listOf(
        PageData(
            id = 1,
            url = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/16/page_4.png",
            content = "아이와 친구는 숲 속에서 반짝이는 길을 발견했습니다."
        ),
        PageData(
            id = 2,
            url = "https://example.com/page2.png",
            content = "길을 따라가자 신비한 호수가 나타났습니다."
        ),
        PageData(
            id = 3,
            url = "https://example.com/page3.png",
            content = "호수 위에는 빛나는 나비들이 날아다니고 있었습니다."
        ),
        PageData(
            id = 4,
            url = "https://example.com/page4.png",
            content = "아이들은 나비를 따라 깊은 숲 속 성으로 향했습니다."
        )
    )
}