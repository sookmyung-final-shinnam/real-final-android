package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ConversationStoryText(
    nextStory: String,
    isTtsSpeaking: Boolean,
    onReplayClick: () -> Unit,
    modifier: Modifier,
    imageStartPadding: Dp = 20.dp,
    verticalPadding: Dp = 24.dp,
    spacePadding: Dp = 12.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(
        fontWeight = Bold,
        lineHeight = MaterialTheme.typography.titleLarge.lineHeight * 1.2f
    ),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding)
    ) {
        // 다람쥐 이미지
        Image(
            painter = painterResource(R.drawable.img_squirrel_cut),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .padding(start = imageStartPadding)
                .graphicsLayer(scaleX = -1f), // 가로 반전
            contentScale = ContentScale.Fit
        )

        // 다음 스토리 텍스트
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(verticalPadding, verticalPadding),
                verticalArrangement = Arrangement.spacedBy(spacePadding)
            ){
                // 스토리 텍스트
                Text(
                    text = nextStory.replace("", "\u200B"),
                    style = textStyle
                )

                // 다시듣기
                ConversationReplayButton(
                    isTtsSpeaking = isTtsSpeaking,
                    onReplayClick = onReplayClick,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}