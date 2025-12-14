package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.zIndex

@Composable
fun TargetImage(
    rect: Rect,
    image: Painter,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .absoluteOffset(
                x = with(density) { rect.left.toDp() },
                y = with(density) { rect.top.toDp() }
            )
            .size(
                with(density) { rect.width.toDp() },
                with(density) { rect.height.toDp() }
            )
            .zIndex(20f)
    )
}
