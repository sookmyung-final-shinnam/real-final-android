package com.veryshinnam.myapp.feature.character.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeTitle
import com.veryshinnam.myapp.common.model.Gender
import com.veryshinnam.myapp.feature.character.model.CharacterData

@Composable
fun CharacterRightFront(
    modifier: Modifier,
    character: CharacterData, // 캐릭터 정보
    nameTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
    mainTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
    subTextStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- 캐릭터 이름
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StrokeTitle(
                    titleText = character.name,
                    titleColor = Color.White,
                    strokeColor = colorResource(R.color.main_orange),
                    titleTextStyle = nameTextStyle,
                    strokeWidth = 8f,
                    modifier = Modifier
                )
                Text(
                    text = "${ if (character.gender == Gender.FEMALE) "여자" else "남자" } ${character.age}세",
                    style = subTextStyle.copy(color = colorResource(R.color.main_orange))
                )
            }

            // --- 캐릭터 기본 정보
            // 성별 & 나이
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 성격
                Text(
                    text = character.personality,
                    style = mainTextStyle.copy(
                        lineHeight = (mainTextStyle.fontSize.value * 1.1).sp
                    )
                )

                // 생성일
                Text(
                    text = character.birth,
                    style = subTextStyle

                )
            }
        }

}