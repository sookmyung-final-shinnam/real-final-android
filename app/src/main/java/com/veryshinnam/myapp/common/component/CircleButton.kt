package com.veryshinnam.myapp.common.component

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
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold),
    textPadding: Dp = 8.dp,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.main_orange),
            contentColor = Color.White
        ),
        shape = CircleShape,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier
                .padding(vertical = textPadding)
        )
    }
}