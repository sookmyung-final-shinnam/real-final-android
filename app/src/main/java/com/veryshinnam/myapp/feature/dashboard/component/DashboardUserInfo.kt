package com.veryshinnam.myapp.feature.dashboard.component

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun DashboardUserInfo(
    modifier: Modifier,
    username: String,
    interest: String,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    spanTextStyle: TextStyle = MaterialTheme.typography.displaySmall
) {
    Column(
        modifier = modifier.fillMaxHeight()
    ) {
        // 여우 이미지
        Image(
            painter = painterResource(R.drawable.img_fox_cut),
            contentDescription = "설명하는 여우 이미지",
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
            border = BorderStroke(4.dp, colorResource(id = R.color.deep_green)),
        ) {
            Text(
                text = buildAnnotatedString {
                    append("${username}의 최근 관심사는 ")

                    withStyle(
                        style = spanTextStyle
                            .copy(fontWeight = FontWeight.Bold)
                            .toSpanStyle()
                            .copy(color = colorResource(id = R.color.light_green))
                    ) {
                        append("\"${interest}\"") // 관심사 텍스트 강조
                    }

                    append("이야!")
                },
                modifier = Modifier.padding(20.dp),
                style = textStyle.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}