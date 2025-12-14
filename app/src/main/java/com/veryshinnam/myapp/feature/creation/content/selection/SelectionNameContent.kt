package com.veryshinnam.myapp.feature.creation.content.selection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.feature.creation.model.NameError
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionNameInput
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionBottomButtons


@Composable
fun SelectionNameContent(
    name: String,
    onNameChange: (String) -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onSimpleWarning: (String) -> Unit,    // 경고 콜백
    modifier: Modifier,
    spacePadding: Dp = 24.dp
) {
    var inputName by rememberSaveable(name) { mutableStateOf(name) }

    // 한국어 닉네임 유효성 처리
    val trimmed = inputName.trim()
    val JAMO_REGEX = Regex("[ㄱ-ㅎㅏ-ㅣ]")
    fun validateKoreanName(input: String): NameError {
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

    // 닉네임 유효성 검사
    val error = validateKoreanName(inputName)
//    val isValid = error == NameError.NONE && inputName.isNotBlank()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally, // 가로 중앙
    ) {
        // 텍스트 필드
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8f),
            contentAlignment = Alignment.TopCenter
        ) {
            SelectionNameInput(
                name = inputName,
                onNameChange = { new ->
                    if (new.length <= 10) inputName = new
                },
                error = error,
                modifier = Modifier.fillMaxWidth(.85f)
            )
        }

        Spacer(Modifier.height(spacePadding))

        // 하단 버튼 영역
        SelectionBottomButtons(
            isLeft = true,     // 이전 버튼
            isCenter = false,  // 없음
            isRight = true,    // 다음 버튼
            onLeftClick = { onPrevClick() },  // 이전 단계로 이동
            onRightClick = {
                when {
                    trimmed.isBlank() -> {
                        onSimpleWarning("아직 이름을 입력하지 않았어요!")
                    }
                    error != NameError.NONE -> {
                        onSimpleWarning("올바르지 않은 이름이에요!")
                    }
                    else -> {
                        onNameChange(trimmed)
                        onNextClick()
                    }
                }
            }, // 다음 단계로 이동
            modifier = Modifier.fillMaxWidth().weight(2f),
        )
    }
}