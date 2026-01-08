package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp

@Composable
fun ManualStopButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val text = "그만 들을래요."

    CircleButton(
        modifier = modifier.statusBarsPadding().padding(top = 8.dp, end = 16.dp),
        onClick = onClick,
        enabled = enabled,
        text = text,
        textStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = Bold),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp)
    )
}