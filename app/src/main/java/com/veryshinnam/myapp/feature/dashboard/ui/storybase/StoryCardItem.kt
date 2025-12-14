package com.veryshinnam.myapp.feature.dashboard.ui.storybase

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.dashboard.model.EmotionItem
import com.veryshinnam.myapp.feature.dashboard.model.LanguageItem
import com.veryshinnam.myapp.feature.dashboard.ui.storybase.language.LanguageGraph
import com.veryshinnam.myapp.feature.dashboard.ui.storybase.emotion.EmotionGraph

/** 스토리 학습/정서 분석 카드 아이템 (앞/뒤 전환 기능) */
@Composable
fun StoryCardItem(
    lang: LanguageItem,
    emotion: EmotionItem,
    isCenter: Boolean = false,
    onClick: () -> Unit
) {
    var isFront by remember { mutableStateOf(true) }   // 앞/뒤 전환 상태

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(480.dp)
            .padding(4.dp)
            .clickable {
                isFront = !isFront        // 카드 터치 시 앞/뒤 전환
            },
//            .then(
//                if (isCenter) Modifier.border(
//                    width = 2.dp,
//                    color = MaterialTheme.colorScheme.primary,
//                    shape = RoundedCornerShape(12.dp)
//                ) else Modifier
//            ),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.lemon_yellow)
        ),
        border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(if (isCenter) 8.dp else 4.dp)
    ) {

        Box(modifier = Modifier.padding(16.dp)) {

            // 좌측 스토리 버튼
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.TopStart) // 상단 좌측에 고정
                    .clickable { onClick() }
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("📖", style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 56.dp, top = 8.dp)
            ) {
                if (isFront) {

                    // ---------------------- 앞면 : 언어 분석 ----------------------
                    Text("📘 언어 발달 / 학습", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(16.dp))
                    LanguageGraph(lang)

                    Spacer(Modifier.weight(1f))
                    Text(
                        "👉 탭해서 정서 분석 보기",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(R.color.main_orange)
                    )

                } else {

                    // ---------------------- 뒷면 : 정서 분석 ----------------------
                    Text("😊 정서 분석", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(16.dp))
                    EmotionGraph(emotion)

                    Spacer(Modifier.weight(1f))
                    Text(
                        "👉 탭해서 언어 분석 보기",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(R.color.main_orange)
                    )
                }
            }
        }
    }

}