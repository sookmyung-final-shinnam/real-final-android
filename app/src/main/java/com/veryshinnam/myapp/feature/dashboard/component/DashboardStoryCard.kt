package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.dashboard.model.Attempt
import com.veryshinnam.myapp.feature.dashboard.model.StoryAnalysisData

@Composable
fun DashboardStoryCard(
    story: StoryAnalysisData,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    cardCorner: Dp = 16.dp,
    cardColor: Color = Color.White,
    borderWidth: Dp = 4.dp,
    borderCorner: Dp = 16.dp,
    borderColor: Color = colorResource(R.color.deep_green),
    orangeColor: Color = colorResource(R.color.main_orange),
    titleTextStyle: TextStyle,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = Bold),
    modifier: Modifier
) {

    var titlePressed by remember { mutableStateOf(false) }
    var attemptPressed by remember { mutableStateOf(false) }
    var emotionPressed by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {

        // 제목
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    borderColor,
                    shape = RoundedCornerShape(cardCorner)
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Text("동화 언어 및 정서 분석", style = titleTextStyle)

            // 도움말 터치 영역
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitFirstDown()
                                titlePressed = true

                                // Up 또는 Cancel 대기
                                while (true) {
                                    val event = awaitPointerEvent()
                                    if (event.changes.all { !it.pressed }) {
                                        break
                                    }
                                }

                                titlePressed = false
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // 원형 도움말
                DashboardHelpButton()
            }
        }
        Box {
            Row(
                modifier = Modifier.fillMaxWidth().zIndex(10f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ▶ 다음 버튼
                Button(
                    onClick = { onPrevClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = orangeColor.copy(0.5f),
                        contentColor = Color.White
                    ),
                    border = BorderStroke(
                        2.dp,
                        orangeColor
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(48.dp)
                ) {
                    Text(
                        text = "◀",
                    )
                }

                // ▶ 다음 버튼
                Button(
                    onClick = { onNextClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = orangeColor.copy(0.5f),
                        contentColor = Color.White
                    ),
                    border = BorderStroke(
                        2.dp,
                        orangeColor
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(48.dp)
                ) {
                    Text(
                        text = "▶",
                    )
                }
            }
            // 시도횟수 및 감정분석
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(cardColor, shape = RoundedCornerShape(cardCorner))
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = RoundedCornerShape(borderCorner)
                    )
            ) {
                if (titlePressed) {
                    Box(
                        modifier = Modifier.background(borderColor).zIndex(20f)
                    ) {
                        Text("터치 중")
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "storyId: ${story.storyId}",
                        style = titleTextStyle.copy(color = Color.Black),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "동화 보러 가기",
                        modifier = Modifier.align(Alignment.End),
                        style = textStyle.copy(
                            color = orangeColor,
                            fontWeight = SemiBold,
                            textDecoration = Underline),
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .drawBehind {
                                drawLine(
                                    color = borderColor,
                                    start = Offset(size.width / 2, 0f),
                                    end = Offset(size.width / 2, size.height),
                                    strokeWidth = borderWidth.toPx()
                                )
                            }
                    ) {
                        // -- 시도 횟수 영역
                        Column(
                            modifier = Modifier.weight(1f).fillMaxWidth()
                        ) {
                            // 도움말 터치 영역
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(48.dp)
                                    .pointerInput(Unit) {
                                        awaitPointerEventScope {
                                            while (true) {
                                                awaitFirstDown()
                                                attemptPressed = true

                                                // Up 또는 Cancel 대기
                                                while (true) {
                                                    val event = awaitPointerEvent()
                                                    if (event.changes.all { !it.pressed }) {
                                                        break
                                                    }
                                                }

                                                attemptPressed = false
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.TopEnd
                            ) {
                                // 원형 도움말
                                DashboardHelpButton()
                            }
                            Box {
                                if (attemptPressed) {
                                    Box(
                                        modifier = Modifier.background(borderColor).zIndex(20f)
                                    ) {
                                        Text("터치 중")
                                    }
                                }
                                // 평균 값
                                Column{
                                    Text("평균 답변 길이: ${story.avgAnswerLength}자", style = textStyle)
                                    Text("평균 시도 횟수: ${story.avgAttemptPerStage}회", style = textStyle)

                                    // 기승전결
                                    Attempt.values().forEach { attempt ->

                                        DashboardAttemptRow(
                                            title = attempt.label,
                                            count = story.attempts[attempt] ?: 1
                                        )
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {

                        }
                    }
                }
            }
        }
    }
}