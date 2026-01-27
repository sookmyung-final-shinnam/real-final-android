package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
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
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = Bold),
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
                val backgroundColor = Brush.verticalGradient(
                    colors = if (isSelected) {
                        listOf(
                            colorResource(id = R.color.main_orange),
                            colorResource(id = R.color.dark_orange)
                        )
                    } else {
                        listOf(
                            colorResource(id = R.color.lemon_yellow),
                            colorResource(id = R.color.lemon_yellow),
                            Color.LightGray.copy(alpha = 0.3f)
                        )
                    }
                )
                val textColor = if (isSelected) Color.White else Color.Black

                Button(
                    onClick = { onSelect(style) },
                    shape = CircleShape,
                    border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = textColor
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(brush = backgroundColor, shape = CircleShape)
                        .semantics(true) {
                            role = Role.RadioButton
                            stateDescription = if (isSelected) "선택됨" else "선택 안 됨"
                        }
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