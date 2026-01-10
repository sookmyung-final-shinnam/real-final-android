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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationNeedsText
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationNextButton
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationRecordButton
import com.veryshinnam.myapp.feature.creation.model.FeedbackData

@Composable
fun ConversationFeedbackContent(
    feedback: FeedbackData,
    loopStep: Int,
    isTtsSpeaking: Boolean,
    onReplayClick: () -> Unit,
    onButtonClick: () -> Unit,
    nextEnabled: Boolean,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConversationNeedsText(
                feedback = feedback.text,
                tryNum = feedback.tryNum,
                painter = if (feedback.isPositive) painterResource(R.drawable.img_llm_feedback_positive)
                          else painterResource(R.drawable.img_llm_feedback_negative),
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 6.dp)
                    .align(Alignment.CenterHorizontally)
                    .clearAndSetSemantics {
                        contentDescription = buildString {
                            append("시도 횟수 ${feedback.tryNum}번째. ")
                            append(
                                if (feedback.isPositive) "답변 통과. "
                                else "답변 보충 필요."
                            )
                            append(feedback.text)
                        }
                    },
                isTtsSpeaking = isTtsSpeaking,
                onReplayClick = onReplayClick
            )

        if (feedback.isPositive) {
            ConversationNextButton(
                onClick ={ onButtonClick() },
                enabled = nextEnabled,
                modifier = Modifier
                    .fillMaxHeight(0.15f)
                    .align(Alignment.CenterHorizontally)
                .clearAndSetSemantics {
                    contentDescription = if (loopStep != 4) "다음 단계 진행" else "다음"
                    role = Role.Button
                }
            )
        } else { // 부정 - 먀이크 버튼
            ConversationRecordButton(
                onRecordClick = {  onButtonClick() },
                enabled = nextEnabled,
                modifier = Modifier
                    .fillMaxHeight(0.15f)
                    .align(Alignment.CenterHorizontally)
                    .clearAndSetSemantics {
                        contentDescription = "앞으로 대답 기회 ${3 - (feedback.tryNum)}번 남음. 마이크"
                        role = Role.Button
                    }
            )
        }
    }
}