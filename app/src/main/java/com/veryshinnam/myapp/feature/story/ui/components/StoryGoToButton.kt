package com.veryshinnam.myapp.feature.story.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.storage.enums.Filter

@Composable
fun StoryGoToButton(
    text: String = "▶ 보러가기",
    onButtonClick: () -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        // 버튼
        Button(
            onClick = { onButtonClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.blue_sky),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(end = 16.dp) // 버튼 간 여백
                .border(
                    width = 4.dp,
                    color = colorResource(id = R.color.blue_gray),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}