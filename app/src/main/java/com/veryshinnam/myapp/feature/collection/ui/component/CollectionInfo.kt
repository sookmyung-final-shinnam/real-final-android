package com.veryshinnam.myapp.feature.collection.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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

@Composable
fun CollectionInfo(
    modifier: Modifier = Modifier // 부모가 넘겨준 높이
) {
    Column(
        modifier = modifier.fillMaxHeight()
    ) {
       // 토끼 이미지
        Image(
            painter = painterResource(R.drawable.img_rabbit_cut),
            contentDescription = "설명하는 토끼 이미지",
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .align(Alignment.End)  // end 정렬
                .padding(end = 20.dp),
            contentScale = ContentScale.Fit
        )

        // 보관함 설명 텍스트
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(4.dp, colorResource(id = R.color.blue_gray)),
        ) {
            // 텍스트
            Text(
                "네가 만든 멋진 캐릭터들이야! \n" +
                        "좋아하는 캐릭터 5명을 표시해 줘. 그럼 홈 화면에서도 만나 볼 수 있어!",
                modifier = Modifier.padding(20.dp),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}