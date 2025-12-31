package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DashboardCardTitle(
    title: String,
    borderColor: Color,
    cardCorner: Dp,
    spacer: Float,
    verticalPadding: Dp = 16.dp,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = Bold),
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(borderColor, shape = RoundedCornerShape(cardCorner)),
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(Modifier.fillMaxWidth(spacer - 0.03f))
        Text(
            title, style = titleTextStyle,
            modifier = Modifier.padding(vertical = verticalPadding)
        )
    }
}