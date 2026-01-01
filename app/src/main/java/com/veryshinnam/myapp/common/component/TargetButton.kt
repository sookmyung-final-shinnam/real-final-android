package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R

@Composable
fun TargetButton(
    rect: Rect,
    onButtonClick: () -> Unit,
    containerColor: Color = colorResource(R.color.main_orange),
    contentColor: Color = Color.White,
    btnCorner: Dp = 48.dp,
    btnTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold)
) {
    val density = LocalDensity.current

    Button(
        onClick = onButtonClick,
        shape = RoundedCornerShape(btnCorner),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor),
        modifier = Modifier
            .absoluteOffset(
                x = with(density) { rect.left.toDp() },
                y = with(density) { rect.top.toDp() }
            )
            .size(
                with(density) { rect.width.toDp() },
                with(density) { rect.height.toDp() }
            )
            .zIndex(20f)
    ) {
        Text(
            text = "모험",
            style = btnTextStyle
        )
    }
}