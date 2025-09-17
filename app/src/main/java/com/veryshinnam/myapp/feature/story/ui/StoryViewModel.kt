package com.veryshinnam.myapp.feature.story.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.core.speech.tts.TtsManager
import com.veryshinnam.myapp.feature.story.data.ApiStoryContent
import com.veryshinnam.myapp.feature.story.data.ApiStoryResult
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryData
import com.veryshinnam.myapp.feature.story.model.StoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val tts: TtsManager
) : ViewModel() {

    private val _storyUiState = MutableStateFlow<StoryUiState>(StoryUiState.Loading)
    val storyUiState = _storyUiState.asStateFlow()

    val isTtsReady = tts.isReady
    val isTtsSpeaking = tts.isSpeaking

    // 스토리 페이지 데이터 호출
    fun fetchStoryDetail(storyId: Long, storyType: StoryType) {
        viewModelScope.launch {
            _storyUiState.value = StoryUiState.Loading
            try {
                delay(300) // 로딩감

                // 프롤로그 (page 0)
                val prologueImage = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/$storyId/page_4.png"
                val prologueVideo = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/$storyId/videos/page_4.mp4"

                val storyData = StoryData(
                    story = storyType,
                    storyId = storyId,
                    thumbnail = if (storyType == StoryType.IMAGE) prologueImage else prologueVideo,
                    title = "지우의 모험과 친구가 되는 능력",
                    tags = listOf("모험", "우정", "푸른 숲속").joinToString(", "),
                    description = "지우는 숲속에서 만난 요정 덕분에..."
                )

                val page0 = PageData(0, prologueImage, "")

                // 본문 1~4 더미 데이터 생성
                val pages = (1..4).map { pageNumber ->
                    val content = when (pageNumber) {
                        1 -> "푸른 숲속에 지우가 살고 있었어요. 오늘은 새로운 친구를 찾는 날!"
                        2 -> "어쩌구 저쩌구"
                        3 -> "모험이 시작되었어요."
                        4 -> "마지막 테스트 페이지 4"
                        else -> ""
                    }

                    val imageUrl = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/$storyId/page_${pageNumber}.png"
                    val videoUrl = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/$storyId/videos/page_${pageNumber}.mp4"

                    PageData(
                        pageId = pageNumber.toLong(),
                        image = if (storyType == StoryType.IMAGE) imageUrl else videoUrl,
                        content = content
                    )
                }

                // 최종 State: 프롤로그 + 본문 4장
                _storyUiState.value = StoryUiState.Success(
                    storyData = storyData,
                    pagesData = listOf(page0) + pages,
                    isPrologue = true,
                    isTtsMode = true
                )

            } catch (e: Exception) {
                _storyUiState.value = StoryUiState.Error("스토리 데이터를 불러오는데 실패했습니다.")
            }
        }
    }

    // 프롤로그 스크린 이동
    fun goToPrologue() {
        val current = _storyUiState.value
        if (current is StoryUiState.Success) {
            val newState = current.copy(isTtsMode = !current.isTtsMode)
            _storyUiState.value = newState

            if (!newState.isTtsMode) {
                stopSpeaking() // OFF로 바뀌면 중단
            }
        }
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