package com.veryshinnam.myapp.feature.character.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.character.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _charUiState = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val charUiState = _charUiState.asStateFlow()

    // 캐릭터 상세 불러오기
    fun fetchCharacter(id: Long) {
        viewModelScope.launch {
            _charUiState.value = CharacterUiState.Loading
            try {
                val characterDetail = repository.getCharacterDetail(id) // api 호출
                _charUiState.value = CharacterUiState.Success(
                    characterData = characterDetail
                )
            } catch (e: Exception) {
                _charUiState.value =
                    CharacterUiState.Error("캐릭터($id) 불러오기 실패: ${e.message}")
            }
        }
    }

    // 다시 조회
    fun reload(id: Long) {
        fetchCharacter(id)
    }

    // 캐릭터 즐겨찾기 업데이트
    fun updateFavorite(id: Long) {
        val currentState = _charUiState.value
        if (currentState is CharacterUiState.Success) {
            val character = currentState.characterData
            if (character.id == id) {

                // ui 먼저 반영
                val updatedCharacter = character.copy(isFavorite = !character.isFavorite)
                _charUiState.value = currentState.copy(characterData = updatedCharacter)

                // 서버 반영
                viewModelScope.launch {
                    try { // api 호출
                        if (updatedCharacter.isFavorite) {
                            repository.addFavorite(id)
                        } else {
                            repository.removeFavorite(id)
                        }
                    } catch (e: Exception) {
                        // 실패 시 상태 복구
                        _charUiState.value = currentState
                    }
                }
            }
        }
    }

    // 동화 영상 해제
    fun fetchVideoStory(sId: Long) {
        viewModelScope.launch {
            try {
                repository.generateVideo(sId)
            } catch (e: Exception) {
                _charUiState.value = CharacterUiState.Error(
                    "동화 영상 해제 실패: ${e.message}"
                )
            }
        }
    }

    // 캐릭터 동화 정보 새로고침
    fun refreshStories(id: Long) {
        viewModelScope.launch {
            try {
                val newStory = repository.getStories(id)
                val currentState = _charUiState.value
                if (currentState is CharacterUiState.Success && currentState.characterData.id == id) {
                    // CharacterData 안의 stories 필드만 갱신
                    val updatedCharacter = currentState.characterData.copy(
                        stories = newStory
                    )
                    _charUiState.value = currentState.copy(characterData = updatedCharacter)
                }
            } catch (e: Exception) { }
        }
    }
}