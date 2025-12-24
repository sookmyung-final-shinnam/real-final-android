package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun DashboardParentCard(
    username: String,
    advice: String,
    titleTextStyle: TextStyle,
    adviceTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = Bold),
    cardCorner: Dp = 16.dp,
    cardColor: Color = Color.White,
    borderWidth: Dp = 4.dp,
    borderCorner: Dp = 16.dp,
    borderColor: Color = colorResource(R.color.deep_green),
    modifier: Modifier
) {
    var pressed by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(borderColor, shape = RoundedCornerShape(cardCorner)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text("${username}의 부모님께", style = titleTextStyle)

            // 터치 영역
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitFirstDown()
                                pressed = true

                                // Up 또는 Cancel 대기
                                while (true) {
                                    val event = awaitPointerEvent()
                                    if (event.changes.all { !it.pressed }) {
                                        break
                                    }
                                }

                                pressed = false
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // 원형 도움말
                DashboardHelpButton()
            }
        }

        // 부모 조언
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(cardColor, shape = RoundedCornerShape(cardCorner))
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(borderCorner)
                )
        ) {
            Text(text = advice, style = adviceTextStyle)

            if (pressed) {
                Box( modifier = modifier
                    .background(borderColor)
                ) {
                    Text("터치 중")
                }
            }
        }
    }
}