package com.veryshinnam.myapp.feature.creation.content.conversation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationNeedsText
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationNextButton
import com.veryshinnam.myapp.feature.creation.componenet.conversation.ConversationRecordButton
import com.veryshinnam.myapp.feature.creation.model.FeedbackData

@Composable
fun ConversationFeedbackContent(
    feedback: FeedbackData,
    isTtsSpeaking: Boolean,
    onReplayClick: () -> Unit,
    onButtonClick: () -> Unit,
    nextEnabled: Boolean,
    modifier: Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
            ConversationNeedsText(
                feedback = feedback.text,
                tryNum = feedback.tryNum,
                painter = if (feedback.isPositive) painterResource(R.drawable.img_feedback_positive)
                          else painterResource(R.drawable.img_feedback_negative),
                modifier = Modifier
                    .weight(0.8f)
                    .align(Alignment.CenterHorizontally),
                isTtsSpeaking = isTtsSpeaking,
                onReplayClick = onReplayClick
            )

        Spacer(Modifier.weight(0.05f))

        if (feedback.isPositive) {
            ConversationNextButton(
                onClick ={ onButtonClick() },
                enabled = nextEnabled,
                modifier = Modifier.weight(0.15f)
                .align(Alignment.CenterHorizontally))
        } else { // 부정 - 먀이크 버튼
            ConversationRecordButton(
                onRecordClick = {  onButtonClick() },
                enabled = nextEnabled,
                modifier = Modifier
                    .weight(0.15f)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}