package com.veryshinnam.myapp.feature.permit.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun PermitTermTitle(
    modifier: Modifier = Modifier,
    title: String,
    isRequired: Boolean = true,
    isChecked: Boolean = false,  // 체크 상태
    onClick: () -> Unit = {},
    textColor: Color = Color.Black,
    textSize: TextStyle = MaterialTheme.typography.headlineMedium,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .wrapContentWidth()
            .clickable{ onClick() }
    ) {
        Icon(
            imageVector =Icons.Default.Check,
            contentDescription = title,
            tint = if (isChecked) colorResource(R.color.main_orange) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            color = textColor,
            style = textSize,
        )

        Spacer(modifier = Modifier.width(4.dp))

        if (isRequired) {
            Text(
                text = "(필수)",
                color = Color.Red,
                style = textSize,
            )
        }
    }
}