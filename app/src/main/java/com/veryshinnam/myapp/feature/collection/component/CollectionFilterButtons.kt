package com.veryshinnam.myapp.feature.collection.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.model.Gender

@Composable
fun CollectionFilterButtons(
    modifier: Modifier = Modifier, // 부모 너비 가져옴
    selectedFilter: Gender,
    onFilterClick: (Gender) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    // 성별 필터
    val filters = listOf(Gender.ALL, Gender.FEMALE, Gender.MALE)

    Row(
        modifier = modifier
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        filters.forEach { filter ->
            val isSelected = filter == selectedFilter

            // 버튼 및 텍스트 색상
            val backgroundColor =
                if (isSelected) colorResource(id = R.color.blue_sky) else Color.White
            val textColor = if (isSelected) Color.White else Color.Black

            Box(
                modifier = Modifier
                    .background(backgroundColor, CircleShape)
                    .border(
                        width = 4.dp,
                        color = colorResource(id = R.color.blue_gray),
                        shape = CircleShape
                    )
                    .clickable { onFilterClick(filter) }
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .semantics(true) {
                        contentDescription = "${filter.type} 성별"
                        role = Role.RadioButton
                        selected = isSelected
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = filter.type,
                    style = textStyle.copy(
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    ),
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        }
    }
}