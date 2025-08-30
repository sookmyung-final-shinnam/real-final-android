package com.veryshinnam.myapp.feature.creation.ui.select

import android.R.attr.onClick
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R


// 캐릭터 생성 - 테마 선택 진입점
@Composable
fun SelectThemeScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    vm: SelectViewModel = hiltViewModel()
) {

    // 테마 선택 화면 상태 관리
    val uiState by vm.selectUiState.collectAsState()

    val initThemes =  remember {
        mutableStateListOf("웃음", "사랑", "삼", "사", "오", "육", "직접추가", "직접추가")
    }

    val updatedThemes = remember(uiState.customThemes) {
        initThemes.mapIndexed { index, text ->
            uiState.customThemes[index] ?: text
        }
    }

    val selectedThemes = uiState.selectedThemes
    val customThemes = uiState.customThemes

    // 직접추가 > 다이얼로그 입력창 표시 여부
    var isInputDialogShow by remember { mutableStateOf(false) }

    // 직접 추가한 테마
    var customInput by remember { mutableStateOf("") }
    var customIndex by remember { mutableIntStateOf(-1) }

    // 뒤로가기 > 다이얼로그 창
    var isBackDialogShow by remember { mutableStateOf(false) }

    LaunchedEffect(selectedThemes) {
        Log.d("SelectThemeScreen", "선택된 테마: $selectedThemes")
    }

    // 0. 뒤로가기
    BackHandler {
        isBackDialogShow = true
    }

    BackDialog(
        isShow = isBackDialogShow,
        onDismiss = { isBackDialogShow = false },
        onConfirm = {
            isBackDialogShow = false
            onBack()  // 원래 함수
        }
    )

    Column(
    modifier = Modifier
    .fillMaxSize()
    .padding(16.dp),
    ) {
        // 1. 상단 '뒤로' 버튼
        TextButton(onClick = { isBackDialogShow = true }) {
            Text("뒤로")
        }
        Spacer(Modifier.height(8.dp))

        // 2. 테마 그리드 그룹
        GridThemeGroup(
            themes = updatedThemes,
            selectedThemes = selectedThemes,
            customThemes = customThemes.values.toList(),
            onSelectedChange = { selected ->
                vm.selectTheme(selected) },
            onClickAddCustom = { index ->
                customIndex = index
                isInputDialogShow = true
            },
            onClickDeleteCustom = { customTheme ->
                val index = updatedThemes.indexOf(customTheme)
                if (index != -1) {
                    initThemes[index] = "직접추가" // UI 복구
                }
                vm.removeCustomTheme(customTheme)
            },
            modifier = Modifier
                .weight(.8f)
        )
        Spacer(Modifier.weight(0.1f))

        // 3. 공통 바텀 버튼
        BottomButton(
            text = "다음으로",
            enabled = selectedThemes.isNotEmpty(),
            onClick = onNext,
            modifier = Modifier
                .navigationBarsPadding()
                .weight(.1f)
        )

    }

    // 4. 공통 직접추가 다이얼로그창
    CustomDialog(
        isShow = isInputDialogShow,
        title = "원하는 테마를 입력하세요.",
        value = customInput,
        onValueChange = { customInput = it },
        onConfirm = {
            val input = customInput.trim()
            if (input.isNotEmpty() && customIndex != -1) {
                initThemes[customIndex] = input

                vm.addCustomTheme(customIndex, input)

                customInput = ""
            }
            isInputDialogShow = false  // 닫기
        },
        onDismiss = { isInputDialogShow = false }
    ) {
        // 다이얼로그창 안의 내용
        OutlinedTextField(
            value = customInput,
            onValueChange = { customInput = it },
            placeholder = { Text("ex. 신비") }
        )
    }
}

// 뒤로가기 대화창
@Composable
fun BackDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    if (!isShow) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("정말 나가시겠습니까?") },
        text = { Text("현재 선택하신 정보들은 저장되지 않습니다.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("예")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("아니오")
            }
        }
    )
}


// 테마 그리드 그룹
@Composable
fun GridThemeGroup(
    themes: List<String>,
    selectedThemes: List<String>,
    customThemes: List<String>,
    onSelectedChange: (String) -> Unit,
    onClickAddCustom: (Int) -> Unit,
    onClickDeleteCustom: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val rows = themes.chunked(2) // 2열로 잘라 행 수

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        rows.forEachIndexed  { rowIndex, rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // 각 행 높이 동일
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEachIndexed { indexInRow, item ->
                    val selectedIndex = rowIndex * 2 + indexInRow // 선택한 버튼 위치의 인덱스
                    val isCustomTheme = customThemes.contains(item)
                    val isSelectedTheme = selectedThemes.contains(item)

                    Button(
                        onClick = {
                            // 직접추가 버튼
                            if (item == "직접추가") {
                                onClickAddCustom(selectedIndex)
                            } else if (isCustomTheme) {
                                onClickDeleteCustom(item)
                            }
                            // 일반 버튼
                            else {
                                onSelectedChange(item)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            // 버튼 배경색
                            containerColor = if (isSelectedTheme) colorResource(R.color.main_orange) else colorResource(R.color.yellow_80),

                            // 버튼 글자색
                            contentColor = if (isSelectedTheme) Color.White else Color.Black
                        ),
                        shape = RectangleShape,
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .weight(1f) // 각 열 너비 동일
                    ) {
                        Text(text = item)
                    }
                }

                // 만약 rowItems가 1개뿐이라면 오른쪽에 빈 공간 추가
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}