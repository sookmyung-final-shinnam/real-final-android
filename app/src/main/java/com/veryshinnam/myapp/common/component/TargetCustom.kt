package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R

@Composable
fun TargetCustom(
    rect: Rect,
    onCustomClick: () -> Unit,
    containerColor: Color = colorResource(R.color.lemon_yellow),
    contentColor: Color =  colorResource(R.color.main_orange),
    btnCorner: Dp = 20.dp,
    btnTextStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = SemiBold, textAlign = Center)
) {
    val density = LocalDensity.current

    Box(
    modifier = Modifier
        .absoluteOffset(
            x = with(density) { rect.left.toDp() },
            y = with(density) { rect.top.toDp() }
        )
        .size(
            with(density) { rect.width.toDp() },
            with(density) { rect.height.toDp() }
        )
        .zIndex(20f),
    contentAlignment = Alignment.Center
    ) {
        // 버튼을 항상 렌더링하여 높이-공간 확보
        Button(
            onClick = onCustomClick ,
            modifier = Modifier.fillMaxHeight().aspectRatio(1f),
            shape = RoundedCornerShape(btnCorner),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = containerColor,
                disabledContentColor = contentColor
            ),
            border = BorderStroke(
                width = 2.dp,
                color = contentColor
            ),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_keyboard),
                    contentDescription = "직접 추가 이미지", // 접근성 내용 제거
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .padding(16.dp),
                )
                Text(text = "직접 추가하기", style = btnTextStyle)
            }
        }
    }
}