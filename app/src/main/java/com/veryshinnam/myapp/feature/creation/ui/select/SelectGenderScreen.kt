package com.veryshinnam.myapp.feature.creation.ui.select

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // 1. 상단 뒤로
        TextButton(onClick = onBack) { Text("뒤로") }
        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.8f),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.6f)
                    .padding(horizontal = 20.dp),
            ) {
                // 2-1. 성별 남자 버튼
                GenderButton(
                    label = "남자",
                    isSelected = isMaleSelected,
                    onClick = { vm.selectGender("MALE") },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))

                // 2-2. 성별 여자 버튼
                GenderButton(
                    label = "여자",
                    isSelected = isFemaleSelected,
                    onClick = { vm.selectGender("FEMALE") },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.weight(0.1f))
        // 공통 바텀 버튼
        BottomButton(
            text = "다음 단계로",
            enabled = uiState.gender.isNotBlank(),
            onClick = onNext,
            modifier = Modifier
                .navigationBarsPadding()
                .weight(.1f)
        )
    }
}

// 성별 버튼
@Composable
private fun GenderButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) colorResource(R.color.main_orange) else colorResource(R.color.yellow_80),
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = CircleShape,
        modifier = modifier.aspectRatio(1f)
    ) {
        Text(text = label, style = MaterialTheme.typography.titleLarge)
    }
}