package com.veryshinnam.myapp.feature.permit.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.feature.permit.component.PermitTermDesc
import com.veryshinnam.myapp.feature.permit.component.PermitTermTitle

@Composable
fun SignUpScreen(
    tempCode: String,
    onHome: () -> Unit,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 12.dp,
    spacePadding: Dp = 2.dp,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold),
    vm: PermitViewModel = hiltViewModel(),
) {
    var termsChecked by remember { mutableStateOf(false) }
    var privacyChecked by remember { mutableStateOf(false) }

    val allChecked = termsChecked && privacyChecked
    val state by vm.permitUiState.collectAsStateWithLifecycle()

    // 로그인 결과 감지
    LaunchedEffect(state) {
        if (state is PermitUiState.Success) {
            onHome()
        }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(verticalPadding * 2)
        ) {
            // 제목
            Text(
                text = "이용 약관 동의",
                style = titleTextStyle,
                modifier = Modifier.padding(top = verticalPadding, start = horizontalPadding, end = horizontalPadding),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding, verticalPadding/2)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(verticalPadding*2)
            ) {
                // 이용 약관
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    PermitTermTitle(
                        titleText = "이용 약관",
                        isRequired = true,
                        isChecked =  termsChecked,
                        onClick = { termsChecked = !termsChecked }
                    )
                    Spacer(Modifier.height(spacePadding))
                    PermitTermDesc(
                        modifier = Modifier.weight(1f),
                        descText = "본 이용 약관(이하 “이용 약관”)은 스토릭터 앱 및 관련 서비스의 사용 조건을 규정하며, 이용자의 권리와 의무를 명시합니다."
                    )
                }

                // 개인 정보 수집
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    PermitTermTitle(
                        titleText = "개인 정보 수집 및 이용 동의",
                        isRequired = true,
                        isChecked = privacyChecked,
                        onClick = { privacyChecked = !privacyChecked }
                    )
                    Spacer(Modifier.height(spacePadding))
                    PermitTermDesc(
                        modifier = Modifier.weight(1f),
                        descText = """
Storictor(이하 “당사”)는 사용자의 개인정보를 중요하게 생각하며, 「개인정보 보호법」 등 관련 법령을 준수합니다.
본 개인정보처리방침은 당사가 수집하는 개인정보의 항목, 이용 목적, 처리 방법 등에 대해 설명합니다.

1. 수집하는 개인정보 항목
당사는 서비스 제공을 위해 다음과 같은 개인정보를 수집합니다.

- 회원가입 시 필수 항목: 이름(닉네임), 이메일 주소  
  (카카오 로그인 연동 시 카카오 계정 정보에서 자동 수집됩니다.)

2. 개인정보 수집 및 이용 목적
수집된 개인정보는 다음의 목적에 따라 이용됩니다.  
- 회원 관리: 회원 식별, 가입 의사 확인, 로그인 인증  
- 서비스 제공: 콘텐츠 제공, 맞춤형 서비스 제공  
- 고객 지원: 문의 응대 및 문제 해결  
- 마케팅: 신규 서비스 안내 및 프로모션  

또한, 이용자가 생성한 동화 콘텐츠는 카카오톡 공유 또는 유튜브 등 외부 플랫폼에 업로드될 수 있습니다.  
이러한 특성상 게시된 콘텐츠는 임의로 삭제가 불가능할 수 있으며,  
삭제를 원하시는 경우 당사 이메일(veryshinnam@gmail.com)로 문의해 주시기 바랍니다.

3. 개인정보의 보유 및 이용 기간
회원 탈퇴 시 개인정보는 즉시 삭제됩니다.  
단, 관련 법령에 따라 일정 기간 보관이 필요한 정보는 다음과 같습니다.  
- 계약 또는 청약철회 기록: 5년 (전자상거래 등에서의 소비자 보호에 관한 법률)  
- 거래 기록: 5년 (전자상거래 등에서의 소비자 보호에 관한 법률)

4. 개인정보의 제3자 제공
당사는 원칙적으로 사용자의 개인정보를 외부에 제공하지 않습니다.  
다만, 법령에 따라 요청이 있을 경우에는 예외적으로 제공할 수 있습니다.

5. 사용자의 권리
사용자는 언제든지 자신의 개인정보를 열람, 수정, 삭제 요청할 수 있습니다.  
요청 시 당사는 지체 없이 필요한 조치를 취하겠습니다.

6. 개인정보 보호책임자
- 책임자 이름: 000  
- 연락처: veryshinnam@gmail.com  

7. 개인정보처리방침의 변경
본 방침은 변경될 수 있으며, 변경 시 공지사항을 통해 사전 안내드리겠습니다.  

최종 개정일: 2025-11-01
""".trimIndent()
                    )
                }
            }

            // 모두 동의
            PermitTermTitle(
                titleText = "위 약관을 모두 확인하였으며, 전체 항목에 동의합니다.".replace("", "\u200B"),
                isRequired = false,
                isChecked = allChecked,
                onClick = {
                    val newState = !(termsChecked && privacyChecked)
                    termsChecked = newState
                    privacyChecked = newState
                },
                titleTextStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = if (allChecked) colorResource(R.color.main_orange) else Color.Gray,
                    fontWeight = SemiBold
                ),
                modifier = Modifier.padding(horizontal = horizontalPadding)
            )

            // 하단 버튼
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { if (allChecked) vm.signup(tempCode) }, // 로그인과 함께 신규 유저 플래그
                    enabled = allChecked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.main_orange),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = "시작하기",
                        style = titleTextStyle.copy(
                            fontSize = titleTextStyle.fontSize * 1.2f
                        ),
                        modifier = Modifier.padding(vertical = verticalPadding)
                    )
                }
            }
        }
    }
}