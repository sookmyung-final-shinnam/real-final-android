package com.veryshinnam.myapp.feature.creation.conversation.component

import android.R.attr.scaleX
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ConversationStoryText(
    nextStory: String,
    isTtsSpeaking: Boolean,
    onReplayClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(0.9f)
    ) {
        // 다람쥐 이미지
        Image(
            painter = painterResource(R.drawable.img_squirrel_cut),
            contentDescription = "설명하는 다람쥐 이미지",
            modifier = Modifier
                .weight(0.2f)
                .padding(start = 16.dp)
                .graphicsLayer(scaleX = -1f), // 가로 반전
            contentScale = ContentScale.Fit
        )

        // 다음 스토리 텍스트
        Card(
            modifier = Modifier.fillMaxSize().weight(0.8f),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(48.dp),
            border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                // 스토리 텍스트
                Text(
                    text = nextStory,
                    modifier = Modifier.align(Alignment.Center), // 가로+세로 중앙 위치
                    textAlign = TextAlign.Center, // 줄바꿈 고려 가운데 정렬
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )

                // 다시듣기
                Button(
                    onClick = { onReplayClick() },
                    enabled = !isTtsSpeaking, // speaking 중이면 비활성화
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.lemon_yellow),
                    ),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
                    modifier = Modifier.align(Alignment.BottomEnd) // 오른쪽 아래
                ) {
                    Text(
                        "다시 듣기",
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}