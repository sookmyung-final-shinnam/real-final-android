package com.veryshinnam.myapp.feature.character.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veryshinnam.myapp.R

@Composable
fun CharacterLockButton(
    title: String,
    isExist: Boolean
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        // 기본 책 이미지
        Image(
            painter = painterResource(id = R.drawable.img_character_book),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(16.dp))
                // 존재 여부에 따라 어두운 효과
                .then(if (!isExist) Modifier.alpha(0.5f) else Modifier),
            contentScale = ContentScale.Crop
        )

        if (isExist) {
            // 존재하면 제목 표시
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        } else {
            // 존재하지 않으면 자물쇠 표시
            Image(
                painter = painterResource(id = R.drawable.img_lock),
                contentDescription = "잠금",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}