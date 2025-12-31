package com.veryshinnam.myapp.feature.creation.content.conversation

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeTitle
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationAnswerText
import com.veryshinnam.myapp.feature.creation.model.AnswerData
import kotlinx.coroutines.delay

@Composable
fun ConversationAnswerContent(
    answerData: AnswerData,
    onRecordStop: () -> Unit,
    onFeedback: () -> Unit,
    modifier: Modifier,
    listenTextStyle: TextStyle = MaterialTheme.typography.titleLarge,
    spacerPadding: Dp = 10.dp,
    verticalPadding: Dp = 20.dp,
) {
    var progress by remember { mutableStateOf(0f) }
    var isFinal by remember { mutableStateOf(false) } // userText 고정 여부

    val duration = 3000L   // 녹음 대기 3초
    val interval = 30L     // 업데이트 30초

    // 3초 동안 progress 증가
    LaunchedEffect(Unit) {
        val steps = (duration / interval).toInt()
        repeat(steps) { i ->
            progress = (i + 1) / steps.toFloat()
            delay(interval)
        }
        onRecordStop() // 3초 후 녹음 중단

        isFinal = true // 최종 답변 표시

        delay(1000L) // 1초 후 피드백으로
        onFeedback()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(verticalPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- 진행바 높이
        Spacer(Modifier.fillMaxHeight(0.15f))

        // --- 다람쥐 이미지
        Image(
            painter = painterResource(R.drawable.img_llm_question),
            contentDescription = "다람쥐 이미지",
            modifier = Modifier.fillMaxHeight(0.5f),
            contentScale = ContentScale.Fit
        )

        //  텍스트 + 진행바
        Column(
            modifier = modifier
                .weight(1f)
                .padding(bottom = verticalPadding),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                StrokeTitle(
                    titleText = "듣고 있어요 ...",
                    titleColor = Color.White,
                    strokeColor = colorResource(R.color.brand_orange),
                    titleTextStyle = listenTextStyle.copy(fontSize = listenTextStyle.fontSize * 1.4f),
                    strokeWidth = 12f,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(spacerPadding))

                // 사용자 실시간 대답
                ConversationAnswerText(
                    answerData = answerData,
                    isFinal = isFinal,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 시간 3초 바
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.15f)
                    .clip(CircleShape)
                    .border(2.dp, colorResource(R.color.main_orange), CircleShape),
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
                        .align(Alignment.CenterStart)
                        .fillMaxHeight()
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .background(colorResource(R.color.main_orange), RoundedCornerShape(50))
                )
            }
        }
    }
}