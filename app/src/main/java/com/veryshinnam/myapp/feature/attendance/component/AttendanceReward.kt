package com.veryshinnam.myapp.feature.attendance.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeText

@Composable
fun AttendanceReward(
    painter: Painter,
    text: String,
    onReceiveClick: () -> Unit,
    modifier: Modifier = Modifier,
    spacePadding: Dp = 30.dp, // 요소 간격 패딩
    textPadding: Dp = 16.dp   // 텍스트 모든 방향 패딩
) {
    Column(
        modifier = modifier.fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // 배경색: 검정 알파 50
            .windowInsetsPadding(WindowInsets(0, 0, 0, 0)),
        verticalArrangement = Arrangement.spacedBy(spacePadding, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painter,
            contentDescription = "보상 이미지",
            modifier = Modifier
                .fillMaxWidth(0.8f),
            contentScale = ContentScale.Fit
        )

        StrokeText(
            text = text,
            tColor = Color.White,
            oColor = colorResource(R.color.clear_blue),
            oWidth = 4f,
            fStyle = MaterialTheme.typography.displayLarge,
            fWeight = FontWeight.Bold,
            Modifier.align(Alignment.CenterHorizontally)
        )

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
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}