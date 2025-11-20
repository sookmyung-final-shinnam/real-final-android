package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R

@Composable
fun TargetConvRecord(
    rect: Rect,
    density: Density,
    onClick: () -> Unit,
    borderColor: Color = colorResource(R.color.main_orange),
    containerColor: Color = colorResource(R.color.lemon_yellow)
) {
    Box( // Button > Box 변경
        modifier = Modifier
            .absoluteOffset(
                x = with(density) { rect.left.toDp() },
                y = with(density) { rect.top.toDp() }
            )
            .size(
                with(density) { rect.width.toDp() },
                with(density) { rect.height.toDp() }
            )
            .zIndex(20f)
            .clip(CircleShape)
            .background(containerColor)
            .border(2.dp, borderColor, CircleShape)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.img_mike_on),
            contentDescription = "마이크 버튼",
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 8.dp), // Button 기본 패딩
            contentScale = ContentScale.Fit,
        )
    }
}