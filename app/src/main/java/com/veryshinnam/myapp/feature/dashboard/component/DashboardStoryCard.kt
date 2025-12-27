package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
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
    spacer: Dp = 6.dp,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 28.dp,
    cardCorner: Dp = 16.dp,
    cardColor: Color = Color.White,
    borderWidth: Dp = 4.dp,
    borderCorner: Dp = 16.dp,
    borderColor: Color = colorResource(R.color.deep_green),
    orangeColor: Color = colorResource(R.color.main_orange),
    titleTextStyle: TextStyle,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = Bold),
    subTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = SemiBold),
    modifier: Modifier
) {

    var titlePressed by remember { mutableStateOf(false) }
    var attemptPressed by remember { mutableStateOf(false) }
    var emotionPressed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacer)
    ) {

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
            Text("동화 언어 및 정서 분석", style = titleTextStyle,
                modifier = Modifier.padding(vertical = horizontalPadding))

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

        // 내용
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // 이동 버튼
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

            // 내용
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
                        .padding(horizontal = horizontalPadding, vertical = verticalPadding),
                ) {
                    Text(
                        text = "storyIdstoryIdstoryId: ${story.storyId}",
                        style = titleTextStyle.copy(color = Color.Black),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(spacer))
                    Text(
                        text = "동화 보러 가기",
                        modifier = Modifier.align(Alignment.End),
                        style = textStyle.copy(
                            color = orangeColor,
                            fontWeight = SemiBold,
                            textDecoration = Underline),
                    )


                    // -- 시도 횟수 영역
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // 도움말 터치 영역

                        Spacer(Modifier.height(spacer*2))
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
                                Spacer(Modifier.height(spacer))
                                Text("평균 시도 횟수: ${story.avgAttemptPerStage}회", style = textStyle)

                                Spacer(Modifier.height(spacer*2))

                                // 기승전결
                                Attempt.values().forEach { attempt ->

                                    DashboardAttemptRow(
                                        title = attempt.label,
                                        count = story.attempts[attempt] ?: 1,
                                        subTextStyle = subTextStyle,
                                    )
                                }
                            }
                        }
                    }

                    // -- 정서 분석
                    Column(
                        modifier = Modifier.fillMaxWidth()
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
                                            emotionPressed = true

                                            // Up 또는 Cancel 대기
                                            while (true) {
                                                val event = awaitPointerEvent()
                                                if (event.changes.all { !it.pressed }) {
                                                    break
                                                }
                                            }

                                            emotionPressed = false
                                        }
                                    }
                                },
                            contentAlignment = Alignment.TopEnd
                        ) {
                            // 원형 도움말
                            DashboardHelpButton()
                        }

                        Box {
                            if (emotionPressed) {
                                Box(
                                    modifier = Modifier.background(borderColor).zIndex(20f)
                                ) {
                                    Text("터치 중")
                                }
                            }

                            // 정서 차트
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                DashboardDonutChart(story.emotions,modifier = Modifier.fillMaxWidth() )
                                Text(story.summary, style = subTextStyle, textAlign = TextAlign.Center)
                                Text(story.createdAt, style = textStyle.copy(fontWeight = SemiBold), textAlign = TextAlign.Center)
                            }

                        }
                    }

                }
            }
        }
    }
}