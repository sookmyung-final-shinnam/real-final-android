package com.veryshinnam.myapp.feature.dashboard.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.UserInfo
import com.veryshinnam.myapp.core.orientation.OrientationManager
import com.veryshinnam.myapp.feature.dashboard.component.DashBoardStaticsAnalysis
import com.veryshinnam.myapp.feature.dashboard.component.DashboardLanguageAnalysis
import com.veryshinnam.myapp.feature.dashboard.component.DashboardParentCard

/** 메인 대시보드 화면 */
@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    onCharacterNavigate: (Long) -> Unit,
    cardCorner: Dp = 16.dp,
    cardColor: Color = Color.White,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = Bold),
    borderWidth: Dp = 4.dp,
    borderCorner: Dp = 16.dp,
    borderColor: Color = colorResource(R.color.deep_green),
    vm: DashboardViewModel = hiltViewModel(),
) {
    // 상태 구독
    val uiState by vm.uiState.collectAsStateWithLifecycle() // 화면 전체 ui

    // 세로 모드 고정
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
    }

    // 뒤로 가기
    BackHandler { onBack() }

    // 대시보드 ui
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
                        message ="대시보드를 불러올 수 없어요: ${state.message}",
                        onRetry = {  }
                    )
                }

                // 조회 성공
                is DashboardUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        // 대시보드 상단
                        UserInfo(
                            modifier = Modifier.fillMaxHeight(.12f),
                            isItem = false, // 아이템 설명 존재
                            animalImage = painterResource(R.drawable.img_fox_cut),
                            animalDescription = "보관함 설명 여우 이미지",
                            cardColor = colorResource(R.color.deep_green),
                            cardText = "\${username}의 최대 관심사는 \${username}와 \${username}야",
                        )

                        Row {
                            // 왼쪽 테마
                            DashBoardStaticsAnalysis(
                                title = "주제",
                                chartStats = state.themeChart,
                                listStats = state.dashboardData.themeStats,
                                modifier = Modifier.weight(1f)
                            )

                            // 오른쪽 배경
                            DashBoardStaticsAnalysis(
                                title = "배경",
                                chartStats = state.backgroundChart,
                                listStats = state.dashboardData.backgroundStats,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // -- 언어 분석
                        DashboardLanguageAnalysis(
                            titleTextStyle = titleTextStyle,
                            modifier = Modifier
                        )

                        // 부모 조언 분석
                        DashboardParentCard(
                            username = "username",
                            advice = state.dashboardData.parentAdvice,
                            titleTextStyle = titleTextStyle,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

/** 대시보드 내용
@Composable
fun DashboardContent(
    data: DashboardResult,
    onCharacterClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        // 1. 관심사 통계 (도넛 차트)
        InterestSection(
            themeStats = data.themeStats,
            backgroundStats = data.backgroundStats
        )

        Spacer(Modifier.height(32.dp))

        // 2. 스토리에서 학습한 언어 + 정서 분석
        Text("📘 스토리 언어 학습 & 정서 분석", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        // storyId 기준 매칭 (동화와 언어/감정이 연결되는 구조)
        val stories = data.languageStats.mapNotNull { lang ->
            val emo = data.emotionsStats.find { it.storyId == lang.storyId }
            emo?.let { lang to emo }
        }

        StoryLearningSection(stories) { lang, emotion ->
            onCharacterClick(lang.storyId)
        }

        Spacer(Modifier.height(32.dp))

        // 3. 부모 조언
        Text("💡 부모 조언", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        ParentAdviceSection(data.parentAdvice)
    }

}

/** 부모 조언 섹션 */
@Composable
fun ParentAdviceSection(advice: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.lemon_yellow)
        ),
        border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(
            text = advice,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}*/