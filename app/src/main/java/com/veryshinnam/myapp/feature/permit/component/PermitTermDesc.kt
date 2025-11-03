package com.veryshinnam.myapp.feature.permit.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun PermitTermDesc(
    modifier: Modifier = Modifier,
    descText: String,
    verticalPadding: Dp = 12.dp,
    horizontalPadding: Dp = 8.dp,
    descTextStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = SemiBold)
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color.White, shape = RoundedCornerShape(20.dp)
            )
            .border(
                border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
                shape = RoundedCornerShape(20.dp)
            )
            .verticalScroll(scrollState)
            .padding(vertical = verticalPadding, horizontal = horizontalPadding)
    ) {
        Text(
            text = descText,
            style = descTextStyle
        )
    }
}