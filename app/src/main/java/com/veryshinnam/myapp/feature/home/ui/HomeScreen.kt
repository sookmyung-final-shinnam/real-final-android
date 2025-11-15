package com.veryshinnam.myapp.feature.home.ui

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.CircleButton
import com.veryshinnam.myapp.common.component.TargetImage
import com.veryshinnam.myapp.common.component.TargetMessage
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.feature.home.component.HomeBottomButtons
import com.veryshinnam.myapp.feature.home.component.HomeFavoriteCarousel
import com.veryshinnam.myapp.common.component.UserItem
import com.veryshinnam.myapp.common.component.WarningSheet
import com.veryshinnam.myapp.common.model.ManualState
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
    messageBorder: Dp = 4.dp,
    messageCorner: Dp = 16.dp,
    messagePadding: Dp = 20.dp,
    horizontalPadding: Dp = 16.dp,
    bottomPadding: Dp = 10.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold),
    settingsTextStyle: TextStyle =  MaterialTheme.typography.labelSmall.copy(color = colorResource(id = R.color.main_orange)),
    vm: HomeViewModel =  hiltViewModel(),
    navController: NavController
) {
    // 상태 구독
    val uiState by vm.homeUiState.collectAsStateWithLifecycle() // 화면 전체 ui
    val isNewUser by vm.isNewUser.collectAsStateWithLifecycle() // 신규 유저 여부
    val isAdmin by vm.isAdmin.collectAsStateWithLifecycle()     // 관리자 여부
    val warningState by vm.warningState.collectAsStateWithLifecycle() // 단순 경고
    val manualState by vm.manualState.collectAsStateWithLifecycle()
    val manualMessage by vm.manualMessage.collectAsStateWithLifecycle()
    val manualStep by vm.manualStep.collectAsStateWithLifecycle()
    val username by vm.username.collectAsStateWithLifecycle()
    val userManualMessage = manualMessage.replace("{username}", username)

    // 매뉴얼 > 강조할 좌표
    var squirrelRect by remember { mutableStateOf<Rect?>(null) } // 다람쥐 이미지
    var messageRect by remember { mutableStateOf<Rect?>(null) }  // 메세지 박스
    var creationRect by remember { mutableStateOf<Rect?>(null) }  // 생성
    var collectionRect by remember { mutableStateOf<Rect?>(null) }  // 보관함
    var dashboardRect by remember { mutableStateOf<Rect?>(null) }  // 대시보드
    var attendanceRect by remember { mutableStateOf<Rect?>(null) }  // 출석체크

    // 세로 모드 고정
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
    }

    // HomeScreen 진입 시 한번만  실행
    LaunchedEffect(Unit) {
        vm.checkAdminStatus() // 관리자 여부 확인
        vm.reload()        // 홈 데이터 다시 불러오기
        vm.changeMessage() // 랜덤 메시지도 갱신
    }

    LaunchedEffect(manualStep) {
        if (manualStep == vm.manuals.size) {
            onCreationClick()
        }
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
                                        .padding(start = 26.dp)
                                        .onGloballyPositioned {
                                            if ((manualState == ManualState.START || manualState == ManualState.REQUEST) && squirrelRect == null) {
                                                squirrelRect = it.boundsInRoot()
                                            }
                                        },
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

                            // 메시지 박스
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .background(Color.White, shape = RoundedCornerShape(messageCorner))
                                    .border(
                                        messageBorder,
                                        colorResource(R.color.main_orange),
                                        RoundedCornerShape(messageCorner))
                                    .onGloballyPositioned {
                                        if ((manualState == ManualState.START || manualState == ManualState.REQUEST) && messageRect == null) {
                                            messageRect = it.boundsInRoot()
                                        }
                                    }
                                    .padding(all = messagePadding),
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
                                            style = textStyle
                                        )

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .height(IntrinsicSize.Min)
                                                .clickable(onClick = onSettingsClick)
                                        ) {
                                            Text(
                                                text = "환경설정",
                                                style = settingsTextStyle
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
                                        text = state.randomMessage,
                                        style = textStyle
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
                                    else vm.showWarning("포인트가 부족해요.\n출석을 통해 포인트를 모아 볼까요?")
                                },
                                onCollectionClick = onCollectionClick,
                                onDashboardRect = { rect ->
                                    if ((manualState == ManualState.START || manualState == ManualState.REQUEST) && dashboardRect == null) {
                                        dashboardRect = rect
                                    }
                                },
                                onCreationRect = { rect ->
                                    if ((manualState == ManualState.START || manualState == ManualState.REQUEST) && creationRect == null) {
                                        creationRect = rect
                                    }
                                },
                                onCollectionRect = { rect ->
                                    if ((manualState == ManualState.START || manualState == ManualState.REQUEST) && collectionRect == null) {
                                        collectionRect = rect
                                    }
                                },
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
                            .onGloballyPositioned {
                                if ((manualState == ManualState.START || manualState == ManualState.REQUEST) && attendanceRect == null) {
                                    attendanceRect = it.boundsInRoot()
                                }
                            }
                            .clickable(
                                indication = LocalIndication.current,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onAttendanceClick() }
                    )
                }
            }
        }
    }

    if (warningState.isVisible) {
        WarningSheet(
            warningText = warningState.warningText,
            onDismiss = { vm.hideWarning() }
        )
    }

    // 신규 유저 보상창
    if (isNewUser) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
                .background(Color.Black.copy(alpha = 0.5f))
                .pointerInput(Unit) {},
        )

        AttendanceReward(
            painter = painterResource(R.drawable.img_dotory_shining),
            text = "신규 유저 보상\n도토리 5개",
            onReceiveClick = {
//                vm.fetchAttendanceReward() // TODO: 보상 업데이트
                vm.updateNewUser()
                vm.showManual()
            },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
        )
    }

    // 매뉴얼 진행
    if (manualState != ManualState.NONE) {

        val pointerInput =
            when (manualState) {
                ManualState.REQUEST ->
                    Modifier.pointerInput(Unit) {}

                ManualState.START ->
                    Modifier.pointerInput(Unit) {
                        detectTapGestures { vm.nextManual() }
                    }

                else ->
                    Modifier.pointerInput(Unit) {
                        detectTapGestures { vm.hideManual() }
                    }
            }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
                .background(Color.Black.copy(alpha = 0.5f))
                .then(pointerInput)
        ) {
            squirrelRect?.let { rect ->
                TargetImage(
                    rect,
                    painterResource(R.drawable.img_home_squirrel)
                )  // zIndex 20
            }

            messageRect?.let { rect ->
                TargetMessage(
                    rect = rect,
                    message = userManualMessage
                )
            }

            if (manualState == ManualState.REQUEST) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Spacer(modifier = Modifier.fillMaxHeight(0.7f))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        Arrangement.SpaceBetween
                    ) {
                        CircleButton(
                            modifier = Modifier.weight(7f),
                            onClick = { vm.startManual() },
                            text = "좋아요!"
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        CircleButton(
                            modifier = Modifier.weight(7f),
                            onClick = { vm.stopManual() },
                            text = "괜찮아요."
                        )
                    }
                }
            }

            if (manualState == ManualState.START) {
                when (manualStep) {
                    0 -> {}
                    1 -> creationRect?.let { TargetImage(it, painterResource(R.drawable.img_bottom_creation)) }
                    2 -> collectionRect?.let { TargetImage(it, painterResource(R.drawable.img_bottom_collection)) }
                    3 -> dashboardRect?.let { TargetImage(it, painterResource(R.drawable.img_bottom_dashboard)) }
                    4 -> attendanceRect?.let { TargetImage(it, painterResource(R.drawable.img_home_check)) }
                }
            }
        }
    }
}