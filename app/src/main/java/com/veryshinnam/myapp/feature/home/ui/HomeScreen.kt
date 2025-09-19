package com.veryshinnam.myapp.feature.home.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.AppTopBar
import com.veryshinnam.myapp.component.common.LoadErrorView

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    onAttendanceClick: () -> Unit,
    onDashboardClick: () -> Unit, // 바텀 버튼
    onCreationClick: () -> Unit,  // 바텀 버튼
    onCollectionClick: () -> Unit,   // 바텀 버튼
    onCharacterClick: (Long) -> Unit,
    vm: HomeViewModel =  hiltViewModel()
) {
    // 홈화면 상태 관리
    val uiState by vm.homeUiState.collectAsStateWithLifecycle()

    // 동화책 상세 > 홈 화면: 세로 모드
    val context = LocalContext.current
    SideEffect { (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }

    // HomeScreen 진입할 때마다 실행
    LaunchedEffect(Unit) {
        vm.changeMessage()
    }

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = {
                AppTopBar() // 상단 로고
        },
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
            when (val state = uiState) {
                // 조회 로딩 중
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.main_orange), // 주황색
                        trackColor = Color.Gray.copy(alpha = 0.5f),
                        strokeWidth = 4.dp
                    )
                }

                // 조회 오류
                is HomeUiState.Error -> {
                    if (state.message.contains("401")) {

                    } else {
                    LoadErrorView(
                        message = state.message,
                        onRetry = { vm.reload() }
                    )
                    }
                }

                // 조회 성공
                is HomeUiState.Success -> {
                    HomeMainScreen(
                        homeData = state.homeData,
                        lastSelectedId = state.lastSelectedCharacter,
                        randomMessage = state.randomMessage,
                        onSettingsClick = onSettingsClick,
                        onAttendanceClick = onAttendanceClick,
                        onDashboardClick = onDashboardClick,
                        onCreationClick = onCreationClick,
                        onCollectionClick = onCollectionClick,
                        onCharacterClick = onCharacterClick,
                        onUpdateLastSelected = { id -> vm.updateLastSelectedCharacter(id) }
                    )
                }
            }
        }
    }
}