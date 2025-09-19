package com.veryshinnam.myapp.feature.collection.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.character.data.repository.CharacterRepository
import com.veryshinnam.myapp.feature.collection.model.Filter
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

    private val _selectedFilter = MutableStateFlow(Filter.ALL)
    val selectedFilter = _selectedFilter.asStateFlow()

    private val _storageUiState = MutableStateFlow<CollectionUiState>(CollectionUiState.Loading)
    val storageUiState: StateFlow<CollectionUiState> = _storageUiState.asStateFlow()

    init {
        fetchCollection(Filter.ALL)
    }

    // 성별 필터 선택
    fun selectFilter(filter: Filter) {
        _selectedFilter.value = filter
        fetchCollection(filter) // 선택된 필터에 맞게 데이터 다시 불러오기
    }

    // 보관함 데이터 불러오기
    private fun fetchCollection(filter: Filter) {
        viewModelScope.launch {
//            try {
//                _storageUiState.value = CollectionUiState.Loading
//
//                // 더미데이터 불러옴
//                val data = generateDummyData(filter)
//
//                _storageUiState.value = CollectionUiState.Success(
//                    data = data,
//                    selectedFilter = filter
//                )
//            } catch (e: Exception) {
//                _storageUiState.value = CollectionUiState.Error("데이터를 불러오는데 실패했습니다.")
//            }
            try {
                val genderQuery = when (filter) {
                    Filter.ALL -> null
                    Filter.MALE -> "MALE"
                    Filter.FEMALE -> "FEMALE"
                }

                val data = characterRepository.getCharacters(genderQuery)

                _storageUiState.value = CollectionUiState.Success(
                    data = data,
                    selectedFilter = filter
                )
            } catch (e: Exception) {
                _storageUiState.value = CollectionUiState.Error("보관함 불러오기 실패: ${e.message}")
            }
        }
    }

    // 즐겨찾기 업데이트
    fun updateFavorite(cId: Long) {
        val currentState = _storageUiState.value
        if (currentState is CollectionUiState.Success) {
            val updatedList = currentState.data.map { item ->
                if (item.id == cId) item.copy(isFavorite = !item.isFavorite)
                else item
            }
            _storageUiState.value = currentState.copy(data = updatedList)
        }
    }
}