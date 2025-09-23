package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun SelectionCustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    onConfirm: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // 뒤 배경
    Box(modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_yellow)),
        contentAlignment = Alignment.Center
    ) {
        // 입력 박스
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .border(
                    width = 2.dp,
                    color = colorResource(R.color.main_orange),
                    shape = RoundedCornerShape(16.dp)
                )
                .background(colorResource(R.color.lemon_yellow), shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold),
                singleLine = true,
                cursorBrush = SolidColor(colorResource(R.color.main_orange)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onConfirm(value) }
                ),
                decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text("원하는 주제 입력", color = Color.Gray,
                                style = MaterialTheme.typography.headlineSmall )
                        }
                        innerTextField()
                },
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            )
        }
    }
}
