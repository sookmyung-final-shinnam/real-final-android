package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun DashboardHelpText(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontWeight = Bold),
    verticalPadding: Dp = 16.dp,
    cardCorner: Dp = 16.dp,
    cardColor: Color = colorResource(R.color.light_green),
    borderWidth: Dp = 4.dp,
    borderColor: Color = colorResource(R.color.deep_green),
    modifier: Modifier
) {
    // 밑 바탕
    Box(
        modifier = modifier.fillMaxSize()
            .drawBehind {
                drawRoundRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            cardColor.copy(0.95f),
                            cardColor,
                            borderColor// 바깥
                        ),
                        center = center,
                        radius = size.minDimension
                    ),
                    cornerRadius = CornerRadius(cardCorner.toPx())
                )
            }
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(cardCorner)
            ),
        contentAlignment = Alignment.TopStart
    ) {

        Text(
            text = text.replace("", "\u200B"),
            style = textStyle,
            modifier = Modifier.padding(cardCorner, verticalPadding)
        )
    }
}