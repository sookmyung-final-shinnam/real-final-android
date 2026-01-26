package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun CircleButton(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    topColor: Color = colorResource(R.color.main_orange),
    bottomColor: Color = colorResource(R.color.dark_orange),
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        shape = CircleShape,
        contentPadding = contentPadding,
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(topColor, bottomColor)),
                shape = CircleShape
            )
            .border(
                width = 2.dp,
                color = topColor,
                shape = CircleShape
            )
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}