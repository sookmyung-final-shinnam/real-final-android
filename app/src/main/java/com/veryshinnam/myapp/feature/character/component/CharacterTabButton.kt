package com.veryshinnam.myapp.feature.character.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun CharacterTabButton(
    text: String = "Tab",
    alpha: Float = 0.5f,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxHeight(.3f)
            .aspectRatio(1f)
            .alpha(alpha), // 버튼 전체 투명도
        shape = CircleShape,
        border = BorderStroke(2.dp, colorResource(R.color.blue_gray)),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue_sky), // 배경색
            contentColor = Color.White                             // 글씨색
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}