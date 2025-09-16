package com.veryshinnam.myapp.feature.creation.conversation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ConversationCommonText(
    text: String, // 질문 또는 피드백 긍정 텍스트
    painter: Painter,
    isTtsSpeaking: Boolean,
    onReplayClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(0.9f).fillMaxHeight()
    ) {
        // 질문 텍스트
        Card(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f)
                .align(Alignment.TopCenter), // Box 위쪽
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(32.dp),
            border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                // 질문 텍스트
                Text(
                    text = text,
                    modifier = Modifier.align(Alignment.Center), // 가로+세로 중앙
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

        // 다람쥐 이미지
        Image(
            painter = painter,
            contentDescription = "다람쥐 이미지",
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .align(Alignment.BottomCenter), // Box 아래쪽
            contentScale = ContentScale.Fit
        )
    }
}