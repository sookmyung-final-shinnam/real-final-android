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
fun DashboardInfoRow(
    label: String,
    value: String,
    textColor: Color,
    labelTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    valueTextStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text( // 라벨
            text = label,
            style =labelTextStyle.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Text( // 값
            text = value,
            style = valueTextStyle.copy(
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        )
    }
}