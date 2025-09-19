package com.veryshinnam.myapp.feature.collection.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.enums.Gender

@Composable
fun FilterButtons(
    selectedFilter: Gender,
    onFilterClick: (Gender) -> Unit,
    modifier: Modifier = Modifier // 부모 너비 가져옴
) {
    // 성별 필터
    val filters = listOf(Gender.ALL, Gender.FEMALE, Gender.MALE)

    Row (modifier = modifier) {
        filters.forEach { filter ->
            val isSelected = filter == selectedFilter

            // 버튼 및 텍스트 색상
            val backgroundColor = if (isSelected) colorResource(id = R.color.blue_sky) else Color.White
            val textColor       = if (isSelected) Color.White else Color.Black

            Button(
                onClick = { onFilterClick(filter) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = textColor
                ),
                shape = CircleShape,
                modifier = Modifier
                    .weight(1f) 
                    .padding(end = 16.dp) // 버튼 간 여백
                    .border(
                        width = 4.dp,
                        color = colorResource(id = R.color.blue_gray),
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = when (filter) {
                        Gender.ALL -> "전체"
                        Gender.FEMALE -> "여자"
                        Gender.MALE -> "남자"
                    },
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}