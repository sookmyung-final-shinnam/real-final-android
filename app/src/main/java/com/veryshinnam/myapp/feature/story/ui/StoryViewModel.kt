package com.veryshinnam.myapp.feature.story.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryData
import com.veryshinnam.myapp.feature.story.model.StoryPhase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(

) : ViewModel() {
    private val _storyUiState = MutableStateFlow<StoryUiState>(StoryUiState.Loading)
    val storyUiState = _storyUiState.asStateFlow()

    // 스토리 데이터 호출
    fun loadStoryData(storyId: Long) {
        viewModelScope.launch {
            try {
                _storyUiState.value = StoryUiState.Loading
                delay(300) // 로딩감

                // 동시에 실행
                val (data, pages) = kotlinx.coroutines.coroutineScope {
                    val dataDeferred = async { getDummyStoryData(storyId) }
                    val pagesDeferred = async { getDummyPageData(storyId) }
                    dataDeferred.await() to pagesDeferred.await()
                }
                _storyUiState.value = StoryUiState.Success(
                    storyData = data,
                    pagesData = pages
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
            _storyUiState.value = current.copy(phase = StoryPhase.PROLOGUE)
        }
    }

    // 동화보기 스크린 이동
    fun goToReader() {
        val current = _storyUiState.value
        if (current is StoryUiState.Success) {
            _storyUiState.value = current.copy(phase = StoryPhase.READING)
        }
    }

    // 엔딩 페이지 스크린 이동
    fun goToEnding() {
        val current = _storyUiState.value
        if (current is StoryUiState.Success) {
            _storyUiState.value = current.copy(phase = StoryPhase.ENDING)
        }
    }

    // 스토리 기본 정보
    private suspend fun getDummyStoryData(storyId: Long): StoryData {
        delay(100)
        return StoryData(
            18,
            "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", // 표지
            "민수와 깜찍한 요정의 사랑 모험",
            "#해시태그1 #해시태그2 #해시태그3 #해시태그4",
            "민수와 깜찍한 요정의 사랑 모험의 내용 테스트입니다~~~~~~~~~~~줄거리!!"
        )
    }

    // 페이지 더미 데이터
    private suspend fun getDummyPageData(storyId: Long): List<PageData> {
        delay(100)

        return listOf(
            PageData(
                1,
                "https://i.ibb.co/FkBMgqGS/Kakao-Talk-20250424-120644425.png",
                "민수는 숲 속 깊은 곳에서 빛나는 나무를 발견했다."
            ),
            PageData(
                2,
                "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_2.png",
                "민수는 배가 고픈 다람쥐 토미를 만났다.민수는 배가 고픈 다람쥐 토미를 만났다.민수는 배가 고픈 다람쥐 토미를 만났다.민수는 배가 고픈 다람쥐 토미를 만났다."
            ),
            PageData(
                3,
                "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_3.png",
                "\"가진 게 있으면 모두 다 내놔\"라고 외치는 토미에게 사과를 건네주었다."
            ),
            PageData(
                4,
                "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_4.png",
                "민수와 토미는 결혼을 해서 행복하게 살았다."
            )
        )
    }
}