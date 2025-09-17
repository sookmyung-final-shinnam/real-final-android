package com.veryshinnam.myapp.feature.character.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.character.model.CharacterData
import com.veryshinnam.myapp.feature.character.model.StoryVideoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
                storyData = StoryVideoData(
                    title = "숲속 마을의 친구들",
                    storyId = 1,
                    storyImage = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/1/page_4.png",
                    videoImage = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/1/page_4.png"
                )
            )

            20L -> CharacterUiState.Success(
                characterData = CharacterData(
                    12,
                    "파워",
                    "FEMALE",
                    12,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_20.png",
                    "활발하고 모험을 좋아함",
                    "2025-11-16",
                    true
                ),
                storyData = StoryVideoData(
                    title = "집에 가지마, 베이베",
                    storyId = null,
                    storyImage = null,
                    videoImage = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_20.png"
                )
            )

            18L -> CharacterUiState.Success(
                characterData = CharacterData(
                    13,
                    "초록이",
                    "MALE",
                    13,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png",
                    "지혜롭고 용감함",
                    "2025-01-13",
                    false
                ),
                storyData = StoryVideoData(
                    title = "빛과 그림자",
                    storyId = 18,
                    storyImage = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png",
                    videoImage = null
                )
            )

            19L -> CharacterUiState.Success(
                characterData = CharacterData(
                    19,
                    "민수",
                    "FEMALE",
                    11,
                    "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_19.png",
                    "긍정적이고 모험심이 강함.",
                    "2025-01-11",
                    false
                ),
                storyData = StoryVideoData(
                    title = "민수와 깜찍한 요정의 사랑 모험",
                    storyId = 19,
                    storyImage = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_2.png",
                    videoImage = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/3/videos/page_1.mp4"
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
