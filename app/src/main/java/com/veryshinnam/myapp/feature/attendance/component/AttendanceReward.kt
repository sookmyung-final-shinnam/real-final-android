package com.veryshinnam.myapp.feature.attendance.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.clearAndSetSemantics
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
    buttonText: String = "받기",
    onReceiveClick: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
    spacePadding: Dp = 15.dp, // 요소 간격 패딩
    textPadding: Dp = 10.dp,   // 버튼 텍스트 모든 방향 패딩
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .offset(y = spacePadding * 5),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painter,
            contentDescription = null, // 장식용
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(1.8f, 1.8f),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(spacePadding * 6))

        StrokeTitle(
            modifier = Modifier.clearAndSetSemantics { },
            titleText = text,
            titleColor = Color.White,
            strokeColor = colorResource(R.color.clear_blue),
            strokeWidth = 16f,
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
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(R.color.clear_blue),
                            colorResource(R.color.clear_blue),
                            colorResource(R.color.dark_blue))
                    ),
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    color = colorResource(R.color.clear_blue),
                    shape = CircleShape
                )
        ) {
            Text(
                text = buttonText,
                modifier = Modifier.padding(textPadding),
                style = textStyle
            )
        }
    }
}