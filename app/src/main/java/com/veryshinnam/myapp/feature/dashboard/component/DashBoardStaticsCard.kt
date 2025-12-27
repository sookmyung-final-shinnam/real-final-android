package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.dashboard.model.ChartStatData
import com.veryshinnam.myapp.feature.dashboard.model.StatData
import kotlin.math.cos
import kotlin.math.sin

// 대시보드 통계 분석 - 도넛 차트
@Composable
fun DashBoardStaticsCard(
    title: String,
    chartStats: List<ChartStatData>, // 비육
    listStats: List<StatData>,       // 횟수
    modifier: Modifier,
    spacer: Dp = 32.dp,
    cardCorner: Dp = 16.dp,
    cardColor: Color = Color.White,
    borderWidth: Dp = 4.dp,
    borderCorner: Dp = 16.dp,
    borderColor: Color = colorResource(R.color.deep_green),
    donutWith: Dp = 48.dp
) {
    // ui 변수
    val colors = listOf(
        colorResource(R.color.chart_1),
        colorResource(R.color.chart_2),
        colorResource(R.color.chart_3),
        colorResource(R.color.chart_4),
        colorResource(R.color.chart_5),
        colorResource(R.color.chart_6),
        colorResource(R.color.chart_7),
    )
    var pressed by remember { mutableStateOf(false) }

    // 왼쪽 테마
    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(cardColor, shape = RoundedCornerShape(cardCorner))
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(borderCorner)
            ),
        verticalArrangement = Arrangement.spacedBy(spacer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // 제목
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall.copy(
                    color = colorResource(R.color.light_green),
                    fontWeight = Bold
                ),
                modifier = Modifier.align(Alignment.Center)
            )

            // 터치 영역
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitFirstDown()
                                pressed = true

                                // Up 또는 Cancel 대기
                                while (true) {
                                    val event = awaitPointerEvent()
                                    if (event.changes.all { !it.pressed }) {
                                        break
                                    }
                                }

                                pressed = false
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // 원형 도움말
                DashboardHelpButton(borderColor = colorResource(R.color.light_green))
            }

            if (pressed) {
                Text("터치중")
            }
        }

        // 도넛 차트
        Box{
            Canvas(
                modifier = Modifier
                    .fillMaxWidth(0.52f)
                    .aspectRatio(1f)
                    .align(Alignment.Center)
            ) {
                var start = -90f   // 12시 방향
                val radius = size.minDimension / 2f // 반지름
                val labelRadius = radius - donutWith.toPx() / 2f + donutWith.toPx() * 0.5f // 0.5f 만큼 중앙에서 멀어지기

                chartStats.forEachIndexed { i, item ->
                    val sweep = item.ratio * 360f

                    // -- 차트 그리기
                    // 도넛링 그리기
                    drawArc(
                        color = colors[i],
                        startAngle = start,
                        sweepAngle = sweep.coerceAtLeast(0f),
                        useCenter = false, // 도넛
                        style = Stroke(width = donutWith.toPx(), cap = StrokeCap.Butt)
                    )

                    // -- 라벨 그리기
                    // 라벨 위치 계산
                    val middle = start + sweep / 2f
                    val angleRad = Math.toRadians(middle.toDouble())

                    val labelX = center.x + labelRadius * cos(angleRad).toFloat()
                    val labelY = center.y + labelRadius * sin(angleRad).toFloat()

                    // 라벨 설정
                    val paint = android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = (donutWith/3).toPx()
                        isFakeBoldText = true
                        isAntiAlias = true
                    }
                    val lineHeight = paint.textSize * 1.2f

                    // 이름
                    drawContext.canvas.nativeCanvas.drawText(
                        item.name,
                        labelX,
                        labelY - lineHeight / 2,
                        paint
                    )

                    // 퍼센트
                    drawContext.canvas.nativeCanvas.drawText(
                        "${(item.ratio * 100).toInt()}%",
                        labelX,
                        labelY + lineHeight / 2,
                        paint
                    )
                    start += sweep
                }
            }
        }

        // 리스트
        Column {
            Text("어저구")
        }
    }
}