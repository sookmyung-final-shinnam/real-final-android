package com.veryshinnam.myapp.common.component

import android.R.attr.textSize
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R

@Composable
fun TargetProgressBar(
    rect: Rect,
    steps: Int,
    lineColor: Color = colorResource(R.color.main_orange),
) {
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = Modifier
            .absoluteOffset(
                x = with(density) { rect.left.toDp() },
                y = with(density) { rect.top.toDp() }
            )
            .size(
                with(density) { rect.width.toDp() },
                with(density) { rect.height.toDp() }
            )
            .zIndex(30f)
    ) {

        // 원본과 동일한 비율 계산
        val circle = maxWidth * 0.05f
        val edgePadding = maxWidth * 0.12f
        val imageSize = circle * 3f
        val interval = (maxWidth - edgePadding * 2) / (steps - 1)

        val targetProgress = 0f  // ← (1 - 1) / (steps - 1)

        val centerY = maxHeight / 2
        val travelX = edgePadding + interval * (steps - 1) * targetProgress

        Canvas(modifier = Modifier.matchParentSize()) {
            val y = size.height / 2

            val startX = with(density) { edgePadding.toPx() }
            val intervalPx = with(density) { interval.toPx() }
            val circlePx = with(density) { circle.toPx() }

            // 직선
            drawLine(
                color = lineColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 8f
            )

            // 숫자 원
            for (i in 0 until steps) {
                val x = startX + i * intervalPx

                drawCircle(
                    color = lineColor,
                    radius = circlePx,
                    center = Offset(x, y)
                )

                drawContext.canvas.nativeCanvas.drawText(
                    (i + 1).toString(),
                    x,
                    y + (circlePx / 3),
                    Paint().apply {
                        color = android.graphics.Color.WHITE
                        textAlign = Paint.Align.CENTER
                        textSize = circlePx
                        isFakeBoldText = true // 볼드 효과
                    }
                )
            }
        }

        Image(
            painter = painterResource(R.drawable.img_progress),
            contentDescription = null,
            modifier = Modifier
                .offset(
                    x = travelX - (imageSize / 2),
                    y = centerY - (imageSize / 2)
                )
                .size(imageSize)
        )
    }
}