package com.veryshinnam.myapp.feature.creation.ui.select

import android.R.attr.onClick
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.feature.collection.ui.component.CollectionInfo
import com.veryshinnam.myapp.feature.creation.ui.select.componenet.SelectCustomInput
import com.veryshinnam.myapp.feature.creation.ui.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.ui.select.componenet.SelectItemGrid
import com.veryshinnam.myapp.feature.creation.ui.select.componenet.SelectTripleButtons
import com.veryshinnam.myapp.feature.home.ui.HomeMainScreen
import com.veryshinnam.myapp.feature.home.ui.HomeUiState


// 캐릭터 생성 - 테마 선택 진입점
@Composable
fun SelectThemeScreen(
    onNext: () -> Unit,
    onBackClick: () -> Unit,
    vm: SelectViewModel = hiltViewModel()
) {
    val uiState by vm.selectUiState.collectAsState()

    var isInputMode by remember { mutableStateOf(false) }
    var customInput by remember { mutableStateOf("") }

    val horizontalPadding = 16.dp
    val themes = listOf("로맨스", "모험", "일상", "공포", "우정", "추리")

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
            BackButton(onBackClick, modifier = Modifier.align(Alignment.TopStart))

            // TODO: 프로그레스바

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = horizontalPadding)
            ) {
                // 현재 스크린 설명
                SelectInfo(
                    text = if (isInputMode) {
                        "원하는 주제를 입력해줘!" // 입력창 모드
                    } else {
                        "만들고 싶은 동화의 주제를 3개까지 골라봐!" // 그리드 셀 모드
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
                                vm.addCustomTheme(customInput)
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
                        items = themes,
                        customItem = uiState.customTheme,
                        maxSelectCount = 3,
                        onItemClick = { vm.selectTheme(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.55f)
                    )

                    // 하단 버튼 영역
                    SelectTripleButtons(
                        isLeft = false,    // 첫 화면이니 이전 버튼 없음
                        isCenter = true,   // 직접추가 버튼
                        isRight = true,    // 다음 버튼
                        onCenterClick = { isInputMode = true }, // 직접추가 입력창 열기
                        onRightClick = { onNext() },            // 다음 단계로 이동
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.2f),
                    )
                }
            }
        }
    }
}