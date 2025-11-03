package com.veryshinnam.myapp.feature.attendance.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeTitle

@Composable
fun AttendanceReward(
    painter: Painter,
    text: String,
    onReceiveClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
    spacePadding: Dp = 15.dp, // 요소 간격 패딩
    textPadding: Dp = 10.dp   // 버튼 텍스트 모든 방향 패딩
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painter,
            contentDescription = "보상 이미지",
            modifier = Modifier
                .fillMaxWidth(0.7f),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(spacePadding*2))

        StrokeTitle(
            modifier = Modifier,
            titleText = text,
            titleColor = Color.White,
            strokeColor = colorResource(R.color.clear_blue),
            strokeWidth = 12f,
            titleTextStyle = textStyle.copy(
                fontSize = textStyle.fontSize * 1.4f,
                textAlign = Center,
                lineHeight = textStyle.fontSize * 1.6f
            )
        )

        Spacer(Modifier.height(spacePadding))

        Button(
            onClick = onReceiveClick,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.clear_blue),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(
                text = "받기",
                modifier = Modifier.padding(textPadding),
                style = textStyle
            )
        }
    }
}