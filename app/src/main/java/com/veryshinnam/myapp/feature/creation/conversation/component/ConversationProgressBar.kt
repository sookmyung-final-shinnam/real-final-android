package com.veryshinnam.myapp.feature.creation.conversation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ConversationProgressBar(
    progress: Int,
    total: Int,
    modifier: Modifier
) {
    LinearProgressIndicator(
        progress = progress / total.toFloat(),
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .padding(horizontal = 16.dp),
        color = colorResource(id = R.color.main_orange),
        trackColor = Color.LightGray.copy(alpha = 0.5f)
    )
}