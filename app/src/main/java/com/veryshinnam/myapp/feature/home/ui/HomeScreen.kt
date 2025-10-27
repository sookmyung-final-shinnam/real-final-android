package com.veryshinnam.myapp.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.feature.home.component.HomeBottomButtons
import com.veryshinnam.myapp.feature.home.component.HomeFavoriteCarousel
import com.veryshinnam.myapp.feature.home.component.HomeUserInfo

/**
 * 홈 화면
 * : 유저 정보와 즐찾 캐릭터 정보 조회
 *
 * - onSettingsClick: 버튼 클릭 시, 환경설정 화면(SettingsScreen)으로 이동
 * - onAttendanceClick: 버튼 클릭 시, 출석체크 화면(AttendanceScreen)으로 이동
 * - onCharacterClick: 버튼 클릭 시, 캐릭터 상세 화면(CharacterScreen)으로 이동
 * - onDashboardClick: 버튼 클릭 시, 대시보드 화면(DashboardScreen)으로 이동
 * - onCreationClick: 버튼 클릭 시, 캐릭터 및 동화 생성 플로우 진행
 * - onCollectionClick: 버튼 클릭 시, 보관함 화면(CollectionScreen)으로 이동
 */
@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    onAttendanceClick: () -> Unit,
    onCharacterClick: (Long) -> Unit,   // 캐릭터 아이디 전달
    onDashboardClick: () -> Unit,       // 바텀 버튼
    onCreationClick: () -> Unit,        // 바텀 버튼
    onCollectionClick: () -> Unit,      // 바텀 버튼
    horizontalPadding: Dp = 16.dp,
    vm: HomeViewModel =  hiltViewModel()
) {
    // 홈 화면 상태 관리
    val uiState by vm.homeUiState.collectAsStateWithLifecycle()

    // 동화책 상세 > 홈 화면: 세로 모드
//    val context = LocalContext.current
//    SideEffect { (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }

    // HomeScreen 진입할 때마다 실행
    LaunchedEffect(Unit) {
        vm.reload()        // 홈 데이터 다시 불러오기
        vm.changeMessage() // 랜덤 메시지도 갱신
    }

    // 홈 UI
    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = {
            // 상태바 만큼 여백 & 상단 로고
            Column {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                LogoBar()
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
            when (val state = uiState) {
                // 조회 로딩
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.main_orange), // 주황색
                        trackColor = Color.Gray.copy(alpha = 0.5f),
                        strokeWidth = 4.dp
                    )
                }

                // 조회 오류
                is HomeUiState.Error -> {
                    LoadErrorView(
                        message = state.message,
                        onRetry = { vm.reload() }
                    )
                }

                // 조회 성공
                is HomeUiState.Success -> {
                    val (username, points, favorites) = state.homeData

                    Column {
                        // 유저 정보 (스탬프 및 문구 & 환경설정)
                        HomeUserInfo(
                            username = username,
                            stamps = points,
                            message = state.randomMessage,
                            onSettingsClick = onSettingsClick,
                            modifier = Modifier
                                .padding(horizontal = horizontalPadding)
                                .weight(0.28f)
                                .fillMaxWidth()
                        )

                        // 즐찾 캐릭터 캐러셀
                        HomeFavoriteCarousel(
                            modifier = Modifier
                                .weight(0.57f)
                                .fillMaxWidth(),
                            characters = favorites,
                            lastSelectedId = state.lastSelectedCharacter,
                            onCharacterClick = { id ->
                                onCharacterClick(id)
                            }
                        )

                        // 바텀 버튼 (대시보드, 생성, 보관함)
                        HomeBottomButtons(
                            onDashboardClick,
                            onCreationClick,
                            onCollectionClick,
                            modifier = Modifier
                                .padding(horizontal = horizontalPadding)
                                .weight(0.15f)
                                .fillMaxWidth()
                        )
                    }

                    // 출석체크 버튼
                    Image(
                        painter = painterResource(R.drawable.img_home_check),
                        contentDescription = "출석체크",
                        modifier = Modifier
                            .fillMaxHeight(0.08f)
                            .padding(horizontal = 16.dp)
                            .align(Alignment.TopEnd)
                            .clickable(
                                indication = LocalIndication.current,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onAttendanceClick() }
                    )
                }
            }
        }
    }
}