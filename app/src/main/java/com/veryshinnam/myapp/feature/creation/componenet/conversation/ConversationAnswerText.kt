package com.veryshinnam.myapp.feature.creation.componenet.conversation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.creation.model.AnswerData

@Composable
fun ConversationAnswerText(
    answerData: AnswerData,
    isFinal: Boolean,
    modifier: Modifier,
    answerTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(color = colorResource(R.color.main_orange))
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
        style = answerTextStyle,
        modifier = modifier
    )
}