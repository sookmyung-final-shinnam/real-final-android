package com.veryshinnam.myapp.feature.character.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    /** 더미 로딩 (GET만 있다고 했으니 조회만) */
    fun loadDummyCharacter(id: Long) = viewModelScope.launch {
        _charUiState.value = CharacterUiState.Loading
        delay(200) // 로딩감

        val dummy = when (id) {
            11L -> CharacterData(
                characterId = 11,
                name = "소피",
                gender = "FEMALE",
                age = 11,
                imageUrl = "https://ifh.cc/g/QP5O4d.png",
                personality = "상냥하고 친구들을 잘 챙김",
                important = false,
                storyId = 11,
                storyTitle = "숲속 마을의 친구들"
            )
            12L -> CharacterData(
                characterId = 12,
                name = "카일",
                gender = "MALE",
                age = 12,
                imageUrl = "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg",
                personality = "활발하고 모험을 좋아함",
                important = true,
                storyId = 12,
                storyTitle = "바람의 언덕"
            )
            13L -> CharacterData(
                characterId = 13,
                name = "엘라",
                gender = "FEMALE",
                age = 13,
                imageUrl = "https://ifh.cc/g/XTGSPy.png",
                personality = "지혜롭고 용감함",
                important = false,
                storyId = 13,
                storyTitle = "빛과 그림자"
            )
            else -> null
        }

        _charUiState.value = dummy?.let { CharacterUiState.Success(it) }
            ?: CharacterUiState.Error("캐릭터($id)를 찾을 수 없습니다.")
    }

    /** 서버 반영 없이 로컬에서만 즐겨찾기 토글 */
    fun toggleImportantLocal() {
        val s = _charUiState.value
        if (s is CharacterUiState.Success) {
            _charUiState.value = s.copy(
                data = s.data.copy(important = !s.data.important)
            )
        }
    }
}
