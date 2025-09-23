package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import kotlinx.coroutines.delay

@Composable
fun ConversationEndContent(
    onHomeClick: () -> Unit,
) {

    LaunchedEffect(Unit) {
        delay(3000L) // 3초 뒤 자동 홈으로
        onHomeClick()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Spacer(Modifier.weight(0.1f))

        Image(
            painter = painterResource(R.drawable.img_story_end),
            contentDescription = "다람쥐 이미지",
            modifier = Modifier
                .weight(0.6f),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.weight(0.04f))

        // 완료 텍스트
        Card(
            modifier = Modifier.fillMaxWidth(0.8f).weight(0.3f),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(32.dp),
            border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                // 질문 텍스트
                Text(
                    text = "다 끝났어! 이제 동화가 생성 중이야.\n" +
                            "5분 뒤에 보관함에서 보자!",
                    modifier = Modifier.align(Alignment.Center), // 가로+세로 중앙
                    textAlign = TextAlign.Center, // 줄바꿈 고려 가운데 정렬
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
        Spacer(Modifier.weight(0.04f))


        // 홈버튼
        Button(
            modifier = Modifier.fillMaxWidth(0.5f).weight(0.1f),
            onClick = { onHomeClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.main_orange)
            ),
            shape = CircleShape,
        ) {
            Text(
                text = "홈으로",
                color = Color.Black,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }

        Spacer(Modifier.weight(0.1f))
    }
}