package com.veryshinnam.myapp.feature.permit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar
import com.veryshinnam.myapp.feature.permit.component.PermitTermText
import com.veryshinnam.myapp.feature.permit.component.PermitTermTitle

@Composable
fun SignUpScreen(
    tempCode: String,
    onHome: () -> Unit,
    vm: PermitViewModel = hiltViewModel()
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_yellow))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            // 상단 AppBar
            AppTopBar()

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "이용 약관 동의",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 이용 약관
            Column {
                PermitTermTitle(
                    title = "이용 약관",
                    isRequired = true,
                    isChecked =  termsChecked,
                    onClick = { termsChecked = !termsChecked }
                )
                Spacer(modifier = Modifier.height(4.dp))
                PermitTermText(
                    content = "본 이용 약관(이하 “이용 약관”)은 스토릭터 앱 및 관련 서비스의 사용 조건을 규정하며, 이용자의 권리와 의무를 명시합니다."
                )
            }


            // 개인 정보 수집
            Column {
                PermitTermTitle(
                    title = "개인 정보 수집 및 이용 동의",
                    isRequired = true,
                    isChecked = privacyChecked,
                    onClick = { privacyChecked = !privacyChecked }
                )
                Spacer(modifier = Modifier.height(4.dp))
                PermitTermText(
                    content = "회사는 이용자분들께 서비스의 다양한 기능과 편의성을 제공하기 위하여, 목적별로 이용자들의 개인정보를 필수 항목과 선택 항목으로 구분하여 수집하고 있습니다. " +
                            "\n또한 이용자가 생성한 동화 콘텐츠는 카카오톡을 통해 공유되거나 유튜브 등 외부 플랫폼에 업로드될 수 있으며, " +
                            "이러한 특성상 게시된 콘텐츠는 임의로 삭제가 불가능합니다. 삭제를 원하시는 경우 저희 이메일을 통해 문의해 주시기 바랍니다."
                )
            }

            // 모두 동의
            PermitTermTitle(
                title = "위 약관을 모두 확인하였으며, 전체 항목에 동의합니다.",
                isRequired = false,
                isChecked = allChecked,
                onClick = {
                    val newState = !(termsChecked && privacyChecked)
                    termsChecked = newState
                    privacyChecked = newState
                },
                textColor = colorResource(R.color.main_orange),
                textSize = MaterialTheme.typography.titleLarge
            )
        }

        // 하단 버튼
        Column {
            Button(
                onClick = { if (allChecked) vm.login(tempCode) },
                enabled = allChecked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.main_orange),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Text(
                    text = "시작하기",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}