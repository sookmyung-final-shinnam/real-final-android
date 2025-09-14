package com.veryshinnam.myapp.feature.creation.conversation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeText
import kotlinx.coroutines.delay

@Composable
fun ConversationAnswerContent(
    onRecordDone: () -> Unit,
    modifier: Modifier
) {
    var progress by remember { mutableStateOf(0f) }

    val duration = 3000L   // 녹음 대기 3초
    val interval = 30L     // 업데이트 30초

    // 3초 동안 progress 증가
    LaunchedEffect(Unit) {
        val steps = (duration / interval).toInt()
        repeat(steps) { i ->
            progress = (i + 1) / steps.toFloat()
            delay(interval)
        }
        onRecordDone()  // 완료 시 API 호출
    }

    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.weight(0.1f))

        // 다람쥐 이미지
        Image(
            painter = painterResource(R.drawable.img_llm_question),
            contentDescription = "다람쥐 이미지",
            modifier = Modifier
                .weight(1f),
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.weight(0.12f))

        // 듣는 중 텍스트
        StrokeText(
            text = "듣는 중 ...",
            tColor = Color.White,
            oColor = colorResource(R.color.brand_orange),
            oWidth = 4f,
            fStyle = MaterialTheme.typography.displayLarge,
            fWeight = FontWeight.Bold,
            Modifier.weight(0.2f).align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.weight(0.02f))

        // 사용자 실시간 대답
        Text("사용자 실시간 대답",
            color = colorResource(R.color.main_orange),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(0.15f)
        )
        Spacer(Modifier.weight(0.1f))

        // 시간 3초 바
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .weight(0.1f)
                .clip(RoundedCornerShape(50)) // 둥근 모서리
                .border(2.dp, colorResource(R.color.main_orange), RoundedCornerShape(50))
        ) {
            // 배경
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.lemon_yellow), RoundedCornerShape(50))
            )
            // 진행 부분
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .background(colorResource(R.color.main_orange), RoundedCornerShape(50))
            )
        }

        Spacer(Modifier.weight(0.2f))
    }
}