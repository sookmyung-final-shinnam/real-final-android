package com.veryshinnam.myapp.feature.creation.select.componenet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veryshinnam.myapp.R

@Composable
fun SelectCustomButton(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = colorResource(R.color.lemon_yellow),
                shape = RoundedCornerShape(48.dp)
            )
            .border(
                width = 2.dp,
                color = colorResource(R.color.main_orange),
                shape = RoundedCornerShape(48.dp)
            )
            .clickable { onButtonClick() },
        contentAlignment = Alignment.Center // 가로세로 중앙
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_keyboard),
                contentDescription = "직접 추가 이미지",
                contentScale = ContentScale.Fit,
                modifier = Modifier.weight(0.8f)
                    .fillMaxHeight(0.8f)
                    .padding(16.dp)
            )
            Text(
                text = "직접 추가하기",
                modifier = Modifier.offset(y=(-20).dp).weight(0.2f),
                color = colorResource(R.color.main_orange),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold)
            )
        }

    }
}