package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R

@Composable
fun TargetConvText(
    rect: Rect,
    density: Density,
    text: String,
    onClick: () -> Unit,
    borderColor: Color = colorResource(R.color.main_orange),
    replayColor: Color = colorResource(R.color.lemon_yellow),
    verticalPadding: Dp = 24.dp,
    spacePadding: Dp = 12.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold),
    replayTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = colorResource(R.color.main_orange), fontWeight = SemiBold)
) {

    // 질문 텍스트
    Card(
        modifier = Modifier
            .absoluteOffset(
                x = with(density) { rect.left.toDp() },
                y = with(density) { rect.top.toDp() }
            )
            .width(with(density) { rect.width.toDp() })
            .wrapContentHeight()
            .zIndex(20f)
            .padding(vertical = verticalPadding),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(verticalPadding),
            verticalArrangement = Arrangement.spacedBy(spacePadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // 질문 텍스트
            Text(
                text = text,
                style = textStyle
            )

            // 다시듣기
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(CircleShape)
                    .border(2.dp, borderColor, CircleShape)
                    .background(replayColor)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(IntrinsicSize.Min) // Row 크기 유지
                        .padding(horizontal = 10.dp, vertical = 4.dp) // 내부 패딩
                ) {
                    Text(
                        text = "다시 듣기",
                        style = replayTextStyle
                    )

                    Icon(
                        imageVector = Icons.Default.Replay,
                        contentDescription = "다시듣기 아이콘",
                        tint = borderColor,
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .fillMaxHeight()
                    )
                }
            }

        }
    }
}
