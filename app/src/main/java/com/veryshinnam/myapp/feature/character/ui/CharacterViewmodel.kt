package com.veryshinnam.myapp.feature.character.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.character.model.CharacterData
import com.veryshinnam.myapp.feature.character.model.StoryData
import com.veryshinnam.myapp.feature.storage.ui.StorageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(

) : ViewModel() {

    private val _charUiState = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val charUiState = _charUiState.asStateFlow()

    fun loadDummyCharacter(id: Long) = viewModelScope.launch {
        _charUiState.value = CharacterUiState.Loading
        delay(500) // 로딩 감주기

        _charUiState.value = when (id) {
            11L -> CharacterUiState.Success(
                characterData = CharacterData(
                    11,
                    "유리",
                    "FEMALE",
                    11,
                    "https://ifh.cc/g/QP5O4d.png",
                    "상냥하고 친구들을 잘 챙김",
                    "2025-06-01",
                    false
                ),
                storyData = StoryData(
                    title = "숲속 마을의 친구들",
                    storyId = 11,
                    storyImage = "https://ifh.cc/g/QP5O4d.png",
                    videoId = null,
                    videoUrl = null
                )
            )

            12L -> CharacterUiState.Success(
                characterData = CharacterData(
                    12,
                    "파워",
                    "MALE",
                    12,
                    "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg",
                    "활발하고 모험을 좋아함",
                    "2025-11-16",
                    true
                ),
                storyData = StoryData(
                    title = "집에 가지마, 베이베",
                    storyId = null,
                    storyImage = null,
                    videoId = 1,
                    videoUrl = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_2.png"
                )
            )

            13L -> CharacterUiState.Success(
                characterData = CharacterData(
                    13,
                    "파파워",
                    "FEMALE",
                    13,
                    "https://ifh.cc/g/XTGSPy.png",
                    "지혜롭고 용감함",
                    "2025-01-13",
                    false
                ),
                storyData = StoryData(
                    title = "빛과 그림자",
                    storyId = 13,
                    storyImage = "https://ifh.cc/g/XTGSPy.png",
                    videoId = null,
                    videoUrl = null
                )
            )

            18L -> CharacterUiState.Success(
                characterData = CharacterData(
                    18,
                    "민수",
                    "MALE",
                    11,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png",
                    "긍정적이고 모험심이 강함.",
                    "2025-01-11",
                    false
                ),
                storyData = StoryData(
                    title = "민수와 깜찍한 요정의 사랑 모험",
                    storyId = 11,
                    storyImage = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_2.png",
                    videoId = 18,
                    videoUrl = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_2.png"
                )
            )

            else -> CharacterUiState.Error("캐릭터($id)를 찾을 수 없습니다.")
        }
    }

    // 다시 조회
    fun reload(id: Long) {
        loadDummyCharacter(id)
    }

    // 즐겨찾기 업데이트
    fun updateFavorite(cId: Long) {
        val currentState = _charUiState.value
        if (currentState is CharacterUiState.Success) {
            val character = currentState.characterData
            // id 일치 > isFavorite 토글
            if (character.id == cId) {
                val updatedCharacter = character.copy(
                    isFavorite = !character.isFavorite
                )
                _charUiState.value = currentState.copy(
                    characterData = updatedCharacter
                )
            }
        }
    }
}
