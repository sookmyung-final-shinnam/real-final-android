package com.veryshinnam.myapp.feature.storage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.storage.enums.Filter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(

) : ViewModel() {

    private val _selectedFilter = MutableStateFlow(Filter.ALL)
    val selectedFilter = _selectedFilter.asStateFlow()

    private val _storageUiState = MutableStateFlow<StorageUiState>(StorageUiState.Loading)
    val storageUiState: StateFlow<StorageUiState> = _storageUiState.asStateFlow()

    // 초기화
    init {
        loadStorageData(Filter.ALL)
    }

    // 필터 선택
    fun selectFilter(filter: Filter) {
        val currentState = _storageUiState.value
        if (currentState is StorageUiState.Success) {
            _storageUiState.value = currentState.copy(selectedFilter = filter)
            loadStorageData(filter) // 선택된 필터에 맞게 데이터 다시 불러오기
        }
    }

    // 보관함 데이터 불러오기
    private fun loadStorageData(filter: Filter) {
        viewModelScope.launch {
            try {
                _storageUiState.value = StorageUiState.Loading

                // 더미데이터 불러옴
                val data = generateDummyData(filter)

                _storageUiState.value = StorageUiState.Success(
                    data = data,
                    selectedFilter = filter
                )
            } catch (e: Exception) {
                _storageUiState.value = StorageUiState.Error("데이터를 불러오는데 실패했습니다.")
            }
        }
    }

    // 즐겨찾기 업데이트
    fun updateFavorite(cId: Long) {
        val currentState = _storageUiState.value
        if (currentState is StorageUiState.Success) {
            val updatedList = currentState.data.map { item ->
                if (item.characterId == cId) item.copy(isFavorite = !item.isFavorite)
                else item
            }
            _storageUiState.value = currentState.copy(data = updatedList)
        }
    }

    private fun generateDummyData(filter: Filter): List<StorageUiState.StorageData> {
        return when (filter) {
            Filter.FEMALE -> listOf(
                StorageUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                StorageUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
                StorageUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                StorageUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
                StorageUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                StorageUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
                )

            Filter.MALE -> listOf(
                StorageUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                StorageUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                StorageUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                StorageUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                StorageUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                StorageUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                StorageUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                StorageUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                StorageUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                StorageUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
            )

            Filter.ALL -> listOf(
                StorageUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                StorageUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                StorageUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                StorageUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
                StorageUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                StorageUiState.StorageData( 18, "민수", null, false),
                StorageUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                StorageUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                StorageUiState.StorageData( 18, "민수", null, false),
                StorageUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                StorageUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                StorageUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
                StorageUiState.StorageData(13, "파파워", null, false),
                StorageUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                StorageUiState.StorageData(13, "파파워", "https://ifh.cc/g/XTGSPy.png", false),
                StorageUiState.StorageData( 18, "민수", "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png", false),
                StorageUiState.StorageData(11, "유리", "https://ifh.cc/g/QP5O4d.png", true),
                StorageUiState.StorageData(12, "파워", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true),
            )
        }
    }
}