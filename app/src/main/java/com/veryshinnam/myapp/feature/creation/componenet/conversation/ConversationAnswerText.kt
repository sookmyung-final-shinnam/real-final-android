package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.veryshinnam.myapp.feature.creation.model.AnswerData

@Composable
fun ConversationAnswerText(
    answerData: AnswerData,
    isFinal: Boolean,
    modifier: Modifier,
    answerTextStyle: TextStyle
) {
    val answer = if (isFinal) {
        answerData.userAnswer // userAnswer 고정
    } else {
        // 실시간 partial 우선
        when {
            answerData.partialAnswer.isNotBlank() -> answerData.partialAnswer
            answerData.userAnswer.isNotBlank() -> answerData.userAnswer
            else -> "대답해 주세요!"
        }
    }

    Text(
        text = answer,
        style = answerTextStyle.copy(
            textAlign = TextAlign.Center
        ),
        modifier = modifier
    )
}