package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.layout.PaddingValues
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
import com.veryshinnam.myapp.R

@Composable
fun CircleButton(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.main_orange),
            contentColor = Color.White
        ),
        shape = CircleShape,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}