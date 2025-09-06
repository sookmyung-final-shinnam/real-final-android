package com.veryshinnam.myapp.feature.storage.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun StorageInfo(
    modifier: Modifier = Modifier // 부모가 넘겨준 높이
) {
    Column(
        modifier = modifier
        // End에 이미지 맞추기
    ) {
       // 토끼 이미지
        Image(
            painter = painterResource(R.drawable.img_rabbit),
            contentDescription = "설명하는 토끼 이미지",
            modifier = Modifier
                .align(Alignment.End) // end 정렬
                .weight(0.5f)
                .padding(end = 20.dp),
            contentScale = ContentScale.Fit
        )

        // 보관함 설명 텍스트
        Surface(
            // 테두리
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(4.dp, colorResource(id = R.color.blue_gray)),
            color = Color.White,
            tonalElevation = 2.dp
        ) {
            // 텍스트
            Text(
                "네가 만든 멋진 캐릭터들이야! \n" +
                        "좋아하는 캐릭터 5명을 표시해 줘. 그럼 홈 화면에서도 만나 볼 수 있어!",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}