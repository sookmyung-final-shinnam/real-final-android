package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TargetItem(
    rect: Rect,
    density: Density,
    image: Painter,
    imageDescription: String,
    value: Int,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    boxColor: Color,
    verticalPadding: Dp = 4.dp
) {
    Box(
        // 테두리
        modifier = Modifier
            .absoluteOffset(
                x = with(density) { rect.left.toDp() },
                y = with(density) { rect.top.toDp() }
            )
            .size(
                with(density) { rect.width.toDp() },
                with(density) { rect.height.toDp() }
            )
            .background(Color.White, shape = CircleShape)
            .border(
                width = 4.dp,
                color = boxColor,
                shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding( vertical = verticalPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 아이콘 이미지
            Image(
                painter = image,
                contentDescription = imageDescription,
                modifier = Modifier.fillMaxHeight(0.8f),
                contentScale = ContentScale.Fit
            )

            // 개수
            Text(
                text = "$value",
                style = textStyle.copy(
                    fontWeight = FontWeight.Bold)
            )
        }
    }
}