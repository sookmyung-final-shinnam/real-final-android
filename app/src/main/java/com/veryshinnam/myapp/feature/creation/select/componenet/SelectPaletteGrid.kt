package com.veryshinnam.myapp.feature.creation.select.componenet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun SelectPaletteGrid(
    title: String,
    colors: List<Pair<String, Int>>,
    selectedColorName: String,
    onSelect: (String) -> Unit,
    modifier: Modifier
) {
    val topPadding = 8.dp
    val horizontalPadding = 10.dp
    val verticalPadding = 16.dp

    Column(modifier) {
        // 팔레트 위 제목
        Text(title,
            modifier = Modifier,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))

        // 팔레트
        Card(
            modifier = Modifier.fillMaxSize().padding(top=topPadding),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.lemon_yellow)),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
        ) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(2), // 2행
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalArrangement = Arrangement.spacedBy(topPadding),
                contentPadding = PaddingValues(vertical = verticalPadding, horizontal = horizontalPadding)
            ) {
                items(colors) { (label, resId) ->
                    val color = colorResource(resId)
                    val selected = label == selectedColorName
                    SelectPaletteButton(
                        color = color,
                        selected = selected,
                        onClick = { onSelect(label) },
                        modifier = Modifier.aspectRatio(1f) // 1:1 비율
                    )
                }
            }
        }
    }
}