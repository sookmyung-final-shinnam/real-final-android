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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.AppTopBar
import com.veryshinnam.myapp.component.common.BackButton
import com.veryshinnam.myapp.feature.attendance.component.AttendanceCalender
import com.veryshinnam.myapp.feature.attendance.component.AttendanceInfo

@Composable
fun AttendanceScreen(
    onBack: () -> Unit
) {
//    val uiState by vm.dashBoardUiState.collectAsStateWithLifecycle()

    BackHandler { onBack() }

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

//            when (val state = uiState) {
//                // 조회 로딩 중
//                is DashboardUiState.Loading -> {
//                    CircularProgressIndicator(
//                        color = colorResource(id = R.color.main_orange), // 주황색
//                        trackColor = Color.Gray.copy(alpha = 0.5f),
//                        strokeWidth = 4.dp
//                    )
//                }
//                // 조회 오류
//                is DashboardUiState.Error -> {
//                    LoadErrorView(
//                        message = state.message,
//                        onRetry = {  }
//                    )
//                }
//                // 조회 성공
//                is DashboardUiState.Success -> {
                    Column(
                        Modifier.fillMaxSize().padding(16.dp)
                    ) {
                        // 출첵 설명 부분
                        AttendanceInfo(
                            Modifier.weight(0.25f)    // 높이 비율 0.25
                        )

                        // 출첵 달력
                        AttendanceCalender(Modifier.weight(0.75f))
                    }
                }
            }
//        }
//    }
}