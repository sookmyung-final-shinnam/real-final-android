package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun DashboardAttemptRow(
    title: String,
    count: Int,
    spacer: Dp = 12.dp,
    horizontalPadding: Dp = 16.dp,
    successColor: Color = colorResource(R.color.main_orange),
    failColor: Color = Color.LightGray,
    titleTextStyle: TextStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = Bold),
    subTextStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    val iconCount = count.coerceIn(1, 3) // 시도 횟수 1 ~ 3 제한

    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = horizontalPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 기, 승, 전, 결 라벨
        Text(
            text = title,
            style = titleTextStyle
        )

        Spacer(Modifier.width(spacer))

        // 시도 아이콘
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 아이콘 영역
            repeat(iconCount) { index ->

                // isLast: 마지막 인덱스
                val isLast = index == iconCount - 1

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),  // 정사각형
                    contentAlignment = Alignment.Center
                ) {

                    // 마지막 횟수는 체크, 나머지 엑스
                    Icon(
                        imageVector = if (isLast) Icons.Outlined.CheckCircle else Icons.Outlined.Cancel,
                        contentDescription = null,
                        tint = if (isLast) successColor else failColor,
                        modifier = Modifier.fillMaxSize(0.8f)
                    )
                }
            }

            // 4칸 나머지 빈 공간 채우기
            repeat(4 - iconCount) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }
        }

        // 횟수
        Text(text = "${count}회", style = subTextStyle.copy(color = successColor))
    }
}