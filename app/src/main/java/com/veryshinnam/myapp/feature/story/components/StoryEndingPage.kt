package com.veryshinnam.myapp.feature.story.components

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
import androidx.compose.ui.unit.sp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeText

@Composable
fun StoryEndingPage(
    onRestart: () -> Unit,   // 첫 페이지로 버튼 클릭
    onHome: () -> Unit  // 홈 화면으로 버튼 클릭
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_yellow)), // 배경 색상
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // THE + 이미지 + END
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            StrokeText(
                "THE",
                Color.White,
                colorResource(R.color.main_orange),
                16f,
                fStyle = MaterialTheme.typography.displayLarge.copy(fontSize = 120.sp),
                FontWeight.Bold,
                Modifier
            )

            Image(
                painter = painterResource(R.drawable.img_story_ending),
                contentDescription = "마지막 페이지 이미지",
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f)
                    .offset(y=40.dp),
                contentScale = ContentScale.Fit
            )

            StrokeText(
                "END",
                Color.White,
                colorResource(R.color.main_orange),
                16f,
                fStyle = MaterialTheme.typography.displayLarge.copy(fontSize = 120.sp),
                FontWeight.Bold,
                Modifier
            )
        }

        // 버튼 영역
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .offset(y=(-20).dp),
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
                    .fillMaxHeight(0.7f)
                    .aspectRatio(3f)
                    .border(
                        width = 4.dp,
                        color = colorResource(id = R.color.blue_gray),
                        shape = CircleShape
                    )
            ) {
                Text(
                    "첫 페이지로",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Button(
                onClick = { onHome() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.blue_sky),
                    contentColor = Color.Black
                ),
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .aspectRatio(3f)
                    .border(
                        width = 4.dp,
                        color = colorResource(id = R.color.blue_gray),
                        shape = CircleShape
                    )
            ) {
                Text(
                    "홈 화면으로",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}
