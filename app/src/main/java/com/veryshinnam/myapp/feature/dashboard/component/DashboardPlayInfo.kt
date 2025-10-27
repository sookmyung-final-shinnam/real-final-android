package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.dashboard.model.PlayData

@Composable
fun DashboardLanguageInfo(
    modifier: Modifier,
    username: String,
    playData: PlayData,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    verticalPadding: Dp = 20.dp,
    horizontalPadding: Dp = 16.dp
) {
    Column(modifier = modifier.fillMaxWidth()) {

        // 상단 타이틀
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.deep_green),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp) // 위만 둥글게
                )
                .padding(horizontalPadding)
        ) {
            Text(
                text = "${username}의 활동 로그",
                style =textStyle.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        // 하단 로그 텍스트
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp) // 아래만 둥글게
                )
                .border(
                    width = 4.dp,
                    color = colorResource(R.color.deep_green),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = verticalPadding, horizontal = horizontalPadding),
                verticalArrangement = Arrangement.spacedBy(verticalPadding / 2),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DashboardInfoRow("가장 많이 고른 테마", playData.mostSelectedTheme, colorResource(R.color.light_green))
                DashboardInfoRow("가장 많이 고른 배경", playData.mostSelectedBackground, colorResource(R.color.light_green))
                DashboardInfoRow("최근 앱 사용 시간", playData.recentPlayTime, colorResource(R.color.light_green))
                DashboardInfoRow("생성된 동화 다시 읽은 횟수", playData.storyReplayCount, colorResource(R.color.light_green))
            }
        }
    }
}