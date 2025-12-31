package com.veryshinnam.myapp.feature.creation.content.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationNextButton
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationStoryText

@Composable
fun ConversationStoryContent(
    nextStory: String,
    isTtsSpeaking: Boolean,
    onReplayClick: () -> Unit = {},
    onNextClick: () -> Unit,
    nextEnabled: Boolean,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 다음 이야기 텍스트
        Box(
            modifier.fillMaxSize()
                .weight(1f),
            Alignment.TopCenter
        ) {
            ConversationStoryText(
                nextStory = nextStory,
                isTtsSpeaking = isTtsSpeaking,
                modifier = Modifier,
                onReplayClick = onReplayClick
            )
        }

        // 다음 버튼
        ConversationNextButton(
            modifier = Modifier
                .fillMaxHeight(0.15f),
            onClick = onNextClick,
            enabled = nextEnabled
        )
    }
}