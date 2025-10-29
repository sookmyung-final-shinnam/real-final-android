package com.veryshinnam.myapp.feature.dashboard.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.common.component.UserInfo
import com.veryshinnam.myapp.feature.dashboard.component.DashboardTotalInfo

@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    horizontalPadding: Dp = 16.dp,
    spacerPadding: Dp = 20.dp,
    spanTextStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    vm: DashboardViewModel = hiltViewModel()
) {
    val uiState by vm.dashBoardUiState.collectAsStateWithLifecycle()

    // 뒤로 가기
    BackHandler { onBack() }

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = {
            // 상태바 만큼 여백 & 상단 로고
            Column {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                LogoBar(onLogoClick = onLogoClick)
            }
        },
        bottomBar = {
            // 네비게이션바 만큼 여백
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            // 뒤로 가기 버튼
            BackButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .zIndex(1f),
                onBackClick = onBack
            )

            when (val state = uiState) {
                // 조회 로딩
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
                        onRetry = { }
                    )
                }

                // 조회 성공
                is DashboardUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = horizontalPadding)
                    ) {
                        // 대시보드 상단 내용
                        UserInfo(
                            modifier = Modifier,
                            animalImage = painterResource(R.drawable.img_fox_cut),
                            animalDescription = "대시보드 설명 여우 이미지",
                            cardColor = colorResource(R.color.deep_green),
                            cardText = "${state.username}의 최근 관심사는 \"${state.interest}\"이야!",
                            spanText = "\"${state.interest}\"",
                            spanColor = colorResource(R.color.light_green),
                            spanTextStyle = spanTextStyle.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(Modifier.height(spacerPadding))

                        // 대시보드 전체 내용
                        DashboardTotalInfo(
                            modifier = Modifier,
                            username = state.username,
                            playData = state.playData,
                            languageData = state.languageData,
                            spacerPadding = spacerPadding
                        )
                    }
                }
            }
        }
    }
}