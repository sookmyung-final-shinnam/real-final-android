package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ConversationRecordButton(
    onRecordClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier
) {
    Button(
        onClick = { onRecordClick() },
        enabled = enabled,
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .background(
                brush = Brush.verticalGradient(
                    colors = if (enabled) {
                         listOf(
                            colorResource(id = R.color.lemon_yellow),
                            colorResource(id = R.color.lemon_yellow),
                            Color.LightGray.copy(alpha = 0.3f)
                        )
                    } else {
                        listOf(
                            colorResource(id = R.color.lemon_yellow).copy(alpha = 0.5f),
                            colorResource(id = R.color.lemon_yellow).copy(alpha = 0.5f),
                        )
                    }
                ),
                shape = CircleShape
            ),
        border = BorderStroke(2.dp,  if (enabled) colorResource(R.color.main_orange) else colorResource(R.color.main_orange).copy(0.5f)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        ),
        shape = CircleShape
    ) {
        Image(
            painter = painterResource(R.drawable.ic_mike),
            contentDescription = null, // 장식용
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Fit,
            alpha = if (enabled) 1f else 0.3f
        )
    }
}