package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veryshinnam.myapp.common.component.StrokeTitle

@Composable
fun DashBoardStaticsRow(
    color: Color,
    name: String,
    count: Int,
    circleSize: Dp = 18.dp,
    nameTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = Bold),
    subTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = Bold),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.semantics(true) {
            contentDescription = "$name ${count}회"
        }
    ) {
        // 색상 원
        Box(
            modifier = Modifier
                .size(circleSize)
                .background(color, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 이름
        Text(
            text = name,
            style = nameTextStyle,
            modifier = Modifier.weight(1f).clearAndSetSemantics { }
        )

        // 횟수
        StrokeTitle(
            titleText = "${count}회",
            titleColor = Color.White,
            strokeColor = color,
            titleTextStyle = subTextStyle.copy(letterSpacing = 1.5.sp),
            strokeWidth = 12f,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clearAndSetSemantics { }
        )
    }
}