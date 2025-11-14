package com.veryshinnam.myapp.feature.settings.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.WarningConfirmSheet
import com.veryshinnam.myapp.common.component.WarningSheet
import com.veryshinnam.myapp.core.orientation.OrientationManager
import kotlinx.coroutines.delay

/**
 * 설정 화면
 * : 로그아웃, 회원탈퇴, 앱 사용 매뉴얼 제공
 *
 * - onBack: 단순 뒤로 가기
 * - onLogoClick: 버튼 클릭 시, 홈 화면(HomeScreen)으로 이동
 * - horizontalPadding: 화면 가로 패딩
 * - buttonTextStyle: 버튼 공통 텍스트 스타일
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 20.dp,
    buttonTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold),
    footerTextStyle: TextStyle =  MaterialTheme.typography.labelSmall.copy(color = colorResource(id = R.color.main_orange), textAlign = Center),
    vm: SettingsViewModel = hiltViewModel()
) {
    // 상태 구독
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val confirmWarning by vm.confirmWarning.collectAsStateWithLifecycle()
    val warningState by vm.warning.collectAsStateWithLifecycle()

    // 세로 모드 고정
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
    }

    // 에러 처리
    LaunchedEffect(uiState) {
        if (uiState is SettingsUiState.Error) {
            vm.showWarning("요청에 실패하셨습니다.")
        }
    }

    // 뒤로 가기
    BackHandler { onBack() }

    // 설정 화면 UI
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
        // --- 설정 화면 UI
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {
            // 뒤로 가기 버튼
            BackButton(
                modifier = Modifier.zIndex(1f),
                onBackClick = onBack
            )

            // --- 버튼 영역
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
                    .padding(horizontalPadding),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(verticalPadding),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    // 로그아웃 버튼
                    Button(
                        onClick = {
                            vm.showConfirmWarning(
                                warningText = "정말 로그아웃 하시겠어요?",
                                confirmText = "로그아웃 하기",
                                onConfirm = {
                                    vm.logout()
                                    vm.showWarning("로그아웃이 완료되었습니다.")
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.main_orange),
                            contentColor = Color.White
                        ),
                        shape = CircleShape,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "로그아웃",
                            style = buttonTextStyle,
                            modifier = Modifier.padding(vertical = horizontalPadding / 2)
                        )
                    }

                    // 회원 탈퇴 버튼
                    Button(
                        onClick = {
                            vm.showConfirmWarning(
                                warningText =
                                    "정말 탈퇴하시겠어요?\n\n" +
                                            "동화/캐릭터 정보를 제외한 사용자 관련 모든 정보가 삭제되며,\n" +
                                            "탈퇴 후 24시간 내에 재로그인시\n회원 탈퇴가 취소됩니다.",
                                confirmText = "회원 탈퇴 하기",
                                onConfirm = {
                                    vm.withdraw()
                                    vm.showWarning("회원 탈퇴가 완료되었습니다.")
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.main_orange),
                            contentColor = Color.White
                        ),
                        shape = CircleShape,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "회원 탈퇴",
                            style = buttonTextStyle,
                            modifier = Modifier.padding(vertical = horizontalPadding / 2)
                        )
                    }

                    // 앱 사용 매뉴얼 버튼
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.main_orange),
                            contentColor = Color.White
                        ),
                        shape = CircleShape,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "사용 설명 다시 보기",
                            style = buttonTextStyle,
                            modifier = Modifier.padding(vertical = horizontalPadding / 2)
                        )
                    }
                }
            }

            // --- 다람쥐 이미지
            Image(
                painter = painterResource(R.drawable.img_llm_question),
                contentDescription = "다람쥐 이미지",
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f) // 남은 공간 차지
                    .offset(y = (-50).dp)
                    .align(Alignment.CenterHorizontally), // 가로 정렬
                contentScale = ContentScale.Fit
            )

            Text(
                text = "앱 사용 중 불편한 점을 발견하셨다면\n언제든지 저희에게 연락주세요!\n\nveryshinnam@gmail.com",
                style = footerTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = horizontalPadding / 2)
            )
        }
    }

    // 확인 버튼이 있는 경고창
    if (confirmWarning.isVisible) {
        WarningConfirmSheet(
            warningText = confirmWarning.warningText,
            confirmText = confirmWarning.confirmText,
            onDismiss = { vm.hideConfirmWarning() },
            onConfirm = {
                confirmWarning.onConfirm()
                vm.hideConfirmWarning()
            }
        )
    }

    // 단순 경고창
    if (warningState.isVisible) {

        // 3초 카운트다운
        var countdown by remember { mutableIntStateOf(3) }
        LaunchedEffect(warningState.isVisible) {
            if (warningState.isVisible) {
                for (i in 3 downTo 1) {
                    countdown = i
                    delay(1000)
                }
                vm.hideWarning()
            }
        }

        // 검은색 오버레이 + 경고창
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        ){
            WarningSheet(
                warningText = buildString {
                    append(warningState.warningText)
                    append("\n${countdown}초 뒤 시작 화면으로 이동합니다.")
                },
                onDismiss = { },    // 닫기 방지
                dismissible = false // 시트 닫힘 방지
            )
        }
    }
}