package com.veryshinnam.myapp.feature.dashboard.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.EmptyView
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.UserInfo
import com.veryshinnam.myapp.common.model.ManualState
import com.veryshinnam.myapp.core.orientation.OrientationManager
import com.veryshinnam.myapp.feature.dashboard.component.DashBoardStaticsCard
import com.veryshinnam.myapp.feature.dashboard.component.DashboardHelpButton
import com.veryshinnam.myapp.feature.dashboard.component.DashboardStoryCard
import com.veryshinnam.myapp.feature.dashboard.component.DashboardParentCard

/** 메인 대시보드 화면 */
@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    goToCharacter: (Long) -> Unit,
    goToCreation: () -> Unit,
    goToNextManual: () -> Unit,
    spacer: Dp = 6.dp,
    horizontalPadding: Dp = 16.dp,
    greenColor: Color = colorResource(R.color.deep_green),
    vm: DashboardViewModel = hiltViewModel(),
) {
    // 화면 밀도 정보
    val density = LocalDensity.current

    // -- 상태 구독
    val uiState by vm.uiState.collectAsStateWithLifecycle() // 화면 전체 ui
    val isEmpty by vm.isEmpty.collectAsStateWithLifecycle()
    val manualState by vm.manualState.collectAsStateWithLifecycle()
    val manualStep by vm.manualStep.collectAsStateWithLifecycle()
    val manualMessage by vm.manualMessage.collectAsStateWithLifecycle()

    // -- ui 변수
    val scrollState = rememberScrollState() // 대시보드 스크롤
    val logoAlpha by animateFloatAsState(
        targetValue = if (scrollState.value > 8) 0f else 1f,
        label = "logoAlpha"
    )   // 로고 애니메이션
    var logoHeight by remember { mutableStateOf(0f) }
    var bBTop by remember { mutableStateOf(0.dp) }   // 백버튼 높이 위치


    // 매뉴얼 > 강조할 좌표
    val isManual = manualState != ManualState.NONE
    var tHelpRect by remember { mutableStateOf<Rect?>(null) }   // 테마 도움말 위치
    var bHelpRect by remember { mutableStateOf<Rect?>(null) }   // 배경 도움말 위치
    var sHelpRect by remember { mutableStateOf<Rect?>(null) }   // 동화 도움말 위치
    val onStopManual: () -> Unit = { vm.hideManual(); onLogoClick() }

    // -- 세로 모드 고정
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
    }

    // -- 초기화 설정
    LaunchedEffect(manualState) {
        when (manualState) {
            ManualState.NONE -> {
                vm.fetchDashboard()
            } // 실제 데이터
            ManualState.START -> {
                vm.startManual()
            } // 매뉴얼 시작일 때만 더미 데이터
            else -> {}
        }
    }

    LaunchedEffect(manualStep) {
        if (manualStep == vm.manuals.size) {
            goToNextManual()
        }
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
                .alpha(logoAlpha)
        ) {
            Column {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                LogoBar(
                    onLogoClick = onLogoClick,
                    modifier = Modifier.onGloballyPositioned {
                        logoHeight = it.size.height.toFloat()
                    }
                )
            }

            Box {
                // 뒤로 가기 버튼
                BackButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .zIndex(1f)
                        .onGloballyPositioned {
                            // px > dp
                            bBTop = with(density) { it.boundsInRoot().top.toDp() }
                        },
                    onBackClick = onBack
                )
            }

            if (isEmpty) {
                EmptyView(
                    emptyText = "대시보드가 비어 있네요!\n같이 대시보드를 채우러 가볼까요?",
                    buttonText = "동화 만들러 가기",
                    onButtonClick = goToCreation,
                    modifier = Modifier.fillMaxSize()
                )
            }
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
                            // 매뉴얼일 때 스크롤 제한
                            .verticalScroll(scrollState, enabled = !isManual)
                            .then(
                                // 매뉴얼일 때만 터치 가로채기
                                when (manualState) {
                                    ManualState.START -> Modifier.pointerInput(Unit) {
                                        detectTapGestures { vm.nextManual() }
                                    }
                                    ManualState.STOP -> Modifier.pointerInput(Unit) {
                                        detectTapGestures { onStopManual() }
                                    }
                                    else -> Modifier
                                }
                            )
                    ) {
                        // 섹션 0: 대시보드 상단
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .then(
                                    // 매뉴얼일 때만 어두운 배경
                                    if (isManual)
                                        Modifier.background(Color.Black.copy(alpha = 0.5f))
                                    else Modifier
                                ),
                        ) {
                            // 매뉴얼일 때
                            if (isManual) {
                                Text(
                                    text = "그만 들을래요.",
                                    color = colorResource(R.color.main_orange),
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(30.dp)
                                        .clickable {
                                            when (manualState) {
                                                ManualState.START -> vm.stopManual()
                                                ManualState.STOP -> vm.hideManual()
                                                else -> {}
                                            }
                                        }
                                        .zIndex(11f)
                                )
                            }

                            UserInfo(
                                modifier = Modifier
                                    .padding(
                                        top = bBTop,
                                        start = horizontalPadding, end = horizontalPadding),
                                isItem = false, // 아이템 설명 존재
                                animalImage = painterResource(R.drawable.img_fox_cut),
                                animalDescription = "보관함 설명 여우 이미지",
                                cardColor = greenColor,
                                cardText =  if (isManual) manualMessage
                                    else if (isEmpty) "아직 ${state.username}의\n최대 관심사를 찾을 수 없어요!"
                                    else "${state.username}의 최대 관심사예요! \n{${state.maxTheme}}, {${state.maxBackground}}",
                                spanText = "최대 관심사",
                                spanColor = greenColor
                            )
                        }

                         if (!isEmpty) {
                            // 섹션 1: 테마 + 배경 통계 분석
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(
                                        // 매뉴얼일 때, 1, 2, 3 강조
                                        if (isManual && 0 < manualStep && manualStep < 4)
                                            Modifier.background(Color.Black.copy(alpha = 0.5f))
                                        else Modifier
                                    )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = spacer, start = horizontalPadding, end = horizontalPadding),
                                    horizontalArrangement = Arrangement.spacedBy(spacer)
                                ) {
                                    // 왼쪽 테마
                                    DashBoardStaticsCard(
                                        title = "주제",
                                        chartStats = state.themeChart,
                                        listStats = state.themeList,
                                        onHelpRect = {
                                            if (manualState == ManualState.START && tHelpRect == null) {
                                                tHelpRect = it }},
                                        modifier = Modifier.weight(1f)
                                    )

                                    // 오른쪽 배경
                                    DashBoardStaticsCard(
                                        title = "배경",
                                        chartStats = state.backgroundChart,
                                        listStats = state.backgroundList,
                                        onHelpRect = {
                                            if (manualState == ManualState.START && bHelpRect == null) {
                                                bHelpRect = it }},
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                // 매뉴얼일 때, 0, 3이상만 어두운 배경
                                if (isManual && (manualStep == 0 || manualStep >= 4)) {
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .background(Color.Black.copy(alpha = 0.5f))
                                            .zIndex(1f)
                                    )
                                }
                            }

                            // 섹션 2: 스토리별 언어 + 단어 리스트 + 감정 분석
                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .then(
                                        // 매뉴얼일 때, 1, 2, 3 강조
                                        if (isManual && 0 < manualStep && manualStep < 4)
                                            Modifier.background(Color.Black.copy(alpha = 0.5f))
                                        else Modifier
                                    )
                            ){
                                // 이동 버튼
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
                                        goToCharacter(storyId)
                                    },
                                    onHelpRect = {
                                        if (manualState == ManualState.START && sHelpRect == null) {
                                            sHelpRect = it }},
                                    modifier = Modifier
                                )

                                // 매뉴얼일 때, 1, 2 강조
                                if (isManual && (manualStep == 0 || manualStep >= 4)) {
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .background(Color.Black.copy(alpha = 0.5f))
                                            .zIndex(1f)
                                    )
                                }
                            }

                            // 섹션 3: 부모 조언 분석
                            DashboardParentCard(
                                username = state.username,
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

    // 다시 그릴 매뉴얼 강조 요소
    if (isManual && manualStep == 4) {
        tHelpRect?.let { rect ->
            DashboardHelpButton(
                onPress = {  },
                modifier = Modifier
                    .absoluteOffset(
                        x = with(density) { rect.left.toDp() },
                        y = with(density) { (rect.top + logoHeight).toDp()}
                    )
                    .zIndex(2f)
            )
        }

        bHelpRect?.let { rect ->
            DashboardHelpButton(
                onPress = {  },
                modifier = Modifier
                    .absoluteOffset(
                        x = with(density) { rect.left.toDp() },
                        y = with(density) { (rect.top + logoHeight).toDp()}
                    )
                    .zIndex(2f)
            )
        }

        sHelpRect?.let { rect ->
            DashboardHelpButton(
                onPress = {  },
                modifier = Modifier
                    .absoluteOffset(
                        x = with(density) { rect.left.toDp() },
                        y = with(density) { (rect.top + logoHeight).toDp()}
                    )
                    .zIndex(2f)
            )
        }
    }
}