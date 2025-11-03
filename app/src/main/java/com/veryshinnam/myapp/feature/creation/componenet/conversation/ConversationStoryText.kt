package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
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
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding)
    ) {
        // 다람쥐 이미지
        Image(
            painter = painterResource(R.drawable.img_squirrel_cut),
            contentDescription = "설명하는 다람쥐 이미지",
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
//                Button(
//                    onClick = { onReplayClick() },
//                    enabled = !isTtsSpeaking, // speaking 중이면 비활성화
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = colorResource(R.color.lemon_yellow),
//                    ),
//                    shape = CircleShape,
//                    border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
//                    modifier = Modifier.align(Alignment.End) // 오른쪽 아래
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .height(IntrinsicSize.Min)
//                    ) {
//                        Text(
//                            text = "다시 듣기",
//                            style = buttonTextStyle
//                        )
//
//                        Icon(
//                            imageVector = Icons.Default.Replay,
//                            contentDescription = "다시듣기 아이콘",
//                            tint = colorResource(id = R.color.main_orange),
//                            modifier = Modifier
//                                .padding(start = 2.dp)
//                                .fillMaxHeight()
//                        )
//                    }
//                }
            }
        }
    }
}