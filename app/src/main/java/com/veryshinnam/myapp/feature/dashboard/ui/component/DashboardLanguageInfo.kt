package com.veryshinnam.myapp.feature.dashboard.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.dashboard.model.LanguageData
import com.veryshinnam.myapp.feature.dashboard.model.PlayData

@Composable
fun DashboardLanguageInfo(
    username: String,
    languageData: LanguageData,
    modifier: Modifier
) {
    val vertical = 10.dp
    val horizontal = 24.dp

    Column(modifier = modifier.fillMaxWidth()) {

        // 상단 타이틀
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.main_orange),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp) // 위만 둥글게
                )
                .padding(vertical = vertical, horizontal = horizontal)
        ) {
            Text(
                text = "${username}의 언어 발달 정도",
                color = Color.White,
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold)
            )
        }

        // 하단 로그 텍스트
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp) // 아래만 둥글게
                )
                .border(
                    width = 4.dp,
                    color = colorResource(R.color.main_orange),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = vertical, horizontal = horizontal),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DashboardInfoRow("최근 가장 많이 말한 단어", languageData.recentSpokenWord, colorResource(R.color.light_orange))
                DashboardInfoRow("최근 새롭게 사용한 단어", languageData.recentNewWord, colorResource(R.color.light_orange))
                DashboardInfoRow("최근 사용한 비속어 정도", languageData.recentSlangLevel, colorResource(R.color.light_orange))
                DashboardInfoRow("언어 맥락 이해도", languageData.contextUnderstanding, colorResource(R.color.light_orange))
            }
        }
    }
}