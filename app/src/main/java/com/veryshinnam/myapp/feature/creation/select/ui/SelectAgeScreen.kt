package com.veryshinnam.myapp.feature.creation.select.ui

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.AppTopBar
import com.veryshinnam.myapp.component.common.BackButton
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectAgeScroll
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons

@Composable
fun SelectAgeScreen(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    vm: SelectViewModel
) {

    val uiState by vm.selectUiState.collectAsState()
    val listState = vm.ageListState

    val horizontalPadding = 16.dp

    LaunchedEffect(uiState.age) {
        Log.d("SelectScreen", "현재 선택된 나이: ${uiState.age}")
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
                    text = "동화 속 주인공의 나이는 뭐야?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25f)
                )

                // 나이 스크롤
                SelectAgeScroll(
                    age = uiState.age,
                    listState = listState,
                    onAgeConfirm = { vm.selectAge(it) },
                    modifier = Modifier.fillMaxWidth().weight(0.55f)
                )

                // 하단 버튼 영역
                SelectTripleButtons(
                    isLeft = true,     // 이전 버튼
                    isCenter = false,  // 없음
                    isRight = true,    // 다음 버튼
                    onLeftClick = { onBackClick() },  // 이전 단계로 이동
                    onRightClick = {
                        if (uiState.age != -1) {
                            onNextClick()
                        } else {
                            // TODO: 나이를 선택하세요
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
