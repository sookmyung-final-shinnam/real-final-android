package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

// 있으면 버튼, 없으면 빈공간 차지
@Composable
fun SelectionBottomButtons(
    isLeft: Boolean,
    isCenter: Boolean,
    isRight: Boolean,
    onLeftClick: () -> Unit = {},
    onCenterClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
    onCustomBRect: (Rect) -> Unit = {},
    modifier: Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(color = colorResource(R.color.main_orange), fontWeight = SemiBold),
    centerTextStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = Bold, textAlign = Center)
) {
    // --- 이전 버튼 + 직접 추가 버튼 + 다음 버튼
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 이전 버튼
        if (isLeft) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = { onLeftClick() })
                    .semantics(true) {
                        contentDescription = "이전 단계로 가기"
                        role = Role.Button
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = colorResource(R.color.main_orange)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "이전",
                    style = textStyle,
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        } else {
            Spacer(Modifier.weight(1f))
        }

        // 직접 추가 버튼
        val backgroundColor = Brush.verticalGradient(
            colors = if (isCenter) {
                listOf(
                    colorResource(id = R.color.lemon_yellow),
                    colorResource(id = R.color.lemon_yellow),
                    Color.LightGray.copy(alpha = 0.3f)
                )
            } else {
                listOf(
                    Color.Transparent,
                    Color.Transparent
                )
            }
        )
        val contentColor = if (isCenter) Color.Black else Color.Transparent
        val borderColor = if (isCenter) colorResource(R.color.main_orange) else Color.Transparent

        Box(
            modifier = Modifier
                .weight(1f)
                .onGloballyPositioned { onCustomBRect(it.boundsInRoot()) },
            contentAlignment = Alignment.Center
        ) {
            // 버튼을 항상 렌더링하여 높이-공간 확보
            Button(
                onClick = { if (isCenter) onCenterClick() },
                enabled = isCenter,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .background(brush = backgroundColor, shape = RoundedCornerShape(20.dp))
                    .semantics(true) {
                        if (isCenter) {
                            contentDescription = "직접 추가"
                            role = Role.Button
                        }
                    },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = contentColor,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = contentColor
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = borderColor
                ),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_keyboard),
                        contentDescription = null, // 접근성 내용 제거
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        alpha = if (isCenter) 1.0f else 0.0f
                    )
                    Text(
                        text = "직접\n추가하기",
                        style = centerTextStyle,
                        modifier = Modifier
                            .padding(bottom = 2.dp)
                            .clearAndSetSemantics { }
                    )
                }
            }
        }

        // 다음 버튼
        if (isRight) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = { onRightClick() })
                    .semantics(true) {
                        contentDescription = "다음 단계로 가기"
                        role = Role.Button
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "다음",
                    style = textStyle,
                    modifier = Modifier.clearAndSetSemantics { }
                )

                Spacer(Modifier.width(4.dp))
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = colorResource(R.color.main_orange)
                )
            }
        } else {
            Spacer(Modifier.weight(1f))
        }
    }
}