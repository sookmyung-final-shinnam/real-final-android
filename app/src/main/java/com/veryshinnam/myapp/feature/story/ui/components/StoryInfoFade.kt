package com.veryshinnam.myapp.feature.story.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R

@Composable
fun StoryInfoFade(
    title: String,  // 동화책 제목
    desc: String,    // 동화책 설명
    modifier: Modifier = Modifier // 부모에게서 받은 높이를 가져옴
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {

        // 1. 토끼 이미지
        Image(
            painter = painterResource(R.drawable.img_rabbit),
            contentDescription = "토끼",
            modifier = Modifier
                .fillMaxHeight(0.5f) // 높이 절반
                .aspectRatio(1f)       // 원본 이미지 비율
                .zIndex(1f)           // 스토리 정보(2) 위에
                .offset(y = 40.dp),           // 겹침 정도
            contentScale = ContentScale.Fit
        )

        // 2. 스토리 정보
        Card(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.blue_gray),
                    shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // 2-1. 제목과 줄거리
                Column {
                    Text(
                        text = title, // 제목
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = desc, // 설명
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                // 2-2. 버튼
                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxHeight(0.5f) // 스토리 정보(2) 높이의 절반
                        .aspectRatio(3f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue_gray)
                    )
                ) {
                    Text(
                        text = "보러가기",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}