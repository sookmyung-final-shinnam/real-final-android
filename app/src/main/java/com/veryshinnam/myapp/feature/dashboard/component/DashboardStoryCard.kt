package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration.Companion.None
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeTitle
import com.veryshinnam.myapp.feature.dashboard.model.Attempt
import com.veryshinnam.myapp.feature.dashboard.model.StoryAnalysisData

@Composable
fun DashboardStoryCard(
    story: StoryAnalysisData,
    onStoryClick: (Long) -> Unit,
    spacer: Dp = 6.dp,
    verticalPadding: Dp = 24.dp,
    cardCorner: Dp = 16.dp,
    cardColor: Color = Color.White,
    borderWidth: Dp = 4.dp,
    borderCorner: Dp = 16.dp,
    borderColor: Color = colorResource(R.color.deep_green),
    orangeColor: Color = colorResource(R.color.main_orange),
    lGreenColor: Color = colorResource(R.color.light_green),
    imageWidth: Float = 0.25f,
    subTitleTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = Bold),
    linkTextStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = orangeColor, fontWeight = SemiBold, textDecoration = Underline),
    summaryTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = SemiBold),
    modifier: Modifier
) {

    // ui 변수
    var titlePressed by remember { mutableStateOf(false) }
    var attemptPressed by remember { mutableStateOf(false) }
    var emotionPressed by remember { mutableStateOf(false) }
    var wordPressed by remember { mutableStateOf(false) }
    var isMore by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val textHeight = summaryTextStyle.lineHeight.value.dp * 6
    val barHeight = 180.dp

    val colors = listOf(
        colorResource(R.color.emotion_1),
        colorResource(R.color.emotion_2),
        colorResource(R.color.emotion_3),
        colorResource(R.color.emotion_4),
        colorResource(R.color.emotion_5),
        lGreenColor
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = cardCorner),
        verticalArrangement = Arrangement.spacedBy(spacer)
    ) {

        Box(
            modifier = Modifier.zIndex(20f),
            contentAlignment = Alignment.BottomStart
        ) {
            Box {
                // 제목
                DashboardCardTitle(
                    title = "동화 언어 및 정서 분석",
                    borderColor = borderColor,
                    cardCorner = cardCorner,
                    spacer = imageWidth,
                    modifier = Modifier.fillMaxWidth()
                )
                // 원형 도움말
                DashboardHelpButton(
                    onPress = {pressed ->
                        titlePressed = pressed
                    },
                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = spacer*2)
                )
            }

            // 이미지
            Image(
                painter = painterResource(R.drawable.img_book),
                contentDescription = "책 이미지",
                modifier = Modifier
                    .fillMaxWidth(imageWidth)
                    .graphicsLayer {
                        scaleX = 1.3f
                        scaleY = 1.3f
                        translationX = -12.dp.toPx() // 왼쪽으로 이동
                        translationY = 22.dp.toPx() // 아래로 이동
                    },
                contentScale = ContentScale.Fit
            )
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
                    modifier = Modifier
                        .background(borderColor)
                        .zIndex(20f)
                ) {
                    Text("터치 중")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = cardCorner, vertical = verticalPadding),
            ) {
                // -- 동화 제목
                Text(
                    text = "storyIdstoryIdstoryId: ${story.storyId}",
                    style = summaryTextStyle.copy(fontWeight = Bold, fontSize = summaryTextStyle.fontSize*1.2f),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(spacer))

                Text(
                    text = "동화 보러 가기",
                    modifier = Modifier.align(Alignment.End)
                        .clickable {
                            onStoryClick(story.storyId)
                        },
                    style = linkTextStyle
                )

                Spacer(Modifier.height(cardCorner*2)) // 간격

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = verticalPadding)) {
                    Column{
                        // -- 1. 시도 횟수 영역
                        Box(modifier = Modifier.fillMaxWidth()) {
                            // 원형 도움말
                            DashboardHelpButton(
                                onPress = {pressed ->
                                    attemptPressed = pressed
                                },
                                modifier = Modifier.align(Alignment.TopEnd)
                            )

                            Box {
                                if (attemptPressed) {
                                    Box(
                                        modifier = Modifier
                                            .background(borderColor)
                                            .zIndex(20f)
                                    ) {
                                        Text("터치 중")
                                    }
                                }
                                // 평균 값
                                Column{
                                    Text("• 평균 시도 횟수: ${story.avgAttemptPerStage}회", style = subTitleTextStyle)
                                    Spacer(Modifier.height(spacer))


                                    // 기승전결
                                    Attempt.values().forEach { attempt ->

                                        DashboardAttemptRow(
                                            title = attempt.label,
                                            count = story.attempts[attempt] ?: 1,
                                            subTextStyle = summaryTextStyle,
                                        )
                                    }
                                }
                            }
                        }

                        // -- 2. 새 단어 분석
                        Spacer(Modifier.height(verticalPadding*2))
                        Box(modifier = Modifier.fillMaxWidth()) {
                            // 원형 도움말
                            DashboardHelpButton(
                                onPress = {pressed ->
                                    wordPressed = pressed
                                },
                                modifier = Modifier.align(Alignment.TopEnd)
                            )

                            Box {
                                if (wordPressed) {
                                    Box(
                                        modifier = Modifier
                                            .background(borderColor)
                                            .zIndex(20f)
                                    ) {
                                        Text("터치 중")
                                    }
                                }
                                Column {
                                    Text("• 평균 답변 길이: ${story.avgAnswerLength}자", style = subTitleTextStyle)
                                    Text("• 획득한 새 단어 개수: ${story.newWords.size}개", style = subTitleTextStyle)
                                    Text(
                                        text = if (isMore) "닫기" else "자세히 보기",
                                        style = linkTextStyle,
                                        modifier = Modifier.clickable { isMore = !isMore }
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(spacer))

                        Box {
                            if (isMore) {
                                Box (modifier = Modifier
                                    .matchParentSize()
                                    .zIndex(10f)
                                    .border(
                                        width = 2.dp,
                                        color = borderColor,
                                        shape = RoundedCornerShape(cardCorner)
                                    )
                                    .background(lGreenColor, shape = RoundedCornerShape(cardCorner)))
                                {
                                    FlowRow(
                                        modifier = Modifier
                                            .verticalScroll(scrollState) // 스크롤
                                            .padding(8.dp, 16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        story.newWords.forEach { word ->
                                            // 버블
                                            Box(
                                                modifier = Modifier
                                                    .background(
                                                        color = Color.White,
                                                        shape = CircleShape
                                                    )
                                                    .border(
                                                        width = 2.dp,
                                                        color = borderColor,
                                                        shape = CircleShape
                                                    )
                                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                                            ) {
                                                // 단어
                                                Text(
                                                    text = word,
                                                    style = linkTextStyle.copy(
                                                        textDecoration = None
                                                    )
                                                )
                                            }
                                        }
                                    }

                                    // 스크롤 가능 여부 효과
                                    if (scrollState.canScrollForward) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(48.dp)
                                                .align(Alignment.BottomCenter)
                                                .background(
                                                    Brush.verticalGradient(
                                                        colors = listOf(
                                                            Color.Transparent,
                                                            Color.Gray.copy(alpha = 0.5f)
                                                        )
                                                    ),
                                                    shape = RoundedCornerShape(cardCorner)
                                                )
                                        )
                                    }
                                }
                            }

                            // -- 3. 정서 분석
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Spacer(Modifier.height(verticalPadding*2))

                                // 원형 도움말
                                DashboardHelpButton(
                                    onPress = {pressed ->
                                        emotionPressed = pressed
                                    },
                                    modifier = Modifier.align(Alignment.End)
                                )

                                Box {
                                    if (emotionPressed) {
                                        Box(
                                            modifier = Modifier
                                                .background(borderColor)
                                                .zIndex(20f)
                                        ) {
                                            Text("터치 중")
                                        }
                                    }

                                    // 정서 분석
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        // 막대 바
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.Bottom
                                        ) {
                                            story.emotions.forEachIndexed { index, item ->
                                                val barColor = colors[index]

                                                BoxWithConstraints(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(barHeight)
                                                ) {
                                                    val height = maxHeight * item.ratio // item별 막대 높이

                                                    // 퍼센트
                                                    if (item.ratio > 0) {
                                                        Text(
                                                            text = "${(item.ratio * 100).toInt()}%",
                                                            style = linkTextStyle.copy(color = barColor, textDecoration = None),
                                                            softWrap = false,
                                                            modifier = Modifier
                                                                .align(Alignment.BottomCenter)
                                                                .offset(y = -(height + spacer)),
                                                            textAlign = TextAlign.Center
                                                        )
                                                    }

                                                    // 막대 그래프
                                                    Box(
                                                        modifier = Modifier
                                                            .align(Alignment.BottomCenter)
                                                            .fillMaxWidth(0.6f)
                                                            .height(height)
                                                            .background(
                                                                color = barColor ,
                                                                shape = RoundedCornerShape(
                                                                    topStart = 4.dp,
                                                                    topEnd = 4.dp
                                                                )
                                                            )
                                                    )
                                                }
                                            }
                                        }

                                        // x축 선
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(2.dp)
                                                .background(borderColor)
                                        )

                                        // x축
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                                            verticalAlignment = Alignment.Bottom,
                                        ) {
                                            story.emotions.forEachIndexed { index, item ->
                                                val barColor = colors[index]

                                                StrokeTitle(
                                                    titleText = item.name,
                                                    titleColor = Color.White,
                                                    strokeColor = barColor,
                                                    titleTextStyle = linkTextStyle.copy(textDecoration = None),
                                                    strokeWidth = 10f,
                                                    softWrap = false,
                                                    modifier = Modifier
                                                        .graphicsLayer { rotationZ = -30f }
                                                        .weight(1f)
                                                        .align(Alignment.CenterVertically)
                                                )
                                            }
                                        }

                                        // 요약 및 날짜
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = cardCorner)
                                                .height(textHeight),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(story.summary + "\n\n" + story.createdAt,
                                                style = summaryTextStyle, textAlign = TextAlign.Center)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}