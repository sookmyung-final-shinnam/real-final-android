package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ConversationStoryText(
    nextStory: String,
    isTtsSpeaking: Boolean,
    talkBack: String,
    onReplayClick: () -> Unit,
    modifier: Modifier,
    imageStartPadding: Dp = 20.dp,
    verticalPadding: Dp = 24.dp,
    spacePadding: Dp = 12.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = Bold,
        lineHeight = MaterialTheme.typography.titleMedium.lineHeight * 1.2f
    ),
) {
    val scrollState = rememberScrollState()

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
            modifier = Modifier
                .fillMaxWidth()
                .semantics(true) {
                    stateDescription = talkBack
                    contentDescription =  if (scrollState.canScrollForward) "스크롤 가능" else ""
                },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(verticalPadding, verticalPadding),
                verticalArrangement = Arrangement.spacedBy(spacePadding/2)
            ){
                Box {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 270.dp) // 최대 높이
                            .verticalScroll(scrollState)
                    ) {
                        Text(
                            text = nextStory.replace("", "\u200B"),
                            style = textStyle,
                            modifier = Modifier.clearAndSetSemantics { }
                        )
                    }

                    // 스크롤 가능 여부 효과
                    if (scrollState.canScrollForward) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .align(Alignment.BottomCenter)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Gray.copy(alpha = 0.3f)
                                        )
                                    ),
                                    shape = RoundedCornerShape(6.dp)
                                )
                        )
                    }
                }

                // 다시듣기
                ConversationReplayButton(
                    isTtsSpeaking = isTtsSpeaking,
                    onReplayClick = onReplayClick,
                    modifier = Modifier.align(Alignment.End)
                        .clearAndSetSemantics { }
                )
            }
        }
    }
}