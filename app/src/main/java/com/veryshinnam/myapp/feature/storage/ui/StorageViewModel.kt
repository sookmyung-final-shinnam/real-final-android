package com.veryshinnam.myapp.feature.storage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.storage.enums.CharacterFilter
import com.veryshinnam.myapp.feature.storage.enums.Filter
import com.veryshinnam.myapp.feature.storage.enums.StoryFilter
import com.veryshinnam.myapp.feature.storage.enums.Tab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(

) : ViewModel() {

    private val _storageUiState = MutableStateFlow<StorageUiState>(StorageUiState.Loading)
    val storageUiState: StateFlow<StorageUiState> = _storageUiState.asStateFlow()

    init {
        loadStorageData(Tab.CHARACTER)
    }

    private fun loadStorageData(type: Tab) {
        viewModelScope.launch {
            try {
                _storageUiState.value = StorageUiState.Loading

                val data = generateDummyData(type)

                _storageUiState.value = StorageUiState.Success(
                    data = data
                )
            } catch (e: Exception) {
                _storageUiState.value = StorageUiState.Error("데이터를 불러오는데 실패했습니다.")
            }
        }
    }

    // 탭 바꾸기
    fun changeTab(tab: Tab) {
        val currentState = _storageUiState.value
        if (currentState is StorageUiState.Success) {

            // 데이터 갱신
            val newData = generateDummyData(tab)

            _storageUiState.value = currentState.copy(
                data = newData,
                selectedTab = tab,
                selectedFilter = Filter.ALL // 탭 바뀌면 필터는 항상 전체로 초기화
            )
        }
    }


    fun changeFilter(filter: Filter) {
        val currentState = _storageUiState.value
        if (currentState is StorageUiState.Success) {
            _storageUiState.value = currentState.copy(
                selectedFilter = filter
            )
        }
    }

    //    fun onFavoriteClick(itemId: String) {
//        val currentState = _storageUiState.value
//        if (currentState is StorageUiState.Success) {
//            viewModelScope.launch {
//                // 좋아요 처리 중 표시
//                _storageUiState.value = currentState.copy(
//                    favoriteInFlight = currentState.favoriteInFlight + itemId
//                )
//
//                try {
//                    // 실제 좋아요 API 호출
//                    // favoriteRepository.toggleFavorite(itemId)
//
//                    // 성공 후 데이터 업데이트
//                    val updatedCharacterData = currentState.characterData.map { item ->
//                        if (item.id == itemId) item.copy(isFavorite = !item.isFavorite)
//                        else item
//                    }
//                    val updatedStoryData = currentState.storyData.map { item ->
//                        if (item.id == itemId) item.copy(isFavorite = !item.isFavorite)
//                        else item
//                    }
//
//                    _storageUiState.value = currentState.copy(
//                        characterData = updatedCharacterData,
//                        storyData = updatedStoryData,
//                        favoriteInFlight = currentState.favoriteInFlight - itemId
//                    )
//                } catch (e: Exception) {
//                    // 실패 시 원상복구
//                    _storageUiState.value = currentState.copy(
//                        favoriteInFlight = currentState.favoriteInFlight - itemId
//                    )
//                }
//            }
//        }
//    }
    private fun generateDummyData(type: Tab): List<StorageUiState.StorageData> {
        return if(type == Tab.CHARACTER){
            listOf(
                StorageUiState.StorageData(
                    1,
                    "용감한 기사",
                    "https://example.com/char1.jpg",
                    false,
                    true
                ),
                StorageUiState.StorageData(1, "공주님", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", true, true),
                StorageUiState.StorageData(1, "마법사", "https://ifh.cc/g/XTGSPy.png", false, true),
                StorageUiState.StorageData(1, "요정", "https://ifh.cc/g/XTGSPy.png", true, true),
                StorageUiState.StorageData(1, "드래곤", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", false, true),
                StorageUiState.StorageData(1, "할머니", "https://ifh.cc/g/QP5O4d.png", false, false),
                StorageUiState.StorageData(1, "해적", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", false, true),
                StorageUiState.StorageData(1, "신데렐라", "https://ifh.cc/g/XTGSPy.png", true, true)
            )
        } else {
            return listOf(
                StorageUiState.StorageData(1, "신데렐라와 백성들", "https://ifh.cc/g/QP5O4d.png", true, true),
                StorageUiState.StorageData(1, "백설공주와 백성들", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", false, true),
                StorageUiState.StorageData(1, "잠자는 숲속의 공주", "https://ifh.cc/g/XTGSPy.png", false, false),
                StorageUiState.StorageData(1, "라푼젤와 백성들", "https://ifh.cc/g/XTGSPy.png", false, true),
                StorageUiState.StorageData(1, "인어공주와 백성들", "https://ifh.cc/g/XTGSPy.png", true, true),
                StorageUiState.StorageData(1, "미녀와 야수", "https://ifh.cc/g/QP5O4d.png", false, true),
                StorageUiState.StorageData(1, "알라딘와 백성들", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", false, false),
                StorageUiState.StorageData(1, "신나는 피노키오", "https://i.ibb.co/PGs7r1M6/Kakao-Talk-20250707-183009989.jpg", false, true),
                StorageUiState.StorageData(1, "신나는 개구리 왕자", "https://ifh.cc/g/XTGSPy.png", true, true)
            )
        }
    }
}