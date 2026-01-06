package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

// 원형 아이콘 버튼
@Composable
fun CircleNextButton(
    icon: ImageVector,
    desc: String,
    onClick: () -> Unit,
    modifier: Modifier,
    containerColor: Color = colorResource(R.color.main_orange),
    contentColor: Color = Color.White,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(color = colorResource(R.color.main_orange), fontWeight = Bold)
) {
    val spacer = 4.dp

    Column(
        modifier = modifier
            .clickable{ onClick() },
        verticalArrangement = Arrangement.spacedBy(spacer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.weight(1f)
                .aspectRatio(1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = desc,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = desc,
            style = textStyle,
            textAlign = TextAlign.Center,
        )
    }
}