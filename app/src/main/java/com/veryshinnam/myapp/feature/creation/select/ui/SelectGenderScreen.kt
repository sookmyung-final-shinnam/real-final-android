package com.veryshinnam.myapp.feature.creation.select.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.AppTopBar
import com.veryshinnam.myapp.component.common.BackButton
import com.veryshinnam.myapp.feature.creation.model.Gender
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectGenderButton
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons

@Composable
fun SelectGenderScreen(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    vm: SelectViewModel
) {
    val uiState by vm.selectUiState.collectAsState()

    val horizontalPadding = 16.dp

    // 성별 버튼 체크 용도
    val isFemaleSelected = uiState.gender == Gender.FEMALE
    val isMaleSelected = uiState.gender == Gender.MALE

    LaunchedEffect(uiState.gender) {
        Log.d("SelectScreen", "현재 선택된 성별: ${uiState.gender}")
    }

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = { AppTopBar() }, // 상단 로고
        contentWindowInsets = WindowInsets.navigationBars // 네비게이션 여백
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            BackButton(
                onBackClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
            )

            // TODO: 프로그레스바

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = horizontalPadding)
            ) {
                // 현재 스크린 설명
                SelectInfo(
                    text = "동화 속 주인공의 성별은 뭐야?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25f)
                )

                // 성별 버튼
                Column(
                    modifier = Modifier.fillMaxWidth(0.4f)
                        .weight(0.55f)
                        .align(Alignment.CenterHorizontally),
                ) {
                    // 여자 버튼
                    SelectGenderButton(
                        gender = Gender.FEMALE,
                        isSelected = isFemaleSelected,
                        onButtonClick = { vm.selectGender(Gender.FEMALE) },
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )

                    Spacer(Modifier.weight(0.2f))

                    // 남자 버튼
                    SelectGenderButton(
                        gender = Gender.MALE,
                        isSelected = isMaleSelected,
                        onButtonClick = { vm.selectGender(Gender.MALE) },
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )
                }

                // 하단 버튼 영역
                SelectTripleButtons(
                    isLeft = true,     // 이전 버튼
                    isCenter = false,  // 없음
                    isRight = true,    // 다음 버튼
                    onLeftClick = { onBackClick() },  // 이전 단계로 이동
                    onRightClick = {
                        if (uiState.gender != Gender.NONE) {
                            onNextClick() // 다음 단계로 이동
                        } else {
                            // TODO: 성별을 선택하세요
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f),
                )
            }
        }
    }
}