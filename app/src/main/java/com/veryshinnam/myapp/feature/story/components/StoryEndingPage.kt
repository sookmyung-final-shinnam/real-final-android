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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeTitle

@Composable
fun StoryEndingPage(
    onRestart: () -> Unit,   // 첫 페이지로 버튼 클릭
    onHome: () -> Unit,  // 홈 화면으로 버튼 클릭
    spacePadding: Dp = 20.dp,
    endingTextStyle: TextStyle = MaterialTheme.typography.displayLarge,
    buttonVertical: Dp = 16.dp,
    buttonHorizontal:  Dp = 24.dp,
    buttonTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontWeight = FontWeight.Bold),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_yellow)), // 배경 색상
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // THE + 이미지 + END
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacePadding)
        ) {
            StrokeTitle(
                modifier = Modifier,
                titleText = "THE",
                titleColor = Color.White,
                strokeColor = colorResource(R.color.main_orange),
                strokeWidth = 20f,
                titleTextStyle = endingTextStyle.copy(
                    fontSize = endingTextStyle.fontSize * 1.4f
                )
            )

            Image(
                painter = painterResource(R.drawable.img_story_ending),
                contentDescription = "마지막 페이지 이미지",
                modifier = Modifier
                    .fillMaxWidth(0.35f),
                contentScale = ContentScale.Fit
            )

            StrokeTitle(
                modifier = Modifier,
                titleText = "END",
                titleColor = Color.White,
                strokeColor = colorResource(R.color.main_orange),
                strokeWidth = 20f,
                titleTextStyle = endingTextStyle.copy(
                    fontSize = endingTextStyle.fontSize * 1.4f
                )
            )
        }

        Spacer(Modifier.height(spacePadding/2))

        // 버튼 영역
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                contentPadding = PaddingValues(horizontal = buttonHorizontal, vertical = buttonVertical),
                modifier = Modifier
                    .border(
                        width = 4.dp,
                        color = colorResource(id = R.color.blue_gray),
                        shape = CircleShape
                    )
            ) {
                Text(
                    "첫 페이지로",
                    style = buttonTextStyle
                )
            }

            Button(
                onClick = { onHome() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.blue_sky),
                    contentColor = Color.Black
                ),
                shape = CircleShape,
                contentPadding = PaddingValues(horizontal = buttonHorizontal, vertical = buttonVertical),
                modifier = Modifier
                    .border(
                        width = 4.dp,
                        color = colorResource(id = R.color.blue_gray),
                        shape = CircleShape
                    )
            ) {
                Text(
                    "홈 화면으로",
                    style = buttonTextStyle
                )
            }
        }
    }
}
