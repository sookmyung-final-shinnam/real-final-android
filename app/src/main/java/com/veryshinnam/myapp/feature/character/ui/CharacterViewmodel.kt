package com.veryshinnam.myapp.feature.character.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    /** 더미 로딩 (GET만 있다고 했으니 조회만) */
    fun loadDummyCharacter(id: Long) = viewModelScope.launch {
        _charUiState.value = CharacterUiState.Loading
        delay(500) // 로딩감

        val dummy = when (id) {
            11L -> CharacterUiState.CharacterData(
                characterId = 11,
                name = "소피",
                gender = "FEMALE",
                age = 11,
                imageUrl = "https://ifh.cc/g/QP5O4d.png",
                personality = "상냥하고 친구들을 잘 챙김",
                important = false,
                storyId = 11,
                storyTitle = "숲속 마을의 친구들",
                videoId = null,
                videoTitle = null,
                createdAt = "2025-01-01"
            )
            12L -> CharacterUiState.CharacterData(
                characterId = 12,
                name = "카일",
                gender = "MALE",
                age = 12,
                imageUrl = "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg",
                personality = "활발하고 모험을 좋아함",
                important = true,
                storyId = null,
                storyTitle = null,
                videoId = 1,
                videoTitle = "바람의 언덕",
                createdAt = "2025-01-01"
            )
            13L -> CharacterUiState.CharacterData(
                characterId = 13,
                name = "엘라",
                gender = "FEMALE",
                age = 13,
                imageUrl = "https://ifh.cc/g/XTGSPy.png",
                personality = "지혜롭고 용감함",
                important = false,
                storyId = 13,
                storyTitle = "빛과 그림자",
                videoId = 1,
                videoTitle = "바람의 언덕",
                createdAt = "2025-01-01"
            )
            18L -> CharacterUiState.CharacterData(
                characterId = 18,
                name = "민수",
                gender = "MALE",
                age = 11,
                imageUrl = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png",
                personality = "민수는 긍정적이고 모험심이 강한 소년으로, 친구를 아끼고 사랑을 꿈꾸는 따뜻한 마음을 지니고 있어요.",
                important = false,
                storyId = 11,
                storyTitle = "민수와 깜찍한 요정의 사랑 모험",
                videoId = null,
                videoTitle = null,
                createdAt = "2025-01-01"
            )
            else -> null
        }

        _charUiState.value = dummy?.let { CharacterUiState.Success(it) }
            ?: CharacterUiState.Error("캐릭터($id)를 찾을 수 없습니다.")

//        _charUiState.value = CharacterUiState.Error("캐릭터($id)를 찾을 수 없습니다.")
    }

    // 다시 조회
    fun reload(id: Long) {
        loadDummyCharacter(id)
    }

   // 즐겨찾기
    fun toggleImportantLocal() {
       val cur = _charUiState.value
       if (cur !is CharacterUiState.Success) return
       if (cur.favoriteInFlight) return // 중복 탭 방지

       val before = cur.data.important
       val after = !before

       // 1) 낙관적 업데이트
       _charUiState.value = cur.copy(
           data = cur.data.copy(important = after),
           favoriteInFlight = true
       )

       viewModelScope.launch {
           val result = runCatching {
//               repo.setFavorite(id, after) // ← 실제 API 호출
           }
           result.onSuccess { resp ->
               val now = _charUiState.value
               if (now is CharacterUiState.Success) {
                   _charUiState.value = now.copy(
//                       data = now.da?ta.copy(important = resp.),
                       favoriteInFlight = false
                   )
               }
           }.onFailure {
               val now = _charUiState.value
               if (now is CharacterUiState.Success) {
                   // 실패 → 롤백
                   _charUiState.value = now.copy(
                       data = now.data.copy(important = before),
                       favoriteInFlight = false
                   )
               }
           }
       }
    }
}
