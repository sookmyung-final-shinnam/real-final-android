package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ConversationCommonText(
    text: String, // 질문 또는 피드백 긍정 텍스트
    painter: Painter,
    isTtsSpeaking: Boolean,
    onReplayClick: () -> Unit,
    modifier: Modifier,
    verticalPadding: Dp = 24.dp,
    spacePadding: Dp = 12.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(
        fontWeight = Bold,
        textAlign = TextAlign.Center
    ),
    onTextRect: (Rect) -> Unit,
    onImageRect: (Rect) -> Unit,
) {
    Box(modifier = modifier) {
        // 질문 텍스트
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .onGloballyPositioned { onTextRect(it.boundsInRoot()) }, // Box 위쪽
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(32.dp),
            border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(verticalPadding),
                verticalArrangement = Arrangement.spacedBy(spacePadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 질문 텍스트
                Text(
                    text = text,
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

        // 다람쥐 이미지
        Image(
            painter = painter,
            contentDescription = "다람쥐 이미지",
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .align(Alignment.BottomCenter)  // Box 아래쪽
                .onGloballyPositioned { onImageRect(it.boundsInRoot()) },
            contentScale = ContentScale.Fit
        )
    }
}