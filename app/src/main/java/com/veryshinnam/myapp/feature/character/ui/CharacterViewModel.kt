package com.veryshinnam.myapp.feature.character.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.model.Gender
import com.veryshinnam.myapp.common.model.ImageType
import com.veryshinnam.myapp.common.model.ManualData
import com.veryshinnam.myapp.common.model.ManualTarget
import com.veryshinnam.myapp.core.manual.ManualManager
import com.veryshinnam.myapp.feature.character.data.repository.CharacterRepository
import com.veryshinnam.myapp.feature.character.model.CharacterData
import com.veryshinnam.myapp.feature.character.model.StoriesData
import com.veryshinnam.myapp.feature.character.model.VideoStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Long

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
    private val manualManager: ManualManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val uiState = _uiState.asStateFlow()

    // ManualManager 구독
    val manualState = manualManager.state
    val manualMessage = manualManager.message

    // 매뉴얼 진행 단계 상태
    private val _manualStep = MutableStateFlow(0)
    val manualStep = _manualStep.asStateFlow()

    // 캐릭터 상세 불러오기
    fun fetchCharacter(id: Long) {
        viewModelScope.launch {
            _uiState.value = CharacterUiState.Loading
            try {
                val characterDetail = repository.getCharacterDetail(id) // api 호출
                _uiState.value = CharacterUiState.Success(
                    characterData = characterDetail
                )
            } catch (e: Exception) {
                _uiState.value =
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
        val currentState = _uiState.value
        if (currentState is CharacterUiState.Success) {
            val character = currentState.characterData
            if (character.id == id) {

                // ui 먼저 반영
                val updatedCharacter = character.copy(isFavorite = !character.isFavorite)
                _uiState.value = currentState.copy(characterData = updatedCharacter)

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
                        _uiState.value = currentState
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
                _uiState.value = CharacterUiState.Error(
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
                val currentState = _uiState.value
                if (currentState is CharacterUiState.Success && currentState.characterData.id == id) {
                    // CharacterData 안의 stories 필드만 갱신
                    val updatedCharacter = currentState.characterData.copy(
                        stories = newStory
                    )
                    _uiState.value = currentState.copy(characterData = updatedCharacter)
                }
            } catch (e: Exception) { }
        }
    }

    // --- 매뉴얼 관련 ---
    // 생성 전 선택 화면 사용 매뉴얼
    val manuals = listOf(
        ManualData("만들어진 캐릭터의 사진과 정보를 자세히 볼 수 있어요.", ManualTarget.NONE),
        ManualData("저희가 만든 장신남은 용감하고 호기심이 많은 성격을 가진 친구네요!", ManualTarget.NONE),
        ManualData("저기 Tab 버튼을 한번 눌러 보실래요?", ManualTarget.BUTTON),
        ManualData("바로 카드 뒷 장에서 동화를 확인할 수 있고", ManualTarget.NONE),
        ManualData("도토리 1개를 사용하여 잠금을 해제하면", ManualTarget.NONE),
        ManualData("동화를 움직이는 형태로도 볼 수 있답니다.", ManualTarget.NONE),
        ManualData("만든 동화를 친구들에게도 공유할 수 있다는 것도 잊지 마세요!", ManualTarget.IMAGE),
    )

    fun startManual() {
        _manualStep.value = 0
        manualManager.update(manuals[0].message)

        val dummy = CharacterData(
            id = -1,
            name = "장신남",
            gender = Gender.FEMALE,
            age = 11,
            image = ImageType.Resource(R.drawable.img_character_5),
            personality = "용감하고 호기심이 많은 성격으로, 친구를 돕는 데 힘을 쏟습니다.",
            birth = "2025-11-13",
            isFavorite = false,
            stories = StoriesData(
                storyId = -1,
                title = "장신남과 노란 새의 모험",
                imageUrl = ImageType.Resource(R.drawable.img_dummy_page),
                videoStatus = VideoStatus.NONE,
                videoUrl = null ,
                imageYLink = "https://www.youtube.com/shorts/w5M0pxr-u-k",
                videoYLink= null
            )
        )

        // 스크린에서 매뉴얼 UI로 전환
        _uiState.value = CharacterUiState.Success(
            characterData = dummy
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