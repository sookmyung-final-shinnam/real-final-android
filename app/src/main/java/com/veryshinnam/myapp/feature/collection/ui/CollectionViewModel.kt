package com.veryshinnam.myapp.feature.collection.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.collection.model.Filter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(

) : ViewModel() {

    private val _selectedFilter = MutableStateFlow(Filter.ALL)
    val selectedFilter = _selectedFilter.asStateFlow()

    private val _storageUiState = MutableStateFlow<CollectionUiState>(CollectionUiState.Loading)
    val storageUiState: StateFlow<CollectionUiState> = _storageUiState.asStateFlow()

    // 초기화
    init {
        loadStorageData(Filter.ALL)
    }

    // 필터 선택
    fun selectFilter(filter: Filter) {
        val currentState = _storageUiState.value
        if (currentState is CollectionUiState.Success) {
            _storageUiState.value = currentState.copy(selectedFilter = filter)
            loadStorageData(filter) // 선택된 필터에 맞게 데이터 다시 불러오기
        }
    }

    // 보관함 데이터 불러오기
    private fun loadStorageData(filter: Filter) {
        viewModelScope.launch {
            try {
                _storageUiState.value = CollectionUiState.Loading

                // 더미데이터 불러옴
                val data = generateDummyData(filter)

                _storageUiState.value = CollectionUiState.Success(
                    data = data,
                    selectedFilter = filter
                )
            } catch (e: Exception) {
                _storageUiState.value = CollectionUiState.Error("데이터를 불러오는데 실패했습니다.")
            }
        }
    }

    // 즐겨찾기 업데이트
    fun updateFavorite(cId: Long) {
        val currentState = _storageUiState.value
        if (currentState is CollectionUiState.Success) {
            val updatedList = currentState.data.map { item ->
                if (item.characterId == cId) item.copy(isFavorite = !item.isFavorite)
                else item
            }
            _storageUiState.value = currentState.copy(data = updatedList)
        }
    }

    private fun generateDummyData(filter: Filter): List<CollectionUiState.StorageData> {
        return when (filter) {
            Filter.FEMALE -> listOf(
                CollectionUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                CollectionUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
                CollectionUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                CollectionUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
                CollectionUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                CollectionUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
                )

            Filter.MALE -> listOf(
                CollectionUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                CollectionUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                CollectionUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                CollectionUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                CollectionUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                CollectionUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                CollectionUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                CollectionUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                CollectionUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                CollectionUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
            )

            Filter.ALL -> listOf(
                CollectionUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                CollectionUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                CollectionUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                CollectionUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
                CollectionUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                CollectionUiState.StorageData( 18, "민수", null, false),
                CollectionUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                CollectionUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                CollectionUiState.StorageData( 18, "민수", null, false),
                CollectionUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                CollectionUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                CollectionUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
                CollectionUiState.StorageData(13, "파파워", null, false),
                CollectionUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                CollectionUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                CollectionUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                CollectionUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                CollectionUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
            )
        }
    }
}