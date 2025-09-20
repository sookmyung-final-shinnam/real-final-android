package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun SelectionPaletteButton(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Button(
            onClick = onClick,
            shape = CircleShape,
            border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
            colors = ButtonDefaults.buttonColors(containerColor = color),
            modifier = Modifier.matchParentSize()
        ) {}

        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = colorResource(R.color.main_orange),
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(0.6f) // 버튼의 60%
            )
        }
    }
}