package com.veryshinnam.myapp.feature.attendance.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.AppTopBar
import com.veryshinnam.myapp.component.common.BackButton
import com.veryshinnam.myapp.component.common.LoadErrorView
import com.veryshinnam.myapp.feature.attendance.component.AttendanceCalender
import com.veryshinnam.myapp.feature.attendance.component.AttendanceInfo
import org.threeten.bp.YearMonth

@Composable
fun AttendanceScreen(
    onBack: () -> Unit,
    vm: AttendanceViewModel = hiltViewModel()
) {
    val uiState by vm.attendanceUiState.collectAsStateWithLifecycle()

    BackHandler {
        onBack() // 뒤로 가기 (홈 이동)
    }

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = { AppTopBar() },
        bottomBar = {
            // 네비게이션 바만큼 여백
            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            BackButton(onBackClick = onBack, modifier = Modifier.align(Alignment.TopStart).zIndex(1f) )

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
                        onRetry = {  }
                    )
                }
                // 조회 성공
                is AttendanceUiState.Success -> {
                    Column(
                        Modifier.fillMaxSize().padding(16.dp)
                    ) {
                        // 출첵 설명 부분
                        AttendanceInfo(
                            month = state.month,
                            stamps = state.stamps,
                            attendances = state.attendances,
                            Modifier.weight(0.25f)    // 높이 비율 0.25
                        )

                        // 출첵 달력
                        AttendanceCalender(
                            month = state.month,
                            attendanceDates = state.attendanceDates,
                            usedDate = state.usedDate,
                            onPrevMonth = { vm.fetchPreviousMonth() },
                            onNextMonth = { vm.fetchNextMonth() },
                            Modifier.weight(0.75f)
                        )
                    }
                }
            }
        }
    }
}