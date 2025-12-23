package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun DashboardHelpButton(
    buttonSize: Dp = 28.dp,
    buttonColor: Color = colorResource(R.color.light_green),
    borderWidth: Dp = 2.dp,
    borderColor: Color = Color.White,
    helpTextStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White, fontWeight = Bold)
) {
    Box(
        modifier = Modifier
            .size(buttonSize)
            .background(
                color = buttonColor,
                shape = CircleShape
            )
            .border(
                width = borderWidth,
                color = borderColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "?",
            style = helpTextStyle
        )
    }
}