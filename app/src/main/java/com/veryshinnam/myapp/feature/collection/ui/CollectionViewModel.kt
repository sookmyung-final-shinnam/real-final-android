package com.veryshinnam.myapp.feature.collection.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.character.data.repository.CharacterRepository
import com.veryshinnam.myapp.common.enums.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _selectedFilter = MutableStateFlow(Gender.ALL)
    val selectedFilter = _selectedFilter.asStateFlow()

    private val _uiState = MutableStateFlow<CollectionUiState>(CollectionUiState.Loading)
    val uiState: StateFlow<CollectionUiState> = _uiState.asStateFlow()

    init {
        fetchCollection(Gender.ALL)
    }

    // 성별 필터 선택
    fun selectFilter(gender: Gender) {
        _selectedFilter.value = gender
        fetchCollection(gender) // 선택된 필터에 맞게 데이터 다시 불러오기
    }

    // 보관함 데이터 불러오기
    private fun fetchCollection(gender: Gender) {
        viewModelScope.launch {
            try {
                val queryGender = when (gender) {
                    Gender.ALL -> null
                    Gender.MALE -> "MALE"
                    Gender.FEMALE -> "FEMALE"
                }

                val characters = characterRepository.getCharacters(queryGender) // 캐릭터 전체 조회 api
                _uiState.value = CollectionUiState.Success(
                    collectionDataList = characters,
                    selectedFilter = gender
                )
            } catch (e: Exception) {
                _uiState.value = CollectionUiState.Error("보관함 불러오기 실패: ${e.message}")
            }
        }
    }

    // 캐릭터 즐겨찾기 업데이트
    fun updateFavorite(id: Long) {
        val currentState = _uiState.value
        if (currentState is CollectionUiState.Success) {
            val character = currentState.collectionDataList.find { it.id == id }
            if (character != null) {

                // ui 먼저 반영
                val updatedList = currentState.collectionDataList.map { item ->
                    if (item.id == id) item.copy(isFavorite = !item.isFavorite)
                    else item
                }
                val updatedState = currentState.copy(collectionDataList = updatedList)
                _uiState.value = updatedState

                // 서버 반영
                viewModelScope.launch {
                    try { // 관심 캐릭터 등록-해제 api
                        if (character.isFavorite) { characterRepository.addFavorite(id) }
                        else { characterRepository.removeFavorite(id) }
                    } catch (e: Exception) {
                        // 실패 시 복구
                        _uiState.value = currentState
                    }
                }
            }
        }
    }
}