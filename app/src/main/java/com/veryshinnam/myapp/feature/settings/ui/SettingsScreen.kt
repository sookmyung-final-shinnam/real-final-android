package com.veryshinnam.myapp.feature.settings.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.ScreenOrientation
import com.veryshinnam.myapp.common.component.WarningSheet
import com.veryshinnam.myapp.common.component.WarningSimpleSheet
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onHome: () -> Unit,
    onLogoClick: () -> Unit,
    horizontalPadding: Dp = 16.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
    vm: SettingsViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    var isWarning by remember { mutableStateOf(false) }   // 경고창
    var warningText by remember { mutableStateOf("") }
    var confirmText by remember { mutableStateOf("") }
    var confirmAction by remember { mutableStateOf({}) }
//    var warningStyle by remember { mutableStateOf(TextStyle()) }
//    val titleStyle = MaterialTheme.typography.titleLarge
//    val titleStyle2 =  MaterialTheme.typography.headlineSmall

    var isSimpleWarning by remember { mutableStateOf(false) } // 단순 경고창
    var SimpleWarningText by remember { mutableStateOf("") }

    // 세로 모드
    ScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    LaunchedEffect(uiState) {
        when (uiState) {
            is SettingsUiState.Error -> {
                SimpleWarningText = "요청에 실패하셨습니다"
                isSimpleWarning = true
            }
            else -> Unit
        }
    }

    // 뒤로 가기
    BackHandler { onHome() }

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
                onBackClick = onHome
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
                        .padding(vertical = horizontalPadding * 2, horizontal = horizontalPadding),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    // 로그아웃 버튼
                    Button(
                        onClick = {
                            warningText = "정말 로그아웃 하시겠어요?\n"
                            confirmText = "로그아웃 하기"
                            isWarning = true
//                            warningStyle = titleStyle2
                            confirmAction = {
                                isWarning = false
                                vm.logout() // 로그아웃
                                SimpleWarningText = "로그아웃이 완료되었습니다.\n"
                                isSimpleWarning = true
                            }
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
                            style = textStyle,
                            modifier = Modifier.padding(vertical = horizontalPadding / 2)
                        )
                    }

                    // 회원 탈퇴 버튼
                    Button(
                        onClick = {
                            warningText = "정말 탈퇴하시겠어요?\n" +
                                    "동화/캐릭터 정보를 제외한 사용자 관련 모든 정보가 삭제됩니다.\n" +
                                    "(탈퇴 후 24시간 내에 재로그인시 회원 탈퇴가 취소됩니다.)"
                            confirmText = "회원 탈퇴하기"
                            isWarning = true
//                            warningStyle = titleStyle
                            confirmAction = {
                                isWarning = false
                                vm.withdraw() // 회원 탈퇴
                                SimpleWarningText = "회원 탈퇴가 완료되었습니다.\n"
                                isSimpleWarning = true
                            }
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
                            style = textStyle,
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
                    .weight(1f)
                    .offset(y = (-80).dp)
                    .align(Alignment.CenterHorizontally), // 가로 정렬
                contentScale = ContentScale.Fit
            )
        }

    }

    if (isWarning) {
        WarningSheet(
            warningText  = warningText,
            confirmText = confirmText,
            onDismiss = { isWarning = false },
            onConfirm = { confirmAction() }
        )
    }

    if (isSimpleWarning) {
        var countdown by remember { mutableStateOf(3) }

        // 3초 카운트다운
        LaunchedEffect(isSimpleWarning) {
            if (isSimpleWarning) {
                for (i in 3 downTo 1) {
                    countdown = i
                    delay(1000)
                }
            }
        }

        // 뒤로가기 무시
//        BackHandler(enabled = true) {}

        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(enabled = true, onClick = { }) // 터치 불가
        ){
            WarningSimpleSheet(
                warningText = buildString {
                    append(SimpleWarningText)
                    append("\n")
                    append("${countdown}초 뒤 시작 화면으로 이동합니다.")
                },
                onDismiss = { },    // 닫기 금지
                dismissible = false // 시트 닫힘 방지
            )
        }
    }
}