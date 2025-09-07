package com.veryshinnam.myapp.feature.story.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun StoryInfoFade(
    title: String,  // 동화책 제목
    tags: String,   // 동화책 태그
    desc: String,   // 동화책 설명
    modifier: Modifier // 부모에게서 받은 크기
) {
    Row(
        modifier = modifier.fillMaxSize(),
    ) {
        // 페이드 부분
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            colorResource(R.color.main_orange)
                        )
                    )
                )
        )


        // 동화 정보
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .background(colorResource(R.color.main_orange))
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = tags,
                    color = colorResource(R.color.background_yellow),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}