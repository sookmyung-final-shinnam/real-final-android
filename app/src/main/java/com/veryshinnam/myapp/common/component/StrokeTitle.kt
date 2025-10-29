package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StrokeTitle(
    modifier: Modifier = Modifier,
    titleText: String,
    titleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
    titleColor: Color,
    strokeColor: Color,
    strokeWidth: Float = 2f,
    verticalPadding: Dp = 4.dp,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding),
        contentAlignment = Alignment.Center
    ) {
        // 윤곽선
        Text(
            text = titleText,
            style = titleTextStyle.copy(
                fontWeight = FontWeight.Bold,
                color = strokeColor,
                drawStyle = Stroke(width = strokeWidth),
            )
        )

        // 실제 텍스트
        Text(
            text = titleText,
            style = titleTextStyle.copy(
                fontWeight = FontWeight.Bold,
                color = titleColor
            )
        )
    }
}