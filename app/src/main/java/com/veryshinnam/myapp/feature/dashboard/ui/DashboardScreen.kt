package com.veryshinnam.myapp.feature.dashboard.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.veryshinnam.myapp.feature.dashboard.ui.component.DashboardUserInfo
import com.veryshinnam.myapp.feature.dashboard.ui.component.DashboardLanguageInfo

@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    vm: DashboardViewModel = hiltViewModel()
) {
    val uiState by vm.dashBoardUiState.collectAsStateWithLifecycle()

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

            when (val state = uiState) {
                // 조회 로딩 중
                is DashboardUiState.Loading -> {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.main_orange), // 주황색
                        trackColor = Color.Gray.copy(alpha = 0.5f),
                        strokeWidth = 4.dp
                    )
                }
                // 조회 오류
                is DashboardUiState.Error -> {
                    LoadErrorView(
                        message = state.message,
                        onRetry = {  }
                    )
                }
                // 조회 성공
                is DashboardUiState.Success -> {
                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                        val topSectionHeight = maxHeight * 0.25f

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                        ) {
                            item {
                                DashboardUserInfo(
                                    username = state.username,
                                    interest = state.interest,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(topSectionHeight) // 화면 높이의 25%
                                )
                            }
                            item {
                                DashboardLanguageInfo(
                                username = state.username,
                                playData = state.playData,
                                modifier = Modifier.fillMaxWidth()
                                )
                            }
                            item {
                                Spacer(Modifier.height(40.dp))
                            }
                            item {
                                DashboardLanguageInfo(
                                    username = state.username,
                                    languageData = state.languageData,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}