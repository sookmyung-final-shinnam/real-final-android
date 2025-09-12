package com.veryshinnam.myapp.feature.creation.select.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.feature.creation.model.NameError
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectNameInput
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons


@Composable
fun SelectNameScreen(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    vm: SelectViewModel
) {
    val uiState by vm.selectUiState.collectAsState()

    // TODO: 이름은 바텀 버튼에서 업데이트
    var name by rememberSaveable(uiState.name) { mutableStateOf(uiState.name) }

    // 닉네임 유효성 검사
    val trimmed = name.trim()
    val error = validateKoreanName(name)
    val isValid = error == NameError.NONE && name.isNotBlank()

    val horizontalPadding = 16.dp

    LaunchedEffect(uiState.name) {
        Log.d("SelectScreen", "현재 선택된 이름: ${uiState.name}")
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
                    text = "동화 속 주인공의 이름은 뭐야?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25f)
                )

                // 텍스트 필드
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .weight(0.55f),
                    verticalArrangement = Arrangement.Center,          // 세로 중앙
                    horizontalAlignment = Alignment.CenterHorizontally // 가로 중앙
                ) {
                    SelectNameInput(
                        name = name,
                        onNameChange = { new -> if (new.length <= 10) name = new },
                        error = error,
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                }

                // 하단 버튼 영역
                SelectTripleButtons(
                    isLeft = true,     // 이전 버튼
                    isCenter = false,  // 없음
                    isRight = true,    // 다음 버튼
                    onLeftClick = { onBackClick() },  // 이전 단계로 이동
                    onRightClick = {
                        if (isValid) {
                            vm.selectName(trimmed)
                            onNextClick()
                        }
                    }, // 다음 단계로 이동
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f),
                )
            }
        }
    }
}


// 한국어 닉네임 유효성 처리
private val JAMO_REGEX = Regex("[ㄱ-ㅎㅏ-ㅣ]")

private fun validateKoreanName(input: String): NameError {
    val trimmed = input.trim()

    if (trimmed.isEmpty()) return NameError.NONE

    // 길이 체크 (2~10자)
    if (trimmed.length !in 2..10) return NameError.LENGTH

    // 자음 또는 모음 배제
    if (JAMO_REGEX.containsMatchIn(trimmed)) return NameError.EXIST_JAMO

    // 특수문자(!@# 등) 배제
    if (!trimmed.all { it.isLetterOrDigit() || it == ' ' }) return NameError.SPECIAL_CHAR

    return NameError.NONE
}