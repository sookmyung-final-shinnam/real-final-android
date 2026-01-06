package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun SelectionPaletteGrid(
    title: String,
    colors: List<Pair<String, Int>>,
    selectedColorName: String,
    onSelect: (String) -> Unit,
    modifier: Modifier,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = Bold),
) {
    val topPadding = 8.dp
    val horizontalPadding = 10.dp
    val verticalPadding = 16.dp

    Column(modifier) {
        // 팔레트 위 제목
        Text(title,
            modifier = Modifier,
            style = titleTextStyle
        )

        // 팔레트
        Card(
            modifier = Modifier.fillMaxSize().padding(top=topPadding),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.lemon_yellow)),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(vertical = verticalPadding, horizontal = horizontalPadding)
            ) {
                items(colors) { (label, resId) ->
                    SelectionPaletteButton(
                        color = colorResource(resId),
                        label = label,
                        selected = label == selectedColorName,
                        onClick = { onSelect(label) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}