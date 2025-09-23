package com.veryshinnam.myapp.feature.attendance.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.feature.attendance.component.AttendanceCalender
import com.veryshinnam.myapp.feature.attendance.component.AttendanceInfo
import com.veryshinnam.myapp.feature.attendance.component.AttendanceReward

@Composable
fun AttendanceScreen(
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    vm: AttendanceViewModel = hiltViewModel()
) {
    val uiState by vm.attendanceUiState.collectAsStateWithLifecycle()
    var isTodayStamp by remember { mutableStateOf(false) }
    var isReward by remember { mutableStateOf(false) }

    BackHandler {
        onBack() // 뒤로 가기 (홈 이동)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_yellow))
    ) {
        // 상단 AppBar
        AppTopBar(onLogoClick = onLogoClick)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BackButton(
                onBackClick = onBack,
                modifier = Modifier.align(Alignment.TopStart).zIndex(1f)
            )

            when (val state = uiState) {
                // 조회 로딩 중
                is AttendanceUiState.Loading -> {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.main_orange), // 주황색
                        trackColor = Color.Gray.copy(alpha = 0.5f),
                        strokeWidth = 4.dp
                    )
                }
                // 조회 오류
                is AttendanceUiState.Error -> {
                    LoadErrorView(
                        message = state.message,
                        onRetry = { }
                    )
                }
                // 조회 성공
                is AttendanceUiState.Success -> {
                    Column(
                        Modifier.fillMaxSize().padding(16.dp)
                    ) {
                        if (!state.isTodayAttendance) {
                            isTodayStamp = true
                        }

                        // 출첵 설명 부분
                        AttendanceInfo(
                            month = state.month,
                            stamps = state.stamps,
                            attendances = state.attendances,
                            modifier = Modifier.weight(0.25f)    // 높이 비율 0.25
                        )

                        Spacer(Modifier.height(4.dp))

                        // 출첵 달력
                        AttendanceCalender(
                            month = state.month,
                            attendanceDates = state.attendanceDates,
                            usedDate = state.usedDate,
                            stamps = state.stamps,
                            onPrevMonth = { vm.fetchPreviousMonth() },
                            onNextMonth = { vm.fetchNextMonth() },
                            onStampClick = { isReward = true },
                            modifier = Modifier.weight(0.75f),
                        )
                        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                    }


                }
            }
        }
    }

    if (isTodayStamp) {
        AttendanceReward(
            painter = painterResource(R.drawable.img_stamp_shining_blue),
            text = "출석 완료",
            onReceiveClick = {
                vm.fetchAttendance()
                isTodayStamp = false // 닫기
            },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f),
        )
    }

    if (isReward) {
        val rewardCount = (uiState as? AttendanceUiState.Success)?.stamps?.div(10) ?: 0
        AttendanceReward(
            painter = painterResource(R.drawable.img_compass_shining),
            text = "나침반 ${rewardCount}개",
            onReceiveClick = {
                vm.fetchAttendanceReward()
                isReward = false // 닫기
            },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
        )
    }
}