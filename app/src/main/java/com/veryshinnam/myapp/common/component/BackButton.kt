package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun BackButton(
    onBackClick: () -> Unit,
    modifier: Modifier
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
        Text("뒤로",
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.main_orange)
        )
    }
}