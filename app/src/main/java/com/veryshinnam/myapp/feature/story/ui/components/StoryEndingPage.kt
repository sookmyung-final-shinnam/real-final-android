package com.veryshinnam.myapp.feature.story.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeText

@Composable
fun StoryEndingPage(
    onRestart: () -> Unit = {}, // 처음부터 버튼 클릭
    onHome: () -> Unit = {}     // 홈으로 버튼 클릭
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.yellow_80)), // 배경 색상
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // THE + 이미지 + END
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StrokeText(
                "THE",
                Color.White,
                colorResource(R.color.main_orange),
                8f,
                MaterialTheme.typography.displayLarge,
                FontWeight.Bold,
                Modifier
            )

            Image(
                painter = painterResource(R.drawable.img_story_ending),
                contentDescription = "마지막 페이지 이미지",
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )

            StrokeText(
                "END",
                Color.White,
                colorResource(R.color.main_orange),
                8f,
                MaterialTheme.typography.displayLarge,
                FontWeight.Bold,
                Modifier
            )
        }

        // 버튼 영역
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onRestart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.blue_sky),
                    contentColor = Color.Black
                ),
                shape = CircleShape,
                modifier = Modifier
                    .aspectRatio(9/5f)   // 1.8 비율
                    .border(
                        width = 4.dp,
                        color = colorResource(id = R.color.blue_gray),
                        shape = CircleShape
                    )
            ) {
                Text(
                    "처음부터",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = onRestart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.blue_sky),
                    contentColor = Color.Black
                ),
                shape = CircleShape,
                modifier = Modifier
                    .aspectRatio(9/5f)   // 1.8 비율
                    .border(
                        width = 4.dp,
                        color = colorResource(id = R.color.blue_gray),
                        shape = CircleShape
                    )
            ) {
                Text(
                    "홈으로",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
