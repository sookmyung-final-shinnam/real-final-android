package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun SelectionStyleButtons(
    title: String,
    styles: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold),
    subTextStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = Bold, textAlign = TextAlign.Center)
) {
    val topPadding = 8.dp

    Column(modifier) {
        // 버튼 위 제목
        Text(
            text = title,
            style = titleTextStyle
        )

        Row(
            modifier = Modifier.fillMaxSize().padding(top = topPadding),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            styles.forEach { style ->
                val isSelected = style == selected

                Button(
                    onClick = { onSelect(style) },
                    shape = CircleShape,
                    border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) colorResource(R.color.main_orange)
                                         else colorResource(R.color.lemon_yellow),
                        contentColor = if (isSelected) Color.White
                                       else Color.Black ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                ) {
                    Text(
                        text = style,
                        style = subTextStyle
                    )
                }
            }
        }
    }
}