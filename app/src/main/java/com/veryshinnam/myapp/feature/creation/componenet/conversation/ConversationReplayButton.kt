package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import kotlin.contracts.contract

@Composable
fun ConversationReplayButton(
    isTtsSpeaking: Boolean,               // 현재 TTS 재생 중 여부
    onReplayClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = SemiBold)
) {
    val isEnable = !isTtsSpeaking // TTS 재생 중이지 않을 때 활성화

    Button(
        onClick = { onReplayClick() },
        enabled = isEnable,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = colorResource(R.color.main_orange),
            disabledContainerColor = Color.Transparent,
            disabledContentColor = colorResource(R.color.main_orange).copy(0.5f)
        ),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
        shape = CircleShape,
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isEnable) {
                        listOf(
                            colorResource(id = R.color.lemon_yellow),
                            colorResource(id = R.color.lemon_yellow),
                            colorResource(R.color.background_yellow)
                        )
                    } else {
                        listOf(
                            colorResource(id = R.color.lemon_yellow).copy(alpha = 0.5f),
                            colorResource(id = R.color.lemon_yellow).copy(alpha = 0.5f),
                        )
                    }
                ),
                shape = CircleShape
            )
            .border(
                2.dp,
                if (isEnable) colorResource(R.color.main_orange) else colorResource(R.color.main_orange).copy(0.5f),
                shape = CircleShape
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .semantics(true) {
                    contentDescription = "다시 듣기"
                    role = Role.Button
                }
        ) {
            Text(
                text = "다시 듣기",
                style = buttonTextStyle,
                modifier = Modifier.clearAndSetSemantics { }
            )

            Icon(
                imageVector = Icons.Rounded.Replay,
                contentDescription = null,
                tint = if (isEnable) colorResource(id = R.color.main_orange) else colorResource(id = R.color.main_orange).copy(0.5f),
                modifier = Modifier
                    .padding(start = 2.dp)
                    .fillMaxHeight()
            )
        }
    }
}