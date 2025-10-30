package com.veryshinnam.myapp.feature.attendance.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeTitle
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

@Composable
fun AttendanceCalender(
    month: YearMonth,
    attendanceDates: Set<LocalDate>,
    usedDate: LocalDate?,
    stamps: Int,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onStampClick: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
    dateTextStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
    sectionPadding:Dp = 5.dp,   // 섹션 패딩, 요일 행 패딩
    calendarPadding: Dp = 16.dp, // 달력 패딩, 도장 아래 패딩
    roundCorner: Dp = 16.dp,     // 둥근 모서리
    modifier: Modifier
) {
    val density = LocalDensity.current
    val buttonSize = with(density) { textStyle.fontSize.toDp() * 1.2f }

    val days = month.lengthOfMonth()                                 // 이번 달 일 수
    val firstDay = month.atDay(1).dayOfWeek.value % 7   // 이번 달 1일의 요일
    val weeks = (firstDay + days + 6) / 7                            // 달력 그리기에 필요한 행 수
    val weekDays = listOf("일", "월", "화", "수", "목", "금", "토")    // 요일

    val currentMonth = YearMonth.now() // 현재 달
    val isNextEnabled = month.isBefore(currentMonth) // 미래 시점 이동 불가

    // 보상 날짜 > 10개 찍은 순간의 마지막 날짜 (오늘 포함)
    val today = LocalDate.now()
    val rewardDate = if (stamps >= 10) {
        if (attendanceDates.contains(today)) today else attendanceDates.maxOrNull()
    } else null

    // 배경 (테두리)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White, shape = RoundedCornerShape(roundCorner))
            .border(
                width = 4.dp,
                color = colorResource(R.color.deep_pink),
                shape = RoundedCornerShape(roundCorner)
            )
            .padding(top = calendarPadding, start = calendarPadding / 2, end = calendarPadding / 2)
    ) {
        // 연도 + 월 + 달력
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(sectionPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 연도
            Text(
                text = "${month.year}",
                color = colorResource(R.color.deep_pink),
                style = textStyle
            )

            // 월 (이동 버튼)
            Row(
                modifier = Modifier
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(calendarPadding)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_calendar_left),
                    contentDescription = "이전 달",
                    modifier = Modifier
                        .size(buttonSize)
                        .clickable { onPrevMonth() }
                )

                Text(
                    text = "${month.monthValue}월",
                    style = textStyle.copy(fontSize = textStyle.fontSize * 1.8f)
                )

                Image(
                    painter = painterResource(id = R.drawable.img_calendar_right),
                    contentDescription = "다음 달",
                    modifier = Modifier
                        .size(buttonSize)
                        .let {
                            if (isNextEnabled) it.clickable { onNextMonth() }
                            else it.alpha(0.3f) // 비활성화
                        }
                )
            }

            // 달력 (요일 + 날짜)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // 나머지 높이 차지
                        verticalArrangement = Arrangement.spacedBy(sectionPadding * 2),
            ) {
                // 요일
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            color = colorResource(R.color.light_pink),
                            shape = CircleShape),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    weekDays.forEach { day ->
                        Box(
                            modifier = Modifier
                                .weight(1f), // 날짜랑 동일 너비
                            contentAlignment = Alignment.Center
                        ) {
                            StrokeTitle(
                                modifier = Modifier,
                                titleText = day,
                                titleColor = Color.White,
                                strokeColor = colorResource(R.color.deep_pink),
                                strokeWidth = 4f,
                                titleTextStyle = textStyle
                            )
                        }
                    }
                }

                // 날짜
                BoxWithConstraints(
                    modifier = Modifier
                        .weight(1f) // 나머지 높이 차지
                        .fillMaxSize()
                ) {
                    val cellWidth = maxWidth / 7
                    val cellHeight = maxHeight / weeks

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        userScrollEnabled = false,
                    ) {
                        // 1일 전까지 공간
                        items(firstDay) {
                            Spacer(Modifier.size(cellWidth, cellHeight))
                        }

                        // 날짜 시작
                        items(days) { index ->
                            val date = month.atDay(index + 1)
                            val attended = date in attendanceDates
                            val isRewarded = usedDate != null && date.isBefore(usedDate.plusDays(1))

                            Column(
                                modifier = Modifier.size(cellWidth, cellHeight),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text( // 날짜
                                    text = "${index + 1}",
                                    style = dateTextStyle,
                                )

                                if (attended) { // 도장
                                    when {
                                        // 아직 보상 안 받은 보상 도장
                                        date == rewardDate && !isRewarded -> {
                                            Image(
                                                painter = painterResource(R.drawable.img_stamp_shining_yellow),
                                                contentDescription = "보상 도장",
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(bottom = calendarPadding)
                                                    .fillMaxSize()
                                                    .clickable { onStampClick() },
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                        // 일반 도장 (보상 받은 건 반투명)
                                        else -> {
                                            Image(
                                                painter = painterResource(R.drawable.img_stamp_shining_no),
                                                contentDescription = "출석 도장",
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(bottom = calendarPadding)
                                                    .fillMaxSize()
                                                    .alpha(if (isRewarded) 0.5f else 1f),
                                                contentScale = ContentScale.Fit
                                            )
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