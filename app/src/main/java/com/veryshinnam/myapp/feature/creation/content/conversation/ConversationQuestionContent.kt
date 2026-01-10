package com.veryshinnam.myapp.feature.creation.content.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.InstructionText
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationCommonText
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationRecordButton

@Composable
fun ConversationQuestionContent(
    question: String,
    isTtsSpeaking: Boolean,
    onReplayClick: () -> Unit,
    onRecordClick: () -> Unit,
    nextEnabled: Boolean,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 질문 텍스트
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            ConversationCommonText(
                text = question,
                painter = painterResource(R.drawable.img_llm_question),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 6.dp),
                isTtsSpeaking = isTtsSpeaking,
                onReplayClick = onReplayClick,
            )

            InstructionText(
                text = "화면 아래에 있는\n마이크 버튼을 눌러 대답해 주세요.\n버튼을 누르면 3초 동안 말할 수 있어요!",
                textStyle = textStyle,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp)
            )
        }
        // 먀이크 버튼
        ConversationRecordButton(
            onRecordClick = { onRecordClick() },
            enabled = nextEnabled,
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .align(Alignment.CenterHorizontally)
                .semantics{
                    contentDescription = "마이크"
                }
        )
    }
}