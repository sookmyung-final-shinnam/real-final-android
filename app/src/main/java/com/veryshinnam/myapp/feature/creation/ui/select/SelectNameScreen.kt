package com.veryshinnam.myapp.feature.creation.ui.select

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R

private val JAMO_REGEX = Regex("[ㄱ-ㅎㅏ-ㅣ]")
private enum class NameError {
    NONE, LENGTH, EXIST_JAMO, SPECIAL_CHAR
}

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

    // 닉네임 유효성 검사
    val trimmed = name.trim()
    val error = validateKoreanName(name)
    val isValid = error == NameError.NONE && name.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 1. 상단 '뒤로'
        TextButton(onClick = onBack) { Text("뒤로") }
        Spacer(Modifier.height(8.dp))

        // 2. 이름 입력 컨테이너
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f),
            contentAlignment = Alignment.Center
        ) {
            NameInputContainer(
                name = name,
                onNameChange = { new -> if (new.length <= 10) name = new },
                isValid = isValid,
                error = error,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }

        Spacer(Modifier.weight(0.1f))
        // 3. 하단 공통 버튼
        BottomButton(
            text = "다음 단계로",
            enabled = isValid,
            onClick = {
                vm.setName(trimmed)
                onNext()
            },
            modifier = Modifier
                .navigationBarsPadding()
                .weight(.1f)
        )
    }
}

// 이름 입력 컨테이너
@Composable
private fun NameInputContainer(
    name: String,
    onNameChange: (String) -> Unit,
    isValid: Boolean,
    error: NameError,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.yellow_80)
        )
    ) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Text(
                text = "이름",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                singleLine = true,
                label = { Text("이름을 입력해주세요!") },
                placeholder = { Text("한글 2~10자") },
                isError = name.isNotEmpty() && !isValid,
                supportingText = {
                    when (error) {
                        NameError.NONE -> Text(" ") // 정상 or 비어있을 때는 공백
                        NameError.LENGTH -> Text("2~10자 사이로 입력해 주세요.")
                        NameError.EXIST_JAMO -> Text("자음/모음(ㄱ-ㅎ, ㅏ-ㅣ)은 사용할 수 없습니다.")
                        NameError.SPECIAL_CHAR -> Text("특수문자(!@#$)는 사용할 수 없습니다.")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


// 한국어 닉네임 유효성 처리
private fun validateKoreanName(input: String): NameError {
    val trimmed = input.trim()

    if (trimmed.isEmpty()) return NameError.NONE

    // 길이 체크 (2~10자)
    if (trimmed.length !in 2..10) return NameError.LENGTH

    // 자음 또는 모음 배제
    if (JAMO_REGEX.containsMatchIn(trimmed)) return NameError.EXIST_JAMO

    // 특수문자(!@# 등) 배제
    if (!trimmed.all { it.isLetterOrDigit() || it == ' ' }) return NameError.SPECIAL_CHAR

    return NameError.NONE
}

