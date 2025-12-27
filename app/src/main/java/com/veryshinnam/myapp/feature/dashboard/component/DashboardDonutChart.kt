package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.dashboard.model.ChartStatData
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun DashboardDonutChart(
    chartStats: List<ChartStatData>,
    modifier: Modifier = Modifier,
    donutWidth: Dp = 36.dp,
    colors: List<Color> = listOf(
        colorResource(R.color.chart_1),
        colorResource(R.color.chart_2),
        colorResource(R.color.chart_3),
        colorResource(R.color.chart_4),
        colorResource(R.color.chart_5),
        colorResource(R.color.chart_6),
        colorResource(R.color.chart_7),
    )
) {
    // 비어있는 경우
    if (chartStats.isEmpty()) return

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth(0.52f)
                .aspectRatio(1f)
                .align(Alignment.Center)
        ) {
            var startAngle = -90f // 12시 방향 시작
            val strokePx = donutWidth.toPx()
            val radius = size.minDimension / 2f

            // 라벨 위치 (도넛 중앙보다 살짝 바깥)
            val labelRadius =
                radius - strokePx / 2f + strokePx * 0.5f

            chartStats.forEachIndexed { index, item ->
                val sweepAngle = item.ratio * 360f
                if (sweepAngle <= 0f) return@forEachIndexed

                // --- 도넛 호 ---
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = strokePx,
                        cap = StrokeCap.Butt
                    )
                )

                // -- 라벨 그리기
                // 위치 계산
                val middleAngle = startAngle + sweepAngle / 2f
                val angleRad = Math.toRadians(middleAngle.toDouble())

                val labelX =
                    center.x + labelRadius * cos(angleRad).toFloat()
                val labelY =
                    center.y + labelRadius * sin(angleRad).toFloat()

                // 라벨 설정
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = strokePx / 3f
                    isFakeBoldText = true
                    isAntiAlias = true
                }

                val lineHeight = paint.textSize * 1.2f

                // --- 이름 ---
                drawContext.canvas.nativeCanvas.drawText(
                    item.name,
                    labelX,
                    labelY - lineHeight / 2,
                    paint
                )

                // --- 퍼센트 ---
                drawContext.canvas.nativeCanvas.drawText(
                    "${(item.ratio * 100).toInt()}%",
                    labelX,
                    labelY + lineHeight / 2,
                    paint
                )

                startAngle += sweepAngle
            }
        }
    }
}
