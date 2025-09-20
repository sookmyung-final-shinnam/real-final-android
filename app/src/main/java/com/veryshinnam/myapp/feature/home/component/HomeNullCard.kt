package com.veryshinnam.myapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.veryshinnam.myapp.R

@Composable
fun HomeNullCard(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 상단 물음표 이미지
        Image(
            painter = painterResource(id = R.drawable.img_question),
            contentDescription = "빈 카드",
            modifier = Modifier
                .fillMaxWidth()
                .weight(7f),
            contentScale = ContentScale.Fit
        )

        // 안내 문구
        Text(
            text = "보관함에서 즐겨찾기 버튼으로 홈 화면에 캐릭터를 추가하세요!",
            modifier = Modifier.fillMaxWidth().weight(3f),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}