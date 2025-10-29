package com.veryshinnam.myapp.feature.collection.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.enums.Gender

@Composable
fun CollectionFilterButtons(
    modifier: Modifier = Modifier, // 부모 너비 가져옴
    selectedFilter: Gender,
    onFilterClick: (Gender) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    // 성별 필터
    val filters = listOf(Gender.ALL, Gender.FEMALE, Gender.MALE)

    Row (modifier = modifier) {
        filters.forEach { filter ->
            val isSelected = filter == selectedFilter

            // 버튼 및 텍스트 색상
            val backgroundColor =
                if (isSelected) colorResource(id = R.color.blue_sky) else Color.White
            val textColor = if (isSelected) Color.White else Color.Black

            Button(
                onClick = { onFilterClick(filter) },
                modifier = Modifier
                    .padding(end = 4.dp)
//                    .defaultMinSize(minHeight = 0.dp)
                    .heightIn(min = 0.dp),
                shape = CircleShape,
//                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = textColor
                ),
                border = BorderStroke(4.dp, colorResource(id = R.color.blue_gray))
            ) {
//                Button(
//                    modifier = Modifier
//                        .padding(end = 4.dp) // 버튼 간 여백
//                        .border(
//                            width = 4.dp,
//                            color = colorResource(id = R.color.blue_gray),
//                            shape = RoundedCornerShape(50)
//                        ),
//                    onClick = { onFilterClick(filter) },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = backgroundColor,
//                        contentColor = textColor
//                    ),
//                    shape = CircleShape,
//                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
//                ) {
                Text(
                    text = when (filter) {
                        Gender.ALL -> "전체"
                        Gender.FEMALE -> "여자"
                        Gender.MALE -> "남자"
                    },
                    style = textStyle.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}