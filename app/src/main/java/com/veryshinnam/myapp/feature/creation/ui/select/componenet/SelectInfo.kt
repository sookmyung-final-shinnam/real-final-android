package com.veryshinnam.myapp.feature.creation.ui.select.componenet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import org.w3c.dom.Text

@Composable
fun SelectInfo(
    modifier: Modifier,
    text: String
) {
    Column(
        modifier = modifier
    ) {
        // 다람쥐 이미지
        Image(
            painter = painterResource(R.drawable.img_rabbit_cut),
            contentDescription = "다람쥐 이미지",
            modifier = Modifier
                .align(Alignment.End)  // end 정렬
                .weight(0.45f),  // 이미지 비율 조절
            contentScale = ContentScale.Fit
        )

        // 선택스크린 설명 텍스트
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
        ) {
            // 텍스트
            Text(
                text = text,
                modifier = Modifier.padding(20.dp),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}