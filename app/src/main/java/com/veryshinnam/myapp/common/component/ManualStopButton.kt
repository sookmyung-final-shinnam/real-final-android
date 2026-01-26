package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ManualStopButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val text = "그만 들을래요."

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            contentColor = Color.White,
            disabledContentColor = Color.White.copy(alpha = 0.6f)
        ),
        modifier = modifier
            .statusBarsPadding()
            .padding(top = 8.dp, end = 16.dp)
            .shadow(
                elevation = 8.dp,
                shape = CircleShape,
                ambientColor = Color.Black.copy(alpha = 0.3f),
                spotColor = Color.Black.copy(alpha = 0.45f)
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = if (enabled) {
                        listOf(
                            colorResource(R.color.main_orange),
                            colorResource(R.color.dark_orange)
                        )
                    } else {
                        listOf(
                            colorResource(R.color.main_orange).copy(alpha = 0.3f),
                            colorResource(R.color.dark_orange).copy(alpha = 0.3f)
                        )
                    }
                ),
                shape = CircleShape
            )
            .clip(CircleShape)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}