package com.veryshinnam.myapp.feature.character.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.veryshinnam.myapp.feature.character.ui.component.CharacterImageCard

@Composable
fun CharacterSuccessScreen(
    data: CharacterUiState.CharacterData
) {

    // id가 바뀌면 초기값도 새로 반영되도록 key에 characterId 사용
    var isFav by rememberSaveable(data.characterId) { mutableStateOf(data.important) }

    CharacterImageCard(cImage = data.imageUrl,isFavorite = data.important,
        onFavoriteClick = { clickedId -> println("즐겨찾기 클릭! 캐릭터 ID: $clickedId")},
        modifier = Modifier.fillMaxWidth(0.5f) )
//    CharacterInfoCard()
//    BoxWithConstraints(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        val h = maxHeight
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            // 상단 특성 영역
//            CharacterTrait(
//                age = data.age.toString(),
//                name = data.name,
//                personality = data.personality
//                //  (0.9 너비)
//            )
//
//            // 4) 연결 리소스 (0.9 너비)
//            Row(
//                modifier = Modifier.fillMaxWidth(0.9f),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                CharacterLockButton(
//                    type = "동화책",
//                    title = data.storyTitle ?: "책",
//                    isExist = (data.storyId != null) && (data.storyTitle != null),
//                    modifier = Modifier.weight(0.2f)
//                )
//                Spacer(Modifier.weight(0.4f))
//                CharacterLockButton(
//                    type = "동영상",
//                    title = data.videoTitle ?: "영상",
//                    isExist = (data.videoId != null) && (data.videoTitle != null),
//                    modifier = Modifier.weight(0.2f)
//                )
//            }
//
//            // 하단 정보
//            CharacterInfo(
//                name = data.name,
//                gender = data.gender,
//                isFavorite = isFav,
//                createdAt = data.createdAt,
//                onFavoriteClick = { isFav = !isFav }
//                //  (0.9 너비)
//            )
//        }
//
//        // 캐릭터 이미지
//        CharacterImage(
//            imageUrl = data.imageUrl,
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .offset(y = -h * 0.16f)   // 전체 높이의 30% 위
//        )
//    }
}