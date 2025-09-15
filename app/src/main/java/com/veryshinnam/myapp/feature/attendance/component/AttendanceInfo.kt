package com.veryshinnam.myapp.feature.attendance.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.home.ui.component.HomeUserItem

@Composable
fun AttendanceInfo(
    stamps: Int,
    attendances: Int,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)
        ) {
            // 돼지 이미지
            Image(
                painter = painterResource(R.drawable.img_pig_cut),
                contentDescription = "설명하는 돼지 이미지",
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomEnd)  // end 정렬
                    .padding(end = 20.dp),
                contentScale = ContentScale.Fit
            )

            // 스탬프 수
            HomeUserItem(
                painter= painterResource(R.drawable.img_stamp),
                contentDescription = "모은 스탬프 수",
                value = "${stamps}",
                spacer = 30.dp,
                color = colorResource(R.color.deep_pink),
                modifier = Modifier.padding(bottom = 8.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(0.5f)
            )
        }

        // 보관함 설명 텍스트
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(4.dp, colorResource(id = R.color.deep_pink)),
        ) {
            Text(
                text = "오늘도 만나서 반가워\n" +
                        "이번 달은 총 ${attendances}번 출석체크 했어!\n" +
                        "도장 10개당 나침반 1개인걸 잊지마~!",
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}