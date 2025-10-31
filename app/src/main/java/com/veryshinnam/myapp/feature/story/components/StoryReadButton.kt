package com.veryshinnam.myapp.feature.story.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R

@Composable
fun StoryReadButton(
    text: String = "  ▶ 보러 가기",
    onButtonClick: () -> Unit,
    textextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontWeight = FontWeight.Bold),
    buttonVertical: Dp = 16.dp,
    buttonHorizontal:  Dp = 24.dp,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        // 토끼 이미지
        Image(
            painter = painterResource(R.drawable.img_rabbit_full),
            contentDescription = "토끼 이미지",
            modifier = Modifier
                .zIndex(1f)
                .fillMaxHeight()
                .clickable(
                    indication = null, // 클릭 ripple 효과 제거
                    interactionSource = remember { MutableInteractionSource() }
                ) { onButtonClick() }
                .offset(30.dp)
                .graphicsLayer {
                    scaleX = -1f }, // 좌우 반전
            contentScale = ContentScale.Fit
        )

        // 보러가기 버튼
        Button(
            onClick =  onButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.blue_sky),
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(horizontal = buttonHorizontal, vertical = buttonVertical),
            shape = CircleShape,
            modifier = Modifier
                .border(
                    width = 4.dp,
                    color = colorResource(id = R.color.blue_gray),
                    shape = CircleShape
                )
        ) {
            Text(
                text = text,
                style = textextStyle
            )
        }
    }
}