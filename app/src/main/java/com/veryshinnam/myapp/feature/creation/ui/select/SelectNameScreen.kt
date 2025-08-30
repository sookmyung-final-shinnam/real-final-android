package com.veryshinnam.myapp.feature.creation.ui.select

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SelectNameScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    vm: SelectViewModel= hiltViewModel()
) {

    val uiState by vm.selectUiState.collectAsState()

    // 이름은 바텀 버튼에서 업데이트
    var name by rememberSaveable(uiState.name) {
        mutableStateOf(uiState.name)
    }

    // 유효성 체크 (2-10자)
    val trimmed = name.trim()
    val isValid = trimmed.length in 2..10

    BackHandler { onBack() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.fillMaxWidth()) {

            // 1. 상단 '뒤로'
            TextButton(onClick = onBack) { Text("뒤로") }
            Spacer(Modifier.height(8.dp))

            // 2. 이름 입력 컨테이너
            NameInputContainer(
                value = name,
                onValueChange = { new ->
                    if (new.length <= 10) name = new
                },
                isValid = isValid
            )
        }

        // 3. 하단 공통 버튼
        BottomButton(
            text = "다음 단계로",
            enabled = isValid,
            onClick = {
                vm.setName(trimmed)
                onNext()
            }
        )
    }
}

// 이름 입력 컨테이너
@Composable
private fun NameInputContainer(
    value: String,
    onValueChange: (String) -> Unit,
    isValid: Boolean
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Text(
                text = "이름",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                label = { Text("이름을 입력해주세요!") },
                placeholder = { Text("한글, 숫자, 영어 2~10자") },
                isError = value.isNotEmpty() && !isValid,
                supportingText = {
                    val len = value.trim().length
                    Text(if (isValid || value.isEmpty()) " " else "2~10자 사이로 입력해 주세요 ($len/10)")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}