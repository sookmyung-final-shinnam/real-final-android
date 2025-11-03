package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.creation.model.NameError

@Composable
fun SelectionNameInput(
    name: String,
    onNameChange: (String) -> Unit,
    error: NameError,
    modifier: Modifier = Modifier,
    spacePadding: Dp = 8.dp,
    textHorizontalPadding: Dp = 16.dp,
    textVerticalPadding: Dp = 20.dp,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold),
    inputTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = Bold),
    filedTextStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = SemiBold)
) {
    Column (modifier = modifier.fillMaxSize()) {
        Spacer(Modifier.fillMaxHeight(0.2f))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(spacePadding)
        ) {
            // 제목
            Text(
                text = "이름",
                style = titleTextStyle
            )

            // 입력 박스
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (error == NameError.NONE) colorResource(R.color.main_orange)
                        else Color.Red,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(colorResource(R.color.lemon_yellow),
                        shape = RoundedCornerShape(16.dp))
            ) {
                BasicTextField(
                    value = name,
                    onValueChange = onNameChange,
                    singleLine = true,
                    textStyle = inputTextStyle,
                    cursorBrush = SolidColor(colorResource(R.color.main_orange)),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    decorationBox = { innerTextField ->
                        if (name.isEmpty()) {
                            Text(
                                text = "주인공 이름 입력 (한글, 숫자, 영어 2~10자)",
                                style = inputTextStyle.copy(
                                    color = Color.Gray,
                                    fontSize = inputTextStyle.fontSize * 0.7f,
                                    fontWeight = SemiBold
                                )
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = textHorizontalPadding, vertical = textVerticalPadding)
                )
            }

            // 에러 메시지 (실시간 반영)
            Box(
                modifier = Modifier
                    .fillMaxWidth() // 공간은 항상 차지
                    .padding(start = textHorizontalPadding/2)
            ) {
                Text(
                    text = when (error) {
                        NameError.LENGTH -> "2~10자 사이로 입력해 주세요."
                        NameError.EXIST_JAMO -> "자음/모음(ㄱ-ㅎ, ㅏ-ㅣ)은 사용할 수 없습니다."
                        NameError.SPECIAL_CHAR -> "특수문자(!@#$)는 사용할 수 없습니다."
                        else -> "" // NONE일 때는 공백
                    },
                    color = Color.Red,
                    style = filedTextStyle
                )
            }
        }
    }
}
