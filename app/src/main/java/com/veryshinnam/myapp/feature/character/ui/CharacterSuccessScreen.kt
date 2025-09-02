package com.veryshinnam.myapp.feature.character.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun CharacterSuccessScreen(
    data: CharacterUiState.CharacterData
) {
    // id가 바뀌면 초기값도 새로 반영되도록 key에 characterId 사용
    var isFav by rememberSaveable(data.characterId) { mutableStateOf(data.important) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val h = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 상단 특성 영역
            CharacterTrait(
                age = data.age.toString(),
                name = data.name,
                personality = data.personality
                //  (0.9 너비)
            )

            // 4) 연결 리소스 (0.9 너비)
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CharacterLockButton(
                    type = "동화책",
                    title = data.storyTitle ?: "책",
                    isExist = (data.storyId != null) && (data.storyTitle != null),
                    modifier = Modifier.weight(0.2f)
                )
                Spacer(Modifier.weight(0.4f))
                CharacterLockButton(
                    type = "동영상",
                    title = data.videoTitle ?: "영상",
                    isExist = (data.videoId != null) && (data.videoTitle != null),
                    modifier = Modifier.weight(0.2f)
                )
            }

            // 하단 정보
            CharacterInfo(
                name = data.name,
                gender = data.gender,
                isFavorite = isFav,
                createdAt = data.createdAt,
                onFavoriteClick = { isFav = !isFav }
                //  (0.9 너비)
            )
        }

        // 캐릭터 이미지
        CharacterImage(
            imageUrl = data.imageUrl,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -h * 0.16f)   // 전체 높이의 30% 위
        )
    }
}