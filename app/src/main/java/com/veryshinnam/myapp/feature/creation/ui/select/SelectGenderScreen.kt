package com.veryshinnam.myapp.feature.creation.ui.select

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SelectGenderScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    vm: SelectViewModel = hiltViewModel()
) {

    val uiState by vm.selectUiState.collectAsState()

    // 성별 버튼 체크 용도
    val isMaleSelected = uiState.gender == "MALE"
    val isFemaleSelected = uiState.gender == "FEMALE"

    BackHandler { onBack() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.fillMaxWidth()) {
            // 1. 상단 뒤로
            TextButton(onClick = onBack) { Text("뒤로") }
            Spacer(Modifier.height(8.dp))

            // 2-1. 성별 남자 버튼
            GenderButton(
                label = "남자",
                selected = isMaleSelected,
                onClick = { vm.setGender("MALE") },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Spacer(Modifier.height(16.dp))

            // 2-2. 성별 여자 버튼
            GenderButton(
                label = "여자",
                selected = isFemaleSelected,
                onClick = { vm.setGender("FEMALE") },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }

        // 공통 바텀 버튼
        BottomButton(
            text = "다음 단계로",
            enabled = uiState.gender.isNotBlank(),
            onClick = onNext
        )
    }
}

// 성별 버튼
@Composable
private fun GenderButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFFFF9800) else Color(0xFFFFF176)
        ),
        shape = MaterialTheme.shapes.extraLarge, // 둥글게
        modifier = modifier
    ) {
        Text(text = label, style = MaterialTheme.typography.titleLarge)
    }
}