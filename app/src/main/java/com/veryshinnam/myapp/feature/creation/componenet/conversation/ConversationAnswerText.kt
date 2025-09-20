package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.creation.model.AnswerData

@Composable
fun ConversationAnswerText(
    answerData: AnswerData,
    isFinal: Boolean,
    modifier: Modifier
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
        color = colorResource(R.color.main_orange),
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier
    )
}