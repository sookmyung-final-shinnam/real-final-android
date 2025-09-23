package com.veryshinnam.myapp.feature.creation.content.conversation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationCommonText
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationRecordButton

@Composable
fun ConversationQuestionContent(
    question: String,
    isTtsSpeaking: Boolean,
    onReplayClick: () -> Unit,
    onRecordClick: () -> Unit,
    nextEnabled: Boolean,
    modifier: Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        // 질문 텍스트
        ConversationCommonText(
            text = question,
            painter = painterResource(R.drawable.img_llm_question),
            modifier = Modifier
                .weight(0.8f)
                .align(Alignment.CenterHorizontally),
            isTtsSpeaking = isTtsSpeaking,
            onReplayClick = onReplayClick
        )

        Spacer(Modifier.weight(0.05f))
        // 먀이크 버튼
        ConversationRecordButton(
            onRecordClick = {  onRecordClick() },
            enabled = nextEnabled,
            modifier = Modifier
                .weight(0.15f)
                .align(Alignment.CenterHorizontally)
        )
    }
}