package com.veryshinnam.myapp.feature.attendance.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import org.threeten.bp.YearMonth

@Composable
fun AttendanceCalender(
    modifier: Modifier
) {
    var month by remember { mutableStateOf(YearMonth.now()) } // 이번 달
    val days = month.lengthOfMonth()                                 // 이번 달 일 수
    val firstDay = month.atDay(1).dayOfWeek.value % 7    // 이번 달 1일의 요일
    val weekDays = listOf("일", "월", "화", "수", "목", "금", "토")     // 요일
    val weeks = (firstDay + days + 6) / 7   // 달력 그리기에 필요한 행 수

    val sectionPadding = 8.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 4.dp,
                color = colorResource(R.color.deep_pink),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
        ) {
            // 연도
            Text(
                text = "${month.year}",
                color = colorResource(R.color.deep_pink),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(sectionPadding))

            // 월 + 이동 버튼
            Row(
                Modifier.fillMaxWidth(0.4f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image( painter = painterResource(id = R.drawable.img_calendar_left),
                    contentDescription = "이전 달",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { month = month.minusMonths(1) }
                )

                Text(
                    text = "${month.monthValue}월",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold
                )

                Image(
                    painter = painterResource(id = R.drawable.img_calendar_right),
                    contentDescription = "다음 달",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { month = month.plusMonths(1) }
                )
            }

            Spacer(Modifier.height(sectionPadding))

            // 달력
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // 요일
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            color = colorResource(R.color.light_pink),
                            shape = CircleShape
                        ),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    weekDays.forEach { day ->
                        Box(
                            modifier = Modifier.weight(1f), // 날짜랑 동일 너비
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(Modifier.height(sectionPadding))

                // 날짜
                BoxWithConstraints(
                    modifier = Modifier
                        .weight(1f)
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
                            Spacer(
                                Modifier
                                    .size(cellWidth, cellHeight)
                            )
                        }

                        // 날짜 셀
                        items(days) { index ->
                            val day = index + 1
                            Box(
                                Modifier
                                    .size(cellWidth, cellHeight)
                                    .border(0.5.dp, Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$day",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.align(Alignment.TopCenter)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}