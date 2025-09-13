package com.veryshinnam.myapp.feature.creation.conversation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.veryshinnam.myapp.feature.creation.conversation.component.ConversationNextButton
import com.veryshinnam.myapp.feature.creation.conversation.component.ConversationQuestionText
import com.veryshinnam.myapp.feature.creation.conversation.component.ConversationRecordButton
import com.veryshinnam.myapp.feature.creation.conversation.component.ConversationStoryText

@Composable
fun ConversationQuestionContent(
    question: String,
    onReplayClick: () -> Unit,
    onRecordClick: () -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        // 질문 텍스트
        ConversationQuestionText(
            question = question,
            modifier = Modifier
                .weight(0.8f)
                .align(Alignment.CenterHorizontally),
            onReplayClick = onReplayClick
        )

        Spacer(Modifier.weight(0.05f))
        // 먀이크 버튼
        ConversationRecordButton(
            onRecordClick = {  onRecordClick() },
            modifier = Modifier
                .weight(0.15f)
                .align(Alignment.CenterHorizontally)
        )
    }
}