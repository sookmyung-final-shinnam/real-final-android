package com.veryshinnam.myapp.feature.character.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.feature.character.model.CharacterData
import com.veryshinnam.myapp.feature.character.model.StoryVideoData
import com.veryshinnam.myapp.feature.character.ui.component.CharacterImageCard
import com.veryshinnam.myapp.feature.character.ui.component.CharacterInfoCard

@Composable
fun CharacterCardScreen(
    cData: CharacterData,
    sData: StoryVideoData,
    onFavoriteClick: (Long) -> Unit,
    onStoryClick: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End // Row 안 가로 배치 방향
    ) {
        // 왼쪽 캐릭터 이미지 카드
        CharacterImageCard(
            character = cData,
            onFavoriteClick = { onFavoriteClick(cData.id) },
            modifier = Modifier
                .aspectRatio(0.78f) // 카드 비율
                .offset(x = (40).dp)       // 오른쪽 이동
        )

        // 오른쪽 캐릭터 정보 카드
        CharacterInfoCard(
            character = cData,
            story = sData,
            onStoryClick = onStoryClick,
            modifier = Modifier
                .aspectRatio(2f) // 카드 비율
                .zIndex(1f)
        )
    }
}
