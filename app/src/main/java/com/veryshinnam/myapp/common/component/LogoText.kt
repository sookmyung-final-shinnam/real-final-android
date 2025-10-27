package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.veryshinnam.myapp.R

@Composable
fun LogoText(
    modifier: Modifier,
    subTitle: String = "대화하며 나만의 동화 만들기",
    mainTitle: String = "Storictor",
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 로고 위 문구 텍스트
        Text(
            text = subTitle,
            color = colorResource(R.color.brand_orange),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )

        // 로고
        Box {
            // 윤곽선 & 그림자
            Text(
                text = mainTitle,
                color = Color.White,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Black,
                    drawStyle = Stroke(width = 8f),
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(4f, 4f),
                        blurRadius = 8f
                    )
                )
            )

            // 실제 텍스트
            Text(
                text = "Storictor",
                color = colorResource(R.color.brand_orange),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Black
                )
            )
        }
    }
}