package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DashboardInfoText(
    labelText: String,
    valueText: String,
    textColor: Color,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text( // 라벨
            text = labelText,
            style = textStyle
        )
        Text( // 값 (라벨 폰트 크기의 1.2배)
            text = valueText,
            style = textStyle.copy(
                color = textColor,
                fontSize = textStyle.fontSize * 1.2f,
            )
        )
    }
}