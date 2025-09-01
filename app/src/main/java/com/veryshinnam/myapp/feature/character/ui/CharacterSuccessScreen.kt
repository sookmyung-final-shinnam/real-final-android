package com.veryshinnam.myapp.feature.character.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CharacterTrait(
            age = data.age.toString(),
            name = data.name,
            personality = data.personality
        )

        // 1) 캐릭터 썸네일
        CharacterImage(data.imageUrl)



        // 3) 기본 정보
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(data.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                if (data.important) {
                    Text("중요 캐릭터", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                }
            }
            LabeledText("성별", data.gender)
            LabeledText("나이", "${data.age}세")
            LabeledText("성격", data.personality)
            LabeledText("생성일", data.createdAt) // 더미는 String이라 그대로 노출
        }

        Divider()

        // 4) 연결 리소스 (책/영상) - 존재 여부에 따라 잠금
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CharacterLockButton(
                title = data.storyTitle ?: "책",
                isExist = data.storyTitle != null
            )
            Spacer(Modifier.weight(1f))
            CharacterLockButton(
                title = data.videoTitle ?: "영상",
                isExist = data.videoTitle != null
            )
        }
    }
}

@Composable
private fun LabeledText(label: String, value: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}