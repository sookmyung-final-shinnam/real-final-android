package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imeAnimationTarget
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectionCustomInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onConfirm: (String) -> Unit,
    textHorizontalPadding: Dp = 16.dp,
    textVerticalPadding: Dp = 20.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = Bold),
) {
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(Modifier.fillMaxHeight(0.3f))

        // 입력 박스
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = colorResource(R.color.main_orange),
                    shape = RoundedCornerShape(16.dp)
                )
                .background(
                    color = colorResource(R.color.lemon_yellow),
                    shape = RoundedCornerShape(16.dp))
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = textStyle,
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
                        Text("원하는 주제 입력",
                            style = textStyle.copy(
                                color = Color.Gray,
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
    }
}
