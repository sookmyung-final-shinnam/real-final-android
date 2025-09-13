package com.veryshinnam.myapp.feature.creation.conversation.component

import android.R.attr.scaleX
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ConversationStoryText(
    nextStory: String,
    onReplayClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(0.8f)
    ) {
        // 다람쥐 이미지
        Image(
            painter = painterResource(R.drawable.img_squirrel_cut),
            contentDescription = "설명하는 다람쥐 이미지",
            modifier = Modifier
                .weight(0.3f)
                .graphicsLayer(scaleX = -1f), // 가로 반전
            contentScale = ContentScale.Fit
        )

        // 다음 이야기 텍스트
        Card(
            modifier = Modifier.fillMaxSize().weight(0.7f),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
        ) {
            // 텍스트
            Text(
                text = nextStory,
                modifier = Modifier.padding(20.dp),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium
            )

            // 다시듣기
            Button(
                onClick = { onReplayClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.lemon_yellow),
                ),
                shape = CircleShape,
                border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
                modifier = modifier.align(Alignment.End).fillMaxHeight(0.2f)
            ) {
                Text(
                    "다시 듣기",
                    color = Color.Black,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}