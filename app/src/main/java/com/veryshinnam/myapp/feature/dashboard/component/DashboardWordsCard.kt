package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.dashboard.model.WordsData

@Composable
fun DashboardWordsCard(
    wordsList: List<WordsData>,
    verticalPadding: Dp = 24.dp,
    cardHeight: Dp = 600.dp,
    cardCorner: Dp = 16.dp,
    cardColor: Color = Color.White,
    barHeight: Dp = 320.dp,
    borderWidth: Dp = 4.dp,
    borderCorner: Dp = 16.dp,
    borderColor: Color = colorResource(R.color.deep_green),
    lGreenColor: Color = colorResource(R.color.light_green),
    orangeColor: Color = colorResource(R.color.main_orange),
    textStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(color = orangeColor, fontWeight = SemiBold, textDecoration = Underline),
    modifier: Modifier
) {

    var helpPressed by remember { mutableStateOf(false) }
    var barPressed by remember { mutableStateOf(false) }
    val maxCount = wordsList.maxOfOrNull { it.count } ?: 1

    // 막대 그래프 리스트
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(cardHeight)
            .background(cardColor, shape = RoundedCornerShape(cardCorner))
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(borderCorner)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(all = cardCorner)
        ) {
            // 도움말 터치 영역
            Box(
                modifier = Modifier.fillMaxWidth()
                    .size(48.dp)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitFirstDown()
                                helpPressed = true

                                // Up 또는 Cancel 대기
                                while (true) {
                                    val event = awaitPointerEvent()
                                    if (event.changes.all { !it.pressed }) {
                                        break
                                    }
                                }

                                helpPressed = false
                            }
                        }
                    },
                contentAlignment = Alignment.TopEnd
            ) {
                // 원형 도움말
                DashboardHelpButton()
            }

            Box{
                if (helpPressed) {
                    Box(
                        modifier = Modifier.background(borderColor).zIndex(20f)
                    ) {
                        Text("터치 중")
                    }
                }

                // 막대 리스트
                Column {
                    Text(
                        text = if (wordsList.first().createdAt.equals(wordsList.last().createdAt)) {
                            wordsList.first().createdAt
                        } else {
                            "${wordsList.first().createdAt} ~ ${wordsList.last().createdAt}"
                        }
                    )


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        wordsList.forEach { item ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "${item.count} 개")

                                Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(barHeight * (item.count / maxCount.toFloat()))
                                    .background(
                                        color = orangeColor,
                                        shape = RoundedCornerShape(
                                            topStart = 4.dp, topEnd = 4.dp,)
                                    )
                            )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(borderColor)
                    )

                    // 막대 날짜
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 26.dp)
                            .absoluteOffset(x = (-8).dp)
                    ) {
                        val itemWidth = maxWidth / wordsList.size

                        // 날짜 텍스트
                        wordsList.forEachIndexed { index, item ->
                            Box(
                                modifier = Modifier
                                    .absoluteOffset(x = itemWidth * index),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Text(
                                    text = item.createdAt,
                                    style = textStyle,
                                    modifier = Modifier.rotate(-40f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}