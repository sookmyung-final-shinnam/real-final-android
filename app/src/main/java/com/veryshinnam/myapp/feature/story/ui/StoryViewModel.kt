package com.veryshinnam.myapp.feature.story.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.story.model.PageData
import com.veryshinnam.myapp.feature.story.model.StoryData
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
            _storyUiState.value = current.copy(isPrologue = true)
        }
    }

    // 동화보기 스크린 이동
    fun goToReader() {
        val current = _storyUiState.value
        if (current is StoryUiState.Success) {
            _storyUiState.value = current.copy(isPrologue = false)
        }
    }

    // tts 설정 버튼
    fun setSpeaking(enabled: Boolean) {
        val current = _storyUiState.value
        if (current is StoryUiState.Success) {
            _storyUiState.value = current.copy(isSpeaking = enabled)
        }
    }

    // 스토리 기본 정보
    private suspend fun getDummyStoryData(storyId: Long): StoryData {
        delay(100)
        return when (storyId) {
            11L -> StoryData(
                11,
                "https://ifh.cc/g/QP5O4d.png", // 표지
                "용감한 토끼 유리의 대모험",
                "#동화 #용기 #성장",
                "유리는 깊은 동굴 속에서 반짝이는 보석을 찾기 위해 모험을 떠나는데 ..."
            )

            20L -> StoryData(
                20,
                "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_20.png", // 표지
                "파워와 시간여행 기차",
                "#시간여행 #우정 #호기심 ",
                "우연히 발견한 신비한 기차표로 파워는 과거와 미래를 오가며 놀라운 경험을 하게 된다 ..."
            )

            18L -> StoryData(
                18,
                "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", // 표지
                "초록이와 깜찍한 요정의 사랑 모험",
                "#해시태그1 #해시태그2 #해시태그3 #해시태그4",
                "초록이는 숲 속 깊은 곳에서 빛나는 나무를 발견하게 되는데 ..."
            )

            19L -> StoryData(
                19,
                "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_19.png", // 표지
                "바닷속 친구들과 민수의 여름",
                "#바다 #우정 #환상",
                "뜨거운 여름날, 민수는 바닷속 세계로 들어가 다양한 해양 친구들과 즐거운 하루를 보낸다 ..."
            )

            else -> StoryData(
                -1,
                "",
                "알 수 없는 이야기",
                "#none",
                "등록되지 않은 스토리입니다."
            )
        }
    }

    // 페이지 더미 데이터
    private suspend fun getDummyPageData(storyId: Long): List<PageData> {
        delay(100)

        return when (storyId) {
            18L -> listOf(
                PageData(
                    1,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/18/page_1.png",
                    "초록이는 숲 속 깊은 곳에서 빛나는 나무를 발견했다."
                ),
                PageData(
                    2,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/18/page_2.png",
                    "나무 곁에서 깜찍한 요정을 만났다."
                ),
                PageData(
                    3,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/18/page_3.png",
                    "요정은 초록이에게 사랑의 마법을 보여주었다."
                ),
                PageData(
                    4,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/18/page_4.png",
                    "둘은 함께 숲의 수호자가 되었다."
                )
            )

            19L -> listOf(
                PageData(
                    1,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_1.png",
                    "민수는 바닷속 세계로 들어갔다."
                ),
                PageData(
                    2,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_2.png",
                    "거대한 문어와 작은 물고기 친구들을 만났다."
                ),
                PageData(
                    3,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_3.png",
                    "민수와 친구들은 바다의 보물을 찾기 위해 모험을 떠났다."
                ),
                PageData(
                    4,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_4.png",
                    "즐거운 여름을 보내고 무사히 집으로 돌아왔다."
                )
            )

            else -> emptyList()
        }
    }
}