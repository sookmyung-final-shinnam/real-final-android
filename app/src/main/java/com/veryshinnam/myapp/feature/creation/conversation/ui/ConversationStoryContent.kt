package com.veryshinnam.myapp.feature.creation.conversation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.veryshinnam.myapp.feature.creation.conversation.component.ConversationNextButton
import com.veryshinnam.myapp.feature.creation.conversation.component.ConversationStoryText

@Composable
fun ConversationStoryContent(
    nextStory: String,
    isTtsSpeaking: Boolean,
    onReplayClick: () -> Unit = {},
    onNextClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // 다음 이야기 텍스트
        ConversationStoryText(
            nextStory = nextStory,
            isTtsSpeaking = isTtsSpeaking,
            modifier = Modifier.weight(0.8f).align(Alignment.CenterHorizontally),
            onReplayClick = onReplayClick
        )

        Spacer(Modifier.weight(0.05f))
        // 다음 버튼
        ConversationNextButton(
            modifier = Modifier.weight(0.15f).align(Alignment.CenterHorizontally),
            onClick = onNextClick
        )
    }
}