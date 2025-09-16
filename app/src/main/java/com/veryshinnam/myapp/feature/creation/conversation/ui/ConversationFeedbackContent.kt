package com.veryshinnam.myapp.feature.creation.conversation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.creation.conversation.component.ConversationNeedsText
import com.veryshinnam.myapp.feature.creation.conversation.component.ConversationNextButton
import com.veryshinnam.myapp.feature.creation.conversation.component.ConversationRecordButton
import com.veryshinnam.myapp.feature.creation.model.FeedbackData

@Composable
fun ConversationFeedbackContent(
    feedback: FeedbackData,
    isGoodFeedback: Boolean, // 피드백 긍정/부정
    onReplayClick: () -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
            ConversationNeedsText(
                feedback = if (isGoodFeedback) feedback.text
                           else  feedback.text + "\n다시 한번 말해줄래?",
                tryNum = feedback.tryNum,
                painter = if (isGoodFeedback) painterResource(R.drawable.img_feedback_positive)
                          else painterResource(R.drawable.img_feedback_negative),
                modifier = Modifier
                    .weight(0.8f)
                    .align(Alignment.CenterHorizontally),
                onReplayClick = onReplayClick
            )

        Spacer(Modifier.weight(0.05f))

        if (isGoodFeedback) {
            ConversationNextButton(
                onClick ={ onButtonClick() },
                modifier = Modifier.weight(0.15f)
                .align(Alignment.CenterHorizontally))
        } else { // 부정 - 먀이크 버튼
            ConversationRecordButton(
                onRecordClick = {  onButtonClick() },
                modifier = Modifier
                    .weight(0.15f)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}