package com.veryshinnam.myapp.feature.creation.content.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 질문 텍스트
        ConversationCommonText(
            text = question,
            painter = painterResource(R.drawable.img_llm_question),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f)
                .padding(bottom = 6.dp),
            isTtsSpeaking = isTtsSpeaking,
            onReplayClick = onReplayClick,
        )

        // 먀이크 버튼
        ConversationRecordButton(
            onRecordClick = {  onRecordClick() },
            enabled = nextEnabled,
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .align(Alignment.CenterHorizontally)
        )
    }
}