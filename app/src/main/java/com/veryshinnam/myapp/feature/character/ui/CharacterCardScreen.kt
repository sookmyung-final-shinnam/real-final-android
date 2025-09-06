package com.veryshinnam.myapp.feature.character.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.feature.character.ui.component.CharacterImageCard
import com.veryshinnam.myapp.feature.character.ui.component.CharacterInfoCard

@Composable
fun CharacterCardScreen(
    data: CharacterUiState.CharacterData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End // Row 안 가로 배치 방향
    ) {
        // 이미지 카드
        CharacterImageCard(
            cImage = data.imageUrl,
            isFavorite = data.important,
            onFavoriteClick = { println("즐겨찾기 클릭! ${data.characterId}") },
            modifier = Modifier
                .aspectRatio(0.78f) // 카드 비율
                .offset(x = (40).dp)       // 오른쪽 이동
        )

        // 정보 카드 (겹치게)
        CharacterInfoCard(
            cName = data.name,
            cGender = data.gender,
            cAge = data.age,
            cPersonality = data.personality,
            cBirth = data.createdAt,
            modifier = Modifier
                .aspectRatio(1.9f) // 카드 비율
                .zIndex(1f)
        )
    }
}
