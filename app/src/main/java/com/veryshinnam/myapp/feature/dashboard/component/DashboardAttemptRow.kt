package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun DashboardAttemptRow(
    title: String,
    count: Int,
    orangeColor: Color = colorResource(R.color.main_orange),
    failColor: Color = Color.LightGray,
    titleTextStyle: TextStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = Bold),
    subTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = SemiBold),
    modifier: Modifier = Modifier
) {
    val iconCount = count.coerceIn(1, 3) // 1 ~ 3 제한

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 기, 승, 전, 결
        Text(
            text = title,
            style = titleTextStyle
        )
        Spacer(Modifier.width(4.dp))

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
                        tint = if (isLast) orangeColor else failColor,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // 횟수 3보다 적을 때 빈 공간 채우기
            repeat(3 - iconCount) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }
        }

        // "N회"
        Text(text = "${count}회", style = subTextStyle.copy(color = orangeColor))
    }
}