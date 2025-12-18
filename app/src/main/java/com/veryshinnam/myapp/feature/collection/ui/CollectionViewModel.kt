package com.veryshinnam.myapp.feature.collection.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.character.data.repository.CharacterRepository
import com.veryshinnam.myapp.common.model.Gender
import com.veryshinnam.myapp.common.model.ImageType
import com.veryshinnam.myapp.common.model.ManualData
import com.veryshinnam.myapp.common.model.ManualTarget
import com.veryshinnam.myapp.core.manual.ManualManager
import com.veryshinnam.myapp.feature.collection.model.CollectionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val manualManager: ManualManager
) : ViewModel() {
    private val _uiState = MutableStateFlow<CollectionUiState>(CollectionUiState.Loading)
    val uiState: StateFlow<CollectionUiState> = _uiState.asStateFlow()

    private val _selectedFilter = MutableStateFlow(Gender.ALL)
    val selectedFilter = _selectedFilter.asStateFlow()

    private val _isEmpty = MutableStateFlow(false)
    val isEmpty: StateFlow<Boolean> = _isEmpty


    // ManualManager 구독
    val manualState = manualManager.state
    val manualMessage = manualManager.message

    // 매뉴얼 진행 단계 상태
    private val _manualStep = MutableStateFlow(0)
    val manualStep = _manualStep.asStateFlow()

    // 성별 필터 선택
    fun selectFilter(gender: Gender) {
        _selectedFilter.value = gender
        fetchCollection(gender) // 선택된 필터에 맞게 데이터 다시 불러오기
    }

    // 보관함 불러오기
    fun fetchCollection(gender: Gender) {
        viewModelScope.launch {
            try {
                val queryGender = when (gender) {
                    Gender.ALL -> null
                    Gender.MALE -> "MALE"
                    Gender.FEMALE -> "FEMALE"
                }
                val characters = characterRepository.getCharacters(queryGender) // api 호출

                _uiState.value = CollectionUiState.Success(
                    collectionDataList = characters,
                    selectedFilter = gender
                )

                // 보관함 여부 확인
                if (gender == Gender.ALL) {
                    _isEmpty.value = characters.isEmpty()
                }
            } catch (e: Exception) {
                _uiState.value = CollectionUiState.Error("보관함 불러오기 실패: ${e.message}")
            }
        }
    }

    // 캐릭터 즐겨찾기 업데이트
    fun updateFavorite(id: Long, onError: (String) -> Unit = {}) {
        val currentState = _uiState.value
        if (currentState is CollectionUiState.Success) {
            val character = currentState.collectionDataList.find { it.id == id }
            if (character != null) {
                val favoritesCount = currentState.collectionDataList.count { it.isFavorite }
                val newFavoriteState = !character.isFavorite

                if (newFavoriteState && favoritesCount >= 5) {
                    onError("좋아하는 캐릭터는 5명까지 등록할 수 있어요!")
                    return
                }

                // ui 먼저 반영
                val updatedList = currentState.collectionDataList.map { item ->
                    if (item.id == id) item.copy(isFavorite = newFavoriteState)
                    else item
                }
                val updatedState = currentState.copy(collectionDataList = updatedList)
                _uiState.value = updatedState

                // 서버 반영
                viewModelScope.launch {
                    try { // api 호출
                        if (newFavoriteState) { characterRepository.addFavorite(id) }
                        else { characterRepository.removeFavorite(id) }
                    } catch (e: Exception) {
                        // 실패 시 원복
                        _uiState.value = currentState
                    }
                }
            }
        }
    }

    // --- 매뉴얼 관련 ---
    // 생성 전 선택 화면 사용 매뉴얼
    val manuals = listOf(
        ManualData("동화가 완성되면 여기 보관함에서 확인할 수 있어요.", ManualTarget.NONE),
        ManualData("이건 지금까지 만든 동화 수이자 캐릭터 수에요!", ManualTarget.ITEM),
        ManualData("별 모양의 즐겨찾기를 누르면, 홈 화면에서 해당 캐릭터를 볼 수 있답니다.", ManualTarget.ICON),
        ManualData("앗! 같이 만들었던 동화가 여기 있네요. 장신남 캐릭터를 눌러 같이 한번 확인해 볼까요?", ManualTarget.IMAGE),
    )

    fun startManual() {
        _manualStep.value = 0
        manualManager.update(manuals[0].message)

        val dummyList = listOf(
            CollectionData(
                id = -1,
                name = "최강혁",
                image = ImageType.Resource(R.drawable.img_character_1),
                gender = Gender.FEMALE,
                isFavorite = true
            ),
            CollectionData(
                id = -1,
                name = "베네딕트",
                image = ImageType.Resource(R.drawable.img_character_2),
                gender = Gender.FEMALE,
                isFavorite = false
            ),
            CollectionData(
                id = -1,
                name = "준",
                image = ImageType.Resource(R.drawable.img_character_3),
                gender = Gender.FEMALE,
                isFavorite = false
            ),
            CollectionData(
                id = -1,
                name = "여름",
                image = ImageType.Resource(R.drawable.img_character_4),
                gender = Gender.FEMALE,
                isFavorite = true
            ),
            CollectionData(
                id = -1,
                name = "장신남",
                image = ImageType.Resource(R.drawable.img_character_5),
                gender = Gender.MALE,
                isFavorite = true
            )
        )

        // 스크린에서 매뉴얼 UI로 전환
        _isEmpty.value = false
        _uiState.value = CollectionUiState.Success(
            selectedFilter = Gender.ALL,
            collectionDataList = dummyList
        )
    }

    fun nextManual() {
        val current = _manualStep.value

        if (current < manuals.lastIndex) {
            val next = current + 1
            _manualStep.value = next
            manualManager.update(manuals[next].message)
        } else if (current == manuals.lastIndex) {
            _manualStep.value = manuals.size
        }
    }

    fun stopManual() = manualManager.stop()

    fun hideManual() = manualManager.clear()
}