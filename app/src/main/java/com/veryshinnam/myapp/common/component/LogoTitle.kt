package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun LogoTitle(
    modifier: Modifier,
    subText: String = "대화하며 나만의 동화 만들기",
    mainText: String = "Storictor",
    subTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    mainTextStyle: TextStyle = MaterialTheme.typography.displayLarge
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 로고 위 문구 텍스트
        Text(
            text = subText,
            color = colorResource(R.color.brand_orange),
            style = subTextStyle.copy(fontWeight = FontWeight.Bold)
        )

        // 로고
        LogoBar(
            logoText = mainText,
            logoTextStyle = mainTextStyle,
            verticalPadding = 0.dp
        )
    }
}