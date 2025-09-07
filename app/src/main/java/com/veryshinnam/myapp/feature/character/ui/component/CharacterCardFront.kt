package com.veryshinnam.myapp.feature.character.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeText
import com.veryshinnam.myapp.feature.character.model.CharacterData

@Composable
fun CharacterCardFront(
    character: CharacterData // 캐릭터 정보
) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 캐릭터 이름
            StrokeText(
                character.name,
                Color.White,
                colorResource(R.color.main_orange),
                8f,
                MaterialTheme.typography.headlineLarge,
                fWeight = FontWeight.Bold,
                modifier = Modifier
            )

            // 캐릭터 기본 정보
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,   // 가운데 정렬
                text = "\n${
                    // 여자 남자 구분
                    if (character.gender == "FEMALE") "여자" else "남자"
                } ${character.age}세\n${character.personality}\n${character.birth}",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

}