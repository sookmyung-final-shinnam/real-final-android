package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ConversationNextButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier
) {
    Row (
        modifier = if (enabled) {
            modifier.clickable { onClick() }
        } else { modifier },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("다음",
            fontWeight = FontWeight.Medium,
            color = if (enabled) colorResource(R.color.main_orange)
            else colorResource(R.color.main_orange).copy(alpha = 0.3f),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold)
        )

        Spacer(Modifier.width(4.dp))
        Icon(Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "다음",
            tint = if (enabled) colorResource(R.color.main_orange)
            else colorResource(R.color.main_orange).copy(alpha = 0.3f)
        )
    }
}