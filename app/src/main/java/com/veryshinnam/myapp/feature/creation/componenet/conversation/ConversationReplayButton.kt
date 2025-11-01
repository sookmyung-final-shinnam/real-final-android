package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ConversationReplayButton(
    isTtsSpeaking: Boolean,               // 현재 TTS 재생 중 여부
    onReplayClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = colorResource(R.color.main_orange), fontWeight = SemiBold)
) {
    Button(
        onClick = { onReplayClick() },
        enabled = !isTtsSpeaking, // speaking 중이면 비활성화
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.lemon_yellow)
        ),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
        shape = CircleShape,
        border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(IntrinsicSize.Min)
        ) {
            Text(
                text = "다시 듣기",
                style = buttonTextStyle
            )

            Icon(
                imageVector = Icons.Default.Replay,
                contentDescription = "다시듣기 아이콘",
                tint = colorResource(id = R.color.main_orange),
                modifier = Modifier
                    .padding(start = 2.dp)
                    .fillMaxHeight()
            )
        }
    }
}