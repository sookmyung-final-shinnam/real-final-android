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
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectPaletteGrid
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectStyleButtons
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons


@Composable
fun SelectFaceScreen(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    vm: SelectViewModel
) {
    val uiState by vm.selectUiState.collectAsState()
    val horizontalPadding = 16.dp

    // 기본 팔레트 색상 및 머리 스타일
    val colors = listOf(
        "빨강색" to R.color.palette_red,
        "분홍색" to R.color.palette_pink,
        "주황색" to R.color.palette_orange,
        "노랑색" to R.color.palette_yellow,
        "초록색" to R.color.palette_green,
        "연두색" to R.color.palette_lgreen,
        "파랑색" to R.color.palette_blue,
        "하늘색" to R.color.palette_sblue,
        "보라색" to R.color.palette_purple,
        "갈색" to R.color.palette_brown,
        "흰색" to R.color.palette_white,
        "검정색" to R.color.palette_black
    )
    val styles = listOf("단발 머리", "긴 머리", "곱슬 머리", "대머리")

    // 유효성 체크 (모두 선택)
    val isValid = uiState.eyeColor.isNotEmpty() && uiState.hairColor.isNotEmpty()
            && uiState.hairStyle.isNotEmpty()

    LaunchedEffect(uiState.hairStyle) {
        Log.d("SelectScreen", "현재 선택된 머리: ${uiState.hairStyle}")
    }

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = { AppTopBar() }, // 상단 로고
        contentWindowInsets = WindowInsets.navigationBars // 네비게이션 여백
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = horizontalPadding)
            ) {
                // 현재 스크린 설명
                SelectInfo(
                    text = "동화 속 주인공의 모습은 어떻게 할까?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25f)
                )

                // 파레트 및 스타일
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.55f),
                    horizontalAlignment = Alignment.CenterHorizontally // 가로 중앙
                ) {
                    // 눈색 선택 파레트
                    SelectPaletteGrid(
                        title = "눈동자 색",
                        colors = colors,
                        selectedColorName = uiState.eyeColor,
                        onSelect = { vm.selectEyeColor(it) },
                        modifier = Modifier.weight(0.4f)
                    )
                    Spacer(Modifier.weight(0.05f))

                    // 머리색 선택 파레트
                    SelectPaletteGrid(
                        title = "머리카락 색",
                        colors = colors,
                        selectedColorName = uiState.hairColor,
                        onSelect = { vm.selectHairColor(it) },
                        modifier = Modifier.weight(0.4f)
                    )

                    Spacer(Modifier.weight(0.05f))

                    // 머리 모양 선택 버튼
                    SelectStyleButtons(
                        title = "머리 모양",
                        styles = styles,
                        selected = uiState.hairStyle,
                        onSelect = { vm.selectHairStyle(it)},
                        modifier = Modifier.weight(0.2f)
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
                            onNextClick()
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