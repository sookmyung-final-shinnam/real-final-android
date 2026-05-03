package com.veryshinnam.myapp.feature.character.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.model.Gender
import com.veryshinnam.myapp.common.model.ImageType
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
    val manualStep = manualManager.step

    // 매뉴얼 진행 단계 상태
    private val _manualIndex = MutableStateFlow(0)
    val manualIndex = _manualIndex.asStateFlow()

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
    fun updateFavorite(id: Long, onError: (String) -> Unit = {}) {
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
                    } catch (_: Exception) {
                        // 실패 시 상태 복구
                        _uiState.value = currentState
                        // e 종류에 따라 문구
                        onError("좋아하는 캐릭터는 5명까지만 등록할 수 있어요!")
                    }
                }
            }
        }
    }

    // 동화 영상 해제
    fun fetchVideoStory(cId:Long, sId: Long, onError: (String) -> Unit = {}) {
        viewModelScope.launch {
            try {
                repository.generateVideo(sId)
                refreshStories(cId)
            } catch (_: Exception) {
                // 실패 시 상태 복구
                onError("도토리가 부족해요.\n출석을 통해 도토리를 모아 볼까요?")
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
            } catch (_: Exception) { }
        }
    }

    // --- 매뉴얼 관련 ---
    // 생성 전 선택 화면 사용 매뉴얼
    val manuals = listOf(
        "화면을 가로로 돌려\n준비해 주세요!",
        "캐릭터룸에서는 완성된 캐릭터의 사진과 정보를 자세히 볼 수 있어요.",
        "저희가 만든 짱신남은 용감하고 호기심이 많은 성격을 가진 친구네요!",
        "저기 Tab 버튼을 한번 눌러 보실래요?",
        "설명 카드가 뒤집히면서 만든 동화를 확인할 수 있고",
        "도토리 1개를 사용하여 잠금을 해제하면",
        "동화를 움직이는 형태로도 볼 수 있답니다.",
        "카톡 버튼을 눌러 만든 동화를 친구들에게도 공유할 수 있답니다!",
    )

    val manualDummy = CharacterData(
        id = -1,
        name = "짱신남",
        gender = Gender.FEMALE,
        age = 11,
        image = ImageType.Resource(R.drawable.img_character_5),
        personality = "용감하고 호기심이 많은 성격으로, 친구를 돕는 데 힘을 쏟습니다.",
        birth = "2025-11-13",
        isFavorite = true,
        stories = StoriesData(
            storyId = -1,
            title = "짱신남과 요정 스토릭터의 모험",
            imageUrl = ImageType.Resource(R.drawable.img_dummy_page),
            videoStatus = VideoStatus.NONE,
            videoUrl = null ,
            imageYLink = "",
            videoYLink= null
        )
    )

    fun startManual() {
        _manualIndex.value = 0
        manualManager.update(manuals[0])

        // 스크린에서 매뉴얼 UI로 전환
        _uiState.value = CharacterUiState.Success(
            characterData = manualDummy
        )
    }

    fun nextManualIndex() {
        val current = _manualIndex.value

        if (current < manuals.lastIndex) {
            val next = current + 1
            _manualIndex.value = next
            manualManager.update(manuals[next])
            nextManualStep()
        } else if (current == manuals.lastIndex) {
            _manualIndex.value = manuals.size
        }
    }

    fun stopManual() = manualManager.stop()

    fun clearManual() = manualManager.clear()

    fun nextManualStep() = manualManager.nextStep()
}