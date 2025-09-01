package com.veryshinnam.myapp.feature.character.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veryshinnam.myapp.R

@Composable
fun CharacterTrait(
    age: String,
    name: String,
    personality: String,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // 상단 액자 이미지
        Image(
            painter = painterResource(R.drawable.img_character_trait),
            contentDescription = "상단 액자",
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(16f / 9f),
            contentScale = ContentScale.FillBounds

        )

        // 텍스트
        Text(
            text = "안녕, 내 이름은 $name. \n나이는 ${age}살이고, $personality 성격을 가졌어!",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 16.dp),

        )
    }
}