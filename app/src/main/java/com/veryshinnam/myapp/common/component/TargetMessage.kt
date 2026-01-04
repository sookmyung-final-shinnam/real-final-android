package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R

@Composable
fun TargetMessage(
    rect: Rect,
    message: String,
    messageStyle: TextStyle = MaterialTheme.typography.titleLarge,
    boxColor: Color = colorResource(R.color.main_orange),
    messageBorder: Dp = 4.dp,
    messageCorner: Dp = 16.dp,
    messagePadding: Dp = 20.dp,
) {
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .absoluteOffset(
                x = with(density) { rect.left.toDp() },
                y = with(density) { rect.top.toDp() }
            )
            .size(
                with(density) { rect.width.toDp() },
                with(density) { rect.height.toDp() }
            )
//            .width(with(density) { rect.width.toDp() })
//            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(messageCorner))
            .border(
                messageBorder,
                boxColor,
                RoundedCornerShape(messageCorner)
            )
            .padding(messagePadding)
            .zIndex(50f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message.replace("", "\u200B" ),
            style = messageStyle.copy(fontWeight = Bold, lineHeight = messageStyle.lineHeight * 1.2f),
            modifier = Modifier.fillMaxWidth()
        )
    }
}