package com.veryshinnam.myapp.common.component

import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.veryshinnam.myapp.R

@Composable
fun StepProgressBar(
    steps: Int,             // 총 진행 단계
    currentStep: Int,       // 현재 진행 단계
    lineColor: Color = colorResource(R.color.main_orange), // 선 색, 원 색
    prevColor: Color = colorResource(R.color.main_orange_50), // 이전 단계 색
    modifier: Modifier
) {
    val density = LocalDensity.current

    // 애니메이션 효과
    val animatedProgress by animateFloatAsState(
        targetValue = (currentStep - 1) / (steps - 1).toFloat(), // 진행 비율 (0.0f ~ 1.0f)
        animationSpec = tween(durationMillis = 600) // 0.6초 동안 부드럽게 이동
    )

    BoxWithConstraints(modifier = modifier) {
        val circle = maxWidth * 0.05f       // 숫자 원 (반지름)
        val edgePadding = maxWidth * 0.12f  // 양쪽 여백
        val imageSize = circle * 3f         // 이미지 크기
        val interval = (maxWidth - edgePadding * 2) / (steps - 1) // 각 단계 사이의 간격

        // 캔버스 (px 단위 사용)
        Canvas(modifier = Modifier.matchParentSize()) {
            val y = size.height / 2
            val startX = with(density) { edgePadding.toPx() }
            val intervalPx = with(density) { interval.toPx() } // interval dp > px 변환
            val circlePx = with(density) { circle.toPx() }     // circle dp > px 변환

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
                val circleColor = if (i < currentStep - 1) prevColor else lineColor

                drawCircle(color = circleColor, radius = circlePx, center = Offset(x, y))

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

        // 진행 단계 이미지
        val centerY = maxHeight / 2
        val travelX = edgePadding + interval * (steps - 1) * animatedProgress
        Image(
            painter = painterResource(R.drawable.img_progress),
            contentDescription = "현재 진행 단계",
            modifier = Modifier
                .offset(
                    x = travelX - (imageSize / 2),
                    y = centerY - (imageSize / 2) // 원 중앙과 겹치도록
                )
                .size(imageSize)
        )
    }
}