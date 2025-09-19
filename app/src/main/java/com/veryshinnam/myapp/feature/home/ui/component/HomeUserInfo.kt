package com.veryshinnam.myapp.feature.home.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun HomeUserInfo(
    username: String,
    stamps: Int,
    randomMessage: String,
    onSettingsClick: () -> Unit,
    modifier: Modifier,
    bottomPadding: Dp = 8.dp  // 나침반 아래 패딩
) {
    Column(modifier.fillMaxHeight()) {
        // 유저 정보 - 생성한 캐릭터 수, 포인트
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // 다람쥐 이미지
            Image(
                painter = painterResource(R.drawable.img_home_squirrel),
                contentDescription = "다람쥐 이미지",
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomStart)  // start 정렬
                    .padding(start = 40.dp),
                contentScale = ContentScale.Fit
            )


            // 나침반 수
            HomeUserItem(
                painter = painterResource(R.drawable.img_compass),
                contentDescription = "모은 나침반 수",
                value = "${stamps}",
                color = colorResource(R.color.main_orange),
                modifier = Modifier
                    .padding(bottom = bottomPadding)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(0.5f)
            )
        }

        // 유저 정보 - 닉네임
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "안녕 ${username}! \n${randomMessage}",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp)
                )

                // 환경 설정 버튼
                IconButton(
                    onClick = { onSettingsClick() },
                    modifier = Modifier
                        .fillMaxHeight(0.6f) // 버튼 높이 조절
                        .aspectRatio(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "환경설정",
                        modifier = Modifier.fillMaxSize().padding(end = 20.dp),
                        tint = colorResource(id = R.color.main_orange)
                    )
                }
            }
        }
    }
}