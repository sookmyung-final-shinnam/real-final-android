package com.veryshinnam.myapp.common.component


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

/**
 * 로고바
 * : {윤곽선 + 텍스트} 박스
 *
 * - onLogoClick: 로고 클릭 시, 홈 화면(HomeScreen)으로 복구
 *  (null이면 동작 X - 홈, 스플래시, 로그인 화면에서 null 보냄)
 */
@Composable
fun LogoBar(
    logoText: String = "Storictor", // 로고 텍스트
    logoTextStyle: TextStyle = MaterialTheme.typography.labelLarge, // 로고 크기
    verticalPadding: Dp = 4.dp,
    onLogoClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding),
        contentAlignment = Alignment.Center
    ) {
        // 윤곽선 + 그림자
        Text(
            text = logoText,
            style = logoTextStyle.copy(
                fontWeight = FontWeight.Black,
                color = Color.White,
                drawStyle = Stroke(width = 8f),
                shadow = Shadow(
                    color = Color.Gray.copy(alpha = 0.6f),
                    offset = Offset(4f, 4f),
                    blurRadius = 8f
                )
            ),
            modifier = Modifier.clearAndSetSemantics { } // 장식용 - 대체 텍스트 제거
        )

        // 실제 텍스트
        Text(
            text = logoText,
            style = logoTextStyle.copy(
                fontWeight = FontWeight.Black,
                color = colorResource(R.color.brand_orange)
            ),
            modifier = if (onLogoClick != null) {
                Modifier.clickable(
                    indication = null,  // 클릭 효과 X
                    interactionSource = remember { MutableInteractionSource() }
                ) { onLogoClick() }
                    .clearAndSetSemantics {
                        contentDescription = "홈 화면으로 이동" // 대체 텍스트
                        role = Role.Button                    // 버튼으로 인식
                    }
            } else Modifier
        )
    }
}