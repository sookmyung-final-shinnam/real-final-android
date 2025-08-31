package com.veryshinnam.myapp.feature.creation.ui.select

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import kotlin.collections.chunked

// 캐릭터 생성 - 배경 선택 진입점
@Composable
fun SelectBackgroundScreen (
    onNext: () -> Unit,
    onBack: () -> Unit,
    vm: SelectViewModel = hiltViewModel()
) {

    val uiState by vm.selectUiState.collectAsState()
    val selectedBackground = uiState.selectedBackground

    val initBackgrounds = listOf("숲 속","바다","왕국","학교","집","우주","지하","직접추가")

    val updatedBackgrounds = initBackgrounds.toMutableList()
    // 배경은 직접추가 마지막 인덱스로 고정
    uiState.customBackground?.let { updatedBackgrounds[updatedBackgrounds.lastIndex] = it }

    var isDialogShow by remember { mutableStateOf(false) }
    var customBackground by remember { mutableStateOf("") }

    LaunchedEffect(selectedBackground) {
        Log.d("Selection", "선택된 배경: $selectedBackground")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        // 1. 상단 '뒤로' 버튼
        TextButton(onClick = onBack) { Text("뒤로") }
        Spacer(Modifier.height(8.dp))

        // 2. 배경 그리드 그룹
        GridBackgroundGroup(
            backs = updatedBackgrounds,
            selectedBacks = selectedBackground,
            onSelectedChange = { updated -> vm.selectBackground(updated) },
            onClickAddCustom = { isDialogShow = true },
            modifier = Modifier.weight(.8f)
        )
        Spacer(Modifier.weight(0.1f))

        // 3. 공통 바텀 버튼
        BottomButton(
            text = "다음 단계로",
            enabled = selectedBackground.isNotEmpty(),
            onClick = onNext,
            modifier = Modifier
                .navigationBarsPadding()
                .weight(.1f)
        )
    }

    // 4. 공통 직접추가 다이얼로그창
    CustomDialog(
        isShow = isDialogShow,
        title = "원하는 배경을 입력하세요.",
        value = customBackground,
        onValueChange = { customBackground = it },
        onConfirm = {
            val input = customBackground.trim()
            if (input.isNotEmpty()) {
                // 배경 반영
                vm.addCustomBackground(input)
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
            placeholder = { Text("ex. 병원") }
        )
    }
}

// 배경 그리드 그룹
@Composable
fun GridBackgroundGroup(
    backs: List<String>,
    selectedBacks: String,
    onSelectedChange: (String) -> Unit,
    onClickAddCustom: () -> Unit,
    modifier: Modifier = Modifier
){
    val rows = backs.chunked(2) // 2열로 잘라 행 수

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // 각 행 높이 동일
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEach { item ->
                    val isSelectedBack = (item == selectedBacks)

                    Button(
                        onClick = {
                            if (item == "직접추가") {
                                onClickAddCustom()
                            } else {
                                onSelectedChange(item)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelectedBack) colorResource(R.color.main_orange) else colorResource(R.color.yellow_80),
                            contentColor = if (isSelectedBack) Color.White else Color.Black
                        ),
                        shape = RectangleShape,
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .weight(1f) // 각 열 너비 동일
                    ) {
                        Text(item)
                    }
                }
            }
        }
    }
}