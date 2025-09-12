package com.veryshinnam.myapp.feature.creation.select.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectAgeScroll
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons

@Composable
fun SelectAgeScreen(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    vm: SelectViewModel = hiltViewModel()
) {

    val uiState by vm.selectUiState.collectAsState()
    val horizontalPadding = 16.dp
    var selectedValue by remember { mutableStateOf(50) }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 9)

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
                    value = selectedValue,
                    onValueChange = { selectedValue = it },
                    modifier = Modifier.fillMaxWidth().weight(0.55f)
                )

                // 하단 버튼 영역
                SelectTripleButtons(
                    isLeft = true,     // 이전 버튼
                    isCenter = false,  // 없음
                    isRight = true,    // 다음 버튼
                    onLeftClick = { onBackClick() },  // 이전 단계로 이동
                    onRightClick = {
                        val centerIndex = listState.firstVisibleItemIndex + 2
                        val selectedAge = centerIndex + 1
                        vm.selectAge(selectedAge) // 나이 업데이트
                        onNextClick() // 다음 단계로 이동
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f),
                )
            }
        }
    }
}
