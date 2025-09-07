package com.veryshinnam.myapp.feature.story.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryData
import dagger.hilt.android.lifecycle.HiltViewModel
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

                val data  = getDummyStoryData(storyId)
                val pages = getDummyPageData(storyId)

                _storyUiState.value = StoryUiState.Success(
                    data = data,
                    pages = pages
                )
            } catch (e: Exception) {
                _storyUiState.value = StoryUiState.Error("스토리 데이터를 불러오는데 실패했습니다.")
            }
        }
    }

    // 스토리 기본 정보
    private suspend fun getDummyStoryData(storyId: Long): StoryData {
        delay(100)
        return StoryData(
            title = "민수와 깜찍한 요정의 사랑 모험",
             "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", // 표지
             "민수와 깜찍한 요정의 사랑 모험의 내용 테스트"
        )
    }

    // 페이지 더미 데이터
    private suspend fun getDummyPageData(storyId: Long): List<PageData> {
        delay(100)

        return listOf(
            PageData(
                 1,
                 "https://i.ibb.co/RG7Fxwrx/img-calender.png",
                 "민수는 숲 속 깊은 곳에서 빛나는 나무를 발견했다."
            ),
            PageData(
                 2,
                 "https://i.ibb.co/27QsVLDT/img-prince.png",
                 "민수는 배가 고픈 다람쥐 토미를 만났다."
            ),
            PageData(
                 3,
                 "https://i.ibb.co/FkBMgqGS/Kakao-Talk-20250424-120644425.png",
                 "\"가진 게 있으면 모두 다 내놔\"라고 외치는 토미에게 사과를 건네주었다."
            ),
            PageData(
                 4,
                 "https://i.ibb.co/DDvNSjv5/image.png",
                 "민수와 토미는 결혼을 해서 행복하게 살았다."
            )
        )
    }
}