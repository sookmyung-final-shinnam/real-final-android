package com.veryshinnam.myapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun HomeUserItem(
    painter: Painter,
    contentDescription: String,
    value: String,
    spacer: Dp = 8.dp,
    color: Color,
    modifier: Modifier
) {
    Box(
        // 테두리
        modifier = modifier
            .fillMaxHeight()
            .background(Color.White, shape = CircleShape)
            .border(
                width = 4.dp,
                color = color,
                shape =CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically) {
            // 아이콘 이미지
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxHeight(0.8f),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(spacer))
            Text(
                // 개수
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold)
            )
        }
    }
}