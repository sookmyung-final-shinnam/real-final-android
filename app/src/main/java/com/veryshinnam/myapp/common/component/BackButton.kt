package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun BackButton(
    modifier: Modifier,
    onBackClick: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    // 이전 버튼
    Row(
        modifier = modifier
            .clickable(onClick = {onBackClick()})
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "뒤로",
            tint = colorResource(R.color.main_orange)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = "뒤로",
            style = textStyle.copy(
                color = colorResource(id = R.color.main_orange)
            )
        )
    }
}