package com.veryshinnam.myapp.feature.attendance.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import com.veryshinnam.myapp.core.orientation.OrientationManager
import com.veryshinnam.myapp.feature.attendance.component.AttendanceCalender
import com.veryshinnam.myapp.feature.attendance.component.AttendanceReward
import org.threeten.bp.YearMonth

@Composable
fun AttendanceScreen(
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    spacerPadding: Dp = 10.dp,
    horizontalPadding: Dp = 16.dp,
    vm: AttendanceViewModel = hiltViewModel()
) {
    val uiState by vm.attendanceUiState.collectAsStateWithLifecycle()
    val isLoading by vm.isLoading.collectAsStateWithLifecycle()

    var isTodayStamp by remember { mutableStateOf(false) }
    var isExchangeable by remember { mutableStateOf(false) }

    // 세로 모드 고정
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
    }

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
                // 조회 로딩 중
                is AttendanceUiState.Idle -> {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.main_orange), // 주황색
                        trackColor = Color.Gray.copy(alpha = 0.5f),
                        strokeWidth = 4.dp
                    )
                }

                // 조회 오류
                is AttendanceUiState.Error -> {
                    LoadErrorView(
                        message = state.message,
                        onRetry = { }
                    )
                }

                // 조회 성공
                is AttendanceUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = horizontalPadding)
                    ) {
                        // 오늘 출첵 여부 확인
                        LaunchedEffect(state.attendanceData.isTodayAttendance) {
                            isTodayStamp = !state.attendanceData.isTodayAttendance
                        }

                        // 스탬프 수 확인
                        LaunchedEffect(state.attendanceData.stamps, isTodayStamp) {
                            if (!isTodayStamp && state.attendanceData.stamps == 10 && !isExchangeable) {
                                vm.exchangeAttendance()
                                isExchangeable = true
                            }
                        }

                        val month = if (state.yearMonth == YearMonth.now()) "이번 달"
                                    else "지난 ${state.yearMonth.monthValue}월"

                        UserInfo(
                            modifier = Modifier,
                            isItem = true, // 아이템 설명 존재
                            itemCount = state.attendanceData.stamps,
                            itemImage =  painterResource(R.drawable.img_stamp),
                            itemDescription = "모은 스탬프 수",
                            animalImage = painterResource(R.drawable.img_pig_cut),
                            animalDescription = "출석체크 설명 돼지 이미지",
                            cardColor = colorResource(R.color.deep_pink),
                            cardText = "${month}은 총 ${state.attendanceData.attendanceCounts}번 출석했어요!\n" +
                                    "도장 10 개당 나침반 1 개라는 걸 잊지 마세요~!",
                            spanText = "${state.attendanceData.attendanceCounts}번",
                            spanColor = colorResource(R.color.light_pink)
                        )

                        Spacer(Modifier.height(spacerPadding))

                        // 출첵 달력
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .zIndex(2f),
                            contentAlignment = Alignment.Center
                        ) {
                            AttendanceCalender(
                                attendances = state.attendanceData.attendanceDates,
                                yearMonth = state.yearMonth,
                                lastExchangeDate = state.attendanceData.lastExchangeDate,
                                onPrevMonth = { vm.fetchPreviousMonth() },
                                onNextMonth = { vm.fetchNextMonth() },
                                modifier = Modifier.fillMaxSize(),
                            )

                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = colorResource(id = R.color.deep_pink),
                                    trackColor = Color.Gray.copy(alpha = 0.5f),
                                    strokeWidth = 4.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // 오늘 출첵 화면
    if (isTodayStamp) {
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
            painter = painterResource(R.drawable.img_stamp_shining_blue),
            text = "오늘의\n출석 체크 완료",
            buttonText = "확인",
            onReceiveClick = {
                vm.addAttendance()
                isTodayStamp = false
            },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(3f),
        )
    }

    // 출첵 보상 완료 화면
    if (isExchangeable) {
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
            painter = painterResource(R.drawable.img_compass_shining),
            text = "출석 체크 보상\n나침반 1개",
            onReceiveClick = {
                isExchangeable = false // 닫기
            },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
        )
    }
}