package com.veryshinnam.myapp.feature.creation.select.componenet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

@Composable
fun SelectStyleButtons(
    title: String,
    styles: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier
) {
    val topPadding = 8.dp

    Column(modifier) {
        // 버튼 위 제목
        Text(title,
            modifier = Modifier,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))

        Row(
            modifier = Modifier.fillMaxSize().padding(top = topPadding),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            styles.forEach { style ->
                val isSelected = style == selected

                Button(
                    onClick = { onSelect(style) },
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) colorResource(R.color.main_orange)
                                         else colorResource(R.color.lemon_yellow),
                        contentColor = if (isSelected) Color.White
                                       else Color.Black ),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                ) {
                    Text(style,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                }
            }
        }
    }
}