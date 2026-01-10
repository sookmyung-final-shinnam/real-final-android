package com.veryshinnam.myapp.feature.creation.content.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationNextButton
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationStoryText
import com.veryshinnam.myapp.feature.creation.model.ConversationStep

@Composable
fun ConversationStoryContent(
    nextStory: String,
    step: ConversationStep,
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
                modifier = Modifier
                    .clearAndSetSemantics {
                        contentDescription =
                            if (step == ConversationStep.START) "동화 시작. $nextStory"
                            else nextStory
                    },
                onReplayClick = onReplayClick
            )
        }

        // 다음 버튼
        ConversationNextButton(
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .clearAndSetSemantics {
                    contentDescription =
                        if (step == ConversationStep.START) "다음 내용 듣기"
                        else "다음 질문 듣기"
                    role = Role.Button
                },
            onClick = onNextClick,
            enabled = nextEnabled
        )
    }
}