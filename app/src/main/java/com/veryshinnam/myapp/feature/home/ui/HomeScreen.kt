package com.veryshinnam.myapp.feature.home.ui

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.feature.home.component.HomeBottomButtons
import com.veryshinnam.myapp.feature.home.component.HomeFavoriteCarousel
import com.veryshinnam.myapp.common.component.UserItem
import com.veryshinnam.myapp.common.component.WarningSimpleSheet
import com.veryshinnam.myapp.core.orientation.OrientationManager
import com.veryshinnam.myapp.feature.admin.ui.AdminStoryScreen
import com.veryshinnam.myapp.feature.attendance.component.AttendanceReward

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
    bottomPadding: Dp = 10.dp,
    cardPadding: Dp = 20.dp, // 텍스트 양옆 패딩
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    settingsTextStyle: TextStyle =  MaterialTheme.typography.labelSmall,
    vm: HomeViewModel =  hiltViewModel(),
    navController: NavController
) {
    // 홈 화면 상태 관리
    val uiState by vm.homeUiState.collectAsStateWithLifecycle()
    val isNewUser by vm.isNewUser.collectAsStateWithLifecycle() // 신규 유저 여부
    val isAdmin by vm.isAdmin.collectAsStateWithLifecycle()     // 관리자 여부

    var isSimpleWarning by remember { mutableStateOf(false) } // 단순 경고창

    // 관리자 여부 확인
    LaunchedEffect(Unit) {
        vm.checkAdminStatus()
    }

    if (isAdmin == true) {
        AdminStoryScreen(navController = navController)
        return
    }

    if (isAdmin == null) {
        // 관리자 여부 로딩 중일 때 로딩 표시
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.main_orange),
                trackColor = Color.Gray.copy(alpha = 0.5f),
                strokeWidth = 4.dp
            )
        }
        return
    }

    // 세로 모드 고정
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
    }

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
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                            // 유저 정보 - 생성한 캐릭터 수, 포인트
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.12f) // 화면의 40% 고정
                            ) {
                                // 다람쥐 이미지
                                Image(
                                    painter = painterResource(R.drawable.img_home_squirrel),
                                    contentDescription = "다람쥐 이미지",
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .align(Alignment.BottomStart)  // start 정렬
                                        .padding(start = 26.dp),
                                    contentScale = ContentScale.Fit
                                )

                                // 나침반 수
                                UserItem(
                                    painter = painterResource(R.drawable.img_dotory),
                                    contentDescription = "모은 나침반 수",
                                    value = "$points",
                                    color = colorResource(R.color.main_orange),
                                    modifier = Modifier
                                        .padding(bottom = bottomPadding)
                                        .align(Alignment.BottomCenter)
                                        .fillMaxWidth(0.3f)
                                        .fillMaxHeight(0.6f)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                                    .border(
                                        4.dp,
                                        colorResource(R.color.main_orange),
                                        RoundedCornerShape(16.dp))
                                    .padding(all = cardPadding),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                    // 닉네임 + 환경설정 버튼
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight(),
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "반가워요 ${username}!",
                                            style = textStyle.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .height(IntrinsicSize.Min)
                                                .clickable(onClick = onSettingsClick)
                                        ) {
                                            Text(
                                                text = "환경설정",
                                                style = settingsTextStyle.copy(
                                                    color = colorResource(id = R.color.main_orange)
                                                )
                                            )

                                            Icon(
                                                imageVector = Icons.Default.Settings,
                                                contentDescription = "환경설정 아이콘",
                                                tint = colorResource(id = R.color.main_orange),
                                                modifier = Modifier
                                                    .padding(start = 2.dp)
                                                    .fillMaxHeight()
                                            )
                                        }
                                    }

                                    // 랜덤 메시지
                                    Text(
//                                    modifier = Modifier.padding(top = 2.dp),
                                        text = state.randomMessage,
                                        style = textStyle.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f, fill = true)
                        ) {
                            // 캐러셀 (남은 공간 중 대부분)
                            HomeFavoriteCarousel(
                                modifier = Modifier
                                    .fillMaxWidth()
                                .weight(8f),
                                characters = favorites,
                                lastSelectedId = state.lastSelectedCharacter,
                                onCharacterClick = onCharacterClick
                            )

                            // 바텀 버튼 (남은 공간의 1.5 비율)
                            HomeBottomButtons(
                                onDashboardClick = onDashboardClick,
                                onCreationClick = {
                                    if (points > 0) onCreationClick()
                                    else isSimpleWarning = true
                                },
                                onCollectionClick = onCollectionClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(2f)
                                    .padding(vertical = 8.dp)
                            )
                        }
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

    if (isSimpleWarning) {
        WarningSimpleSheet(
            warningText = "포인트가 부족해요.\n출석을 통해 포인트를 모아 볼까요?",
            onDismiss = { isSimpleWarning = false}
        )
    }

    // 신규 유저 보상 화면
    if (isNewUser) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    enabled = true,
                    indication = null, // 터치 효과 제거
                    interactionSource = remember { MutableInteractionSource() }
                ) { }
        )

        AttendanceReward(
            painter = painterResource(R.drawable.img_dotory_shining),
            text = "신규 유저 보상\n나침반 5개",
            onReceiveClick = {
//                vm.fetchAttendanceReward() // 보상 업데이트
                vm.updateNewUser()
            },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
        )
    }
}