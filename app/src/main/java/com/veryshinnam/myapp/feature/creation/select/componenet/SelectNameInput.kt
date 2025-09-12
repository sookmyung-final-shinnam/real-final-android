package com.veryshinnam.myapp.feature.creation.select.componenet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.creation.model.NameError

@Composable
fun SelectNameInput(
    name: String,
    onNameChange: (String) -> Unit,
    error: NameError,
    modifier: Modifier = Modifier
) {
    val horizontalPadding = 10.dp
    val topPadding = 8.dp
    val textPadding = 20.dp

    Column(modifier) {
        // 제목
        Text("이름",
            modifier = Modifier.padding(horizontal = horizontalPadding),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))

        // 입력 박스
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = topPadding)
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
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                cursorBrush = SolidColor(colorResource(R.color.main_orange)),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                decorationBox = { innerTextField ->
                    if (name.isEmpty()) {
                        Text(
                            text = "주인공 이름 입력 (한글, 숫자, 영어 2~10자)",
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier.fillMaxWidth().padding(textPadding)
            )
        }

        // 에러 메시지 (실시간 반영)
        Box(
            modifier = Modifier
                .padding(top = topPadding, start = horizontalPadding)
                .fillMaxWidth() // 공간은 항상 차지
        ) {
            Text(
                text = when (error) {
                    NameError.LENGTH -> "2~10자 사이로 입력해 주세요."
                    NameError.EXIST_JAMO -> "자음/모음(ㄱ-ㅎ, ㅏ-ㅣ)은 사용할 수 없습니다."
                    NameError.SPECIAL_CHAR -> "특수문자(!@#$)는 사용할 수 없습니다."
                    else -> "" // NONE일 때는 공백
                },
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
