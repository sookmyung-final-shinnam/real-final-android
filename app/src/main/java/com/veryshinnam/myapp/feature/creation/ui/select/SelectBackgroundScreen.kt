package com.veryshinnam.myapp.feature.creation.ui.select

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.feature.creation.ui.select.componenet.SelectCustomInput
import com.veryshinnam.myapp.feature.creation.ui.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.ui.select.componenet.SelectItemGrid
import com.veryshinnam.myapp.feature.creation.ui.select.componenet.SelectTripleButtons
import kotlin.collections.chunked

// 캐릭터 생성 - 배경 선택 진입점
@Composable
fun SelectBackgroundScreen (
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    vm: SelectViewModel = hiltViewModel()
) {

    val uiState by vm.selectUiState.collectAsState()

    var isInputMode by remember { mutableStateOf(false) }
    var customInput by remember { mutableStateOf("") }

    val horizontalPadding = 16.dp
    val backgrounds = listOf("숲 속","바다","왕국","학교","집","우주")


    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = { AppTopBar() }, // 상단 로고
        contentWindowInsets = WindowInsets.navigationBars // 네비게이션 여백
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            BackButton(onBackClick, modifier = Modifier.align(Alignment.TopStart)) // 백버튼

            // TODO: 프로그레스바

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = horizontalPadding)
            ) {
                // 현재 스크린 설명
                SelectInfo(
                    text = if (isInputMode) {
                        "원하는 배경을 입력해 줘!" // 입력창 모드
                    } else {
                        "만들고 싶은 동화의 배경을 골라봐!" // 그리드 셀 모드
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25f)
                )

                // 직접추가 버튼 누름에 따라 입력창 또는 그리드 셀 모드
                if (isInputMode) {
                    SelectCustomInput(
                        value = customInput,
                        onValueChange = { customInput = it },
                        onConfirm = {
                            if (customInput.isNotBlank()) {
                                vm.addCustomBackground(customInput)
                                customInput = ""
                                isInputMode = false
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.75f)
                    )
                } else {
                    SelectItemGrid(
                        items = backgrounds,
                        customItem = uiState.customBackground,
                        maxSelectCount = 1,
                        onItemClick = { vm.selectBackground(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.55f)
                    )

                    // 하단 버튼 영역
                    SelectTripleButtons(
                        isLeft = true,     // 이전 버튼
                        isCenter = true,   // 직접추가 버튼
                        isRight = true,    // 다음 버튼
                        onLeftClick = { onBackClick() },
                        onCenterClick = { isInputMode = true }, // 직접추가 입력창 열기
                        onRightClick = { onNextClick() }, // 다음 단계로 이동
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.2f),
                    )
                }
            }
        }
    }
}