package com.veryshinnam.myapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun HomeUserInfo(
    modifier: Modifier,
    username: String,
    stamps: Int,
    message: String,
    onSettingsClick: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    settingsTextStyle: TextStyle =  MaterialTheme.typography.labelSmall,
    cardPadding: Dp = 20.dp, // 텍스트 양옆 패딩
    bottomPadding: Dp = 8.dp  // 나침반 아래 패딩

) {
    Column(modifier.fillMaxHeight()) {
        // 유저 정보 - 생성한 캐릭터 수, 포인트
        Box(
            modifier = Modifier
                .weight(4f)
                .fillMaxWidth()
        ) {
            // 다람쥐 이미지
            Image(
                painter = painterResource(R.drawable.img_home_squirrel),
                contentDescription = "다람쥐 이미지",
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomStart)  // start 정렬
                    .padding(start = 26.dp),
                contentScale = ContentScale.Fit
            )

            // 나침반 수
            HomeUserItem(
                painter = painterResource(R.drawable.img_compass),
                contentDescription = "모은 나침반 수",
                value = "$stamps",
                color = colorResource(R.color.main_orange),
                modifier = Modifier
                    .padding(bottom = bottomPadding)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(0.5f)
            )
        }

        // 유저 정보 - 닉네임
        Box(
            modifier = Modifier
                .weight(6f)
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(4.dp, colorResource(R.color.main_orange), RoundedCornerShape(16.dp))
                .padding(cardPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // 닉네임 + 환경설정 버튼
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "안녕 ${username}!",
                        style = textStyle.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .clickable(onClick = onSettingsClick)
                    ) {
                        Text(
                            text = "환경설정",
                            style = settingsTextStyle.copy(
                                color = colorResource(id = R.color.main_orange)
                            )
                        )

                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "환경설정 아이콘",
                            tint = colorResource(id = R.color.main_orange),
                            modifier = Modifier
                                .padding(start = 2.dp)
                                .fillMaxHeight()
                        )
                    }
                }

                // 랜덤 메시지
                Text(
                    text = message,
                    style = textStyle.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        }
    }
}
