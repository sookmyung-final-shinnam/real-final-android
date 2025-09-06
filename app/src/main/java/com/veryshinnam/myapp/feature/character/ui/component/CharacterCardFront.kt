package com.veryshinnam.myapp.feature.character.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeText

@Composable
fun CharacterCardFront(
    cName: String,   // 캐릭터 이름
    cGender: String, // 캐릭터 성별
    cAge: Int,       // 캐릭터 나이
    cPersonality: String,   // 캐릭터 성격
    cBirth: String?,        // 캐릭터 생일
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 캐릭터 이름
        StrokeText(
            cName,
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
                if (cGender == "FEMALE") "여자" else "남자"
            } ${cAge}세\n${cPersonality}\n${cBirth}",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}