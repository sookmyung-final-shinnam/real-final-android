package com.veryshinnam.myapp.feature.creation.select.componenet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.StepProgressBar

@Composable
fun SelectInfo(
    text: String,       // 설명 문구
    currentStep: Int,   // 현재 단계
    modifier: Modifier,
    totalStep: Int = 6, // 전체 단계 수
    endPadding: Dp = 20.dp // 이미지 오른쪽 패딩, 카드 텍스트 전체 패딩
) {
    Column(
        modifier = modifier.fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            // 진행바
            StepProgressBar(
                steps = totalStep,
                currentStep = currentStep,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.6f)
            )

            // 다람쥐 이미지 (BottomEnd 정렬용)
            Image(
                painter = painterResource(R.drawable.img_squirrel_cut),
                contentDescription = "설명하는 다람쥐 이미지",
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomEnd)  // end 정렬
                    .padding(end = endPadding),
                contentScale = ContentScale.Fit
            )
        }

        // 선택 스크린 설명 텍스트
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
        ) {
            // 텍스트
            Text(
                text = text,
                modifier = Modifier.padding(endPadding),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}