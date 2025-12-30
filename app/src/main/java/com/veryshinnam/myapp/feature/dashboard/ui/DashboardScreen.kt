package com.veryshinnam.myapp.feature.dashboard.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.veryshinnam.myapp.feature.dashboard.component.DashBoardStaticsCard
import com.veryshinnam.myapp.feature.dashboard.component.DashboardStoryCard
import com.veryshinnam.myapp.feature.dashboard.component.DashboardParentCard

/** 메인 대시보드 화면 */
@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    onCharacter: (Long) -> Unit,
    spacer: Dp = 6.dp,
    horizontalPadding: Dp = 16.dp,
    vm: DashboardViewModel = hiltViewModel(),
) {
    // 상태 구독
    val uiState by vm.uiState.collectAsStateWithLifecycle() // 화면 전체 ui

    // ui 변수
    val scrollState = rememberScrollState()
    val logoAlpha by animateFloatAsState(
        targetValue = if (scrollState.value > 8) 0f else 1f,
        label = "logoAlpha"
    )

    // 세로 모드 고정
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
    }

    // 뒤로 가기
    BackHandler { onBack() }

    // 대시보드 ui
    Box {
        // 로고 + 백버튼 ui
        Column (
            modifier = Modifier
                .fillMaxSize()
                .zIndex(20f)
        ) {
            Column(
                modifier = Modifier.alpha(logoAlpha)
            ) {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                LogoBar(onLogoClick = onLogoClick)
            }

            // 뒤로 가기 버튼
            BackButton(
                modifier = Modifier
                    .align(Alignment.Start)
                    .zIndex(1f),
                onBackClick = onBack
            )
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background_yellow))
        ) {
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
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(spacer)
                    ) {
                        // 대시보드 상단
                        UserInfo(
                            modifier = Modifier.fillMaxHeight(.12f).padding(horizontal = horizontalPadding),
                            isItem = false, // 아이템 설명 존재
                            animalImage = painterResource(R.drawable.img_fox_cut),
                            animalDescription = "보관함 설명 여우 이미지",
                            cardColor = colorResource(R.color.deep_green),
                            cardText = "\${username}의 최대 관심사는 \${username}와 \${username}야",
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth().padding(horizontal = horizontalPadding),
                            horizontalArrangement = Arrangement.spacedBy(spacer)
                        ) {
                            // 왼쪽 테마
                            DashBoardStaticsCard(
                                title = "주제",
                                chartStats = state.themeChart,
                                listStats = state.themeList,
                                modifier = Modifier.weight(1f)
                            )

                            // 오른쪽 배경
                            DashBoardStaticsCard(
                                title = "배경",
                                chartStats = state.backgroundChart,
                                listStats = state.backgroundList,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // 섹션 2: 스토리별 언어 + 단어 리스트 + 감정 분석
                        // 이동 버튼
                        Box{
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .matchParentSize()
                                    .padding(horizontal = 4.dp)
                                    .zIndex(10f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // ▶ 다음 버튼
                                Button(
                                    onClick = { vm.prevStory() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(R.color.main_orange).copy(0.5f),
                                        contentColor = Color.White
                                    ),
                                    border = BorderStroke(
                                        2.dp,
                                        colorResource(R.color.main_orange)
                                    ),
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Text(
                                        text = "◀",
                                    )
                                }

                                // ▶ 다음 버튼
                                Button(
                                    onClick = { vm.nextStory() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(R.color.main_orange).copy(0.5f),
                                        contentColor = Color.White
                                    ),
                                    border = BorderStroke(
                                        2.dp,
                                        colorResource(R.color.main_orange)
                                    ),
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Text(
                                        text = "▶",
                                    )
                                }
                            }

                            DashboardStoryCard(
                                story = state.storyAnalysis[state.storyIndex],
                                onStoryClick = { storyId ->
                                    onCharacter(storyId)
                                },
                                modifier = Modifier
                            )
                        }

                        // 섹션 3: 부모 조언 분석
                        DashboardParentCard(
                            username = "username",
                            advice = state.advice,
                            modifier = Modifier
                        )

                        // 네비게이션바 만큼 여백
                        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                    }
                }
            }
        }
    }
}