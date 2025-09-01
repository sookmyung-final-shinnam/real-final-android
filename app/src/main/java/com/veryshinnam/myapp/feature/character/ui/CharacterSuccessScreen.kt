package com.veryshinnam.myapp.feature.character.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R

@Composable
fun CharacterSuccessScreen(
    data: CharacterUiState.CharacterData
) {
    Box(
        modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CharacterTrait(
                age = data.age.toString(),
                name = data.name,
                personality = data.personality
            )

            // 4) 연결 리소스
            Row(
                modifier = Modifier
                    .fillMaxWidth(.9f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 책
                CharacterLockButton(
                    type = "동화책",
                    title = data.storyTitle ?: "책",
                    isExist = (data.storyId != null) && (data.storyTitle != null),
                    modifier = Modifier.weight(0.2f)
                )
                Spacer(Modifier.weight(0.4f))

                // 영상
                CharacterLockButton(
                    type = "동영상",
                    title = data.videoTitle ?: "영상",
                    isExist = (data.videoId != null) && (data.videoTitle != null),
                    modifier = Modifier.weight(0.2f)
                )
            }

            // 이름
            Text("${data.name}")
            // 생성일
            Text("${data.createdAt}")
        }

        // 캐릭터를 가장 위
        CharacterImage(data.imageUrl)
    }
}