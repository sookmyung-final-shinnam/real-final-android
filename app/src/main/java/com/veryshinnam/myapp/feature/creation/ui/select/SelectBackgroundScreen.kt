package com.veryshinnam.myapp.feature.creation.ui.select

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.collections.chunked

// 캐릭터 생성 - 배경 선택 진입점
@Composable
fun SelectBackgroundScreen (
    onNext: () -> Unit,
    onBack: () -> Unit,
    vm: SelectViewModel = hiltViewModel()
) {

    val uiState by vm.selectUiState.collectAsState()
    val selectedBackground = uiState.background

    val basicBackgrounds = listOf("숲 속","바다","왕국","학교","집","우주","지하","직접추가")

    // 직접추가 버튼 클릭시, 다이얼로그 입력창 표시 여부
    var isDialogShow by remember { mutableStateOf(false) }

    // 직접 추가한 배경
    var customBackground by remember { mutableStateOf("") }

    // 뒤로가기
    BackHandler { onBack() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // 1. 상단 '뒤로' 버튼 (원하면 제거 가능)
        TextButton(onClick = onBack) { Text("뒤로") }
        Spacer(Modifier.height(8.dp))

        // 2. 배경 그리드 그룹
//        GridBackgroundGroup(
//            cells = basicBackgrounds,
//            selected = selectedBackground,
//            onSelectedChange = { updated -> vm.setBackground(updated) },
//            onClickCustomCell = { isDialogShow = true },
//            modifier = Modifier.weight(1f)
//        )

        // 3. 공통 바텀 버튼
        BottomButton(
            text = "다음으로",
            enabled = selectedBackground.isNotEmpty(),
            onClick = onNext
        )
    }

    // 4. 공통 직접추가 다이얼로그창
    CustomDialog(
        isShow = isDialogShow,
        title = "직접 입력",
        value = customBackground,
        onValueChange = { customBackground = it },
        onConfirm = {
            val input = customBackground.trim()
            if (input.isNotEmpty()) {
                // 배경 반영
                vm.setBackground(input)
                customBackground = ""
            }
            isDialogShow = false  // 닫기
        },
        onDismiss = { isDialogShow = false }
    ) {
        // 다이얼로그창 안의 내용
        OutlinedTextField(
            value = customBackground,
            onValueChange = { customBackground = it },
            placeholder = { Text("원하는 배경을 입력하세요") }
        )
    }
}

// 배경 그리드 그룹
@Composable
fun GridBackgroundGroup(
    backs: List<String>,
    selected: String,
    onSelectedChange: (String) -> Unit,
    onClickCustomCell: () -> Unit,
    modifier: Modifier = Modifier
){
    val rows = backs.chunked(2) // 2열로 잘라 행 수

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // 각 행 높이 동일
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEach { cell ->
//                    val cell = cells[index]
//                    val isSelected = (cell == selected)
//
//                    Button(
//                        onClick = {
//                            if (cell == "직접추가") onClickCustomCell()
//                            else onSelectedChange(cell) // 단일 선택: 그냥 갈아끼우기
//                        },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = if (isSelected) Color(0xFFFF9800) else Color(0xFFFFF176)
//                        ),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .aspectRatio(1f)
//                    ) { Text(cell) }
                }
            }
        }
    }
}