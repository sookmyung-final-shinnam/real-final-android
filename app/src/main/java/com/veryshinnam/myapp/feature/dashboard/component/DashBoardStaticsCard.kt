package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.model.DashboardInit
import com.veryshinnam.myapp.feature.dashboard.model.ChartStatData
import com.veryshinnam.myapp.feature.dashboard.model.StatData
import kotlin.math.cos
import kotlin.math.sin

// 대시보드 통계 분석
@Composable
fun DashBoardStaticsCard(
    title: String,
    onHelpRect: (Rect) -> Unit = {},
    chartStats: List<ChartStatData>, // 비육
    listStats: List<StatData>,       // 횟수
    modifier: Modifier,
    spacer: Dp = 12.dp,
    cardCorner: Dp = 16.dp,
    cardColor: Color = Color.White,
    borderWidth: Dp = 4.dp,
    borderCorner: Dp = 16.dp,
    borderColor: Color = colorResource(R.color.deep_green),
    lGreenColor: Color = colorResource(R.color.light_green)
) {
    fun getGroupName(
        itemName: String,
        chartStats: List<ChartStatData>
    ): String {
        return if (chartStats.any { it.name == itemName }) {
            itemName
        } else {
            DashboardInit.CUSTOM
        }
    }

    // ui 변수
    val colors = listOf(
        colorResource(R.color.chart_1),
        colorResource(R.color.chart_2),
        colorResource(R.color.chart_3),
        colorResource(R.color.chart_4),
        colorResource(R.color.chart_5),
        colorResource(R.color.chart_6),
        colorResource(R.color.chart_7)
    ) // 사용될 차트 색상

    val colorMap = remember(chartStats) {
        chartStats.mapIndexed { index, chart ->
            chart.name to colors[index]
        }.toMap()
    } // 차트 색상 매핑

    val listState = rememberLazyListState()
    val listHeight = 180.dp

    var helpPressed by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(cardColor, shape = RoundedCornerShape(cardCorner))
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(borderCorner)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = spacer*2),
            verticalArrangement = Arrangement.spacedBy(spacer*2),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(modifier = Modifier.fillMaxWidth()) {
                // 제목
                Text(
                    text = title,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = lGreenColor,
                        fontWeight = Bold
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )

                // 원형 도움말
                DashboardHelpButton(
                    onPress = { pressed ->
                        helpPressed = pressed
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = spacer)
                        .onGloballyPositioned { coords ->
                            val rect = coords.boundsInRoot()
                            if (rect.width > 0 && rect.height > 0) {
                                onHelpRect(rect)
                            }
                        }
                )
            }

            // --- 도움말 텍스트 + 파이 차트 + 리스트
            Box {
                if (helpPressed) {
                    DashboardHelpText(
                        text = "지금까지 생성한 동화의 $title 통계 목록입니다.\n\n" +
                                "스크롤하여 더 자세한 내용을 확인해 보세요!",
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(horizontal = spacer)
                            .offset(y = (-16).dp)
                            .zIndex(10f)
                    )
                }

                // --- 파이 차트 + 리스트
                Column(
                    verticalArrangement = Arrangement.spacedBy(spacer*2),
                ) {
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(
                            modifier = Modifier.size(maxWidth * 0.85f)
                        ) {

                            var start = -90f   // 12시 방향
                            chartStats.forEachIndexed { index, item ->
                                val sweep = item.ratio * 360f
                                if (sweep <= 0f) return@forEachIndexed

                                // 파이 그리기
                                drawArc(
                                    color = colors[index].copy(alpha = 0.8f),
                                    startAngle = start,
                                    sweepAngle = sweep,
                                    useCenter = true
                                )

                                // 파이간 구분선
                                drawArc(
                                    color = Color.White,
                                    startAngle = start,
                                    sweepAngle = sweep,
                                    useCenter = true,
                                    style = Stroke(width = 1.dp.toPx())
                                )

                                start += sweep
                            }

                            start = -90f   // 초기화
                            chartStats.forEachIndexed { index, item ->
                                val sweep = item.ratio * 360f
                                if (sweep <= 0f) return@forEachIndexed

                                // 라벨 위치 계산
                                val middleAngle = start + sweep / 2f
                                val rad = Math.toRadians(middleAngle.toDouble())
                                val labelRadius = size.minDimension / 2f * 0.68f

                                val labelX = center.x + labelRadius * cos(rad).toFloat()
                                val labelY = center.y + labelRadius * sin(rad).toFloat()

                                // 라벨 설정
                                val strokePaint = android.graphics.Paint().apply {
                                    color = colors[index].toArgb()
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    textSize = 16.dp.toPx()
                                    style = android.graphics.Paint.Style.STROKE
                                    strokeWidth = 8f
                                    isFakeBoldText = true
                                    isAntiAlias = true
                                }
                                val fillPaint = android.graphics.Paint().apply {
                                    color = android.graphics.Color.WHITE
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    textSize = 16.dp.toPx()
                                    style = android.graphics.Paint.Style.FILL
                                    isFakeBoldText = true
                                    isAntiAlias = true
                                }
                                val lineHeight = fillPaint.textSize * 1.2f

                                // 이름
                                drawContext.canvas.nativeCanvas.drawText(
                                    item.name,
                                    labelX,
                                    labelY - lineHeight / 2,
                                    strokePaint
                                )
                                drawContext.canvas.nativeCanvas.drawText(
                                    item.name,
                                    labelX,
                                    labelY - lineHeight / 2,
                                    fillPaint
                                )

                                // 퍼센트
                                drawContext.canvas.nativeCanvas.drawText(
                                    "${(item.ratio * 100).toInt()}%",
                                    labelX,
                                    labelY + lineHeight / 2,
                                    strokePaint
                                )
                                drawContext.canvas.nativeCanvas.drawText(
                                    "${(item.ratio * 100).toInt()}%",
                                    labelX,
                                    labelY + lineHeight / 2,
                                    fillPaint
                                )

                                start += sweep
                            }
                        }
                    }

                    // 리스트
                    Box {
                        LazyColumn (
                            state = listState,
                            modifier = Modifier.fillMaxWidth()
                                .height(listHeight)
                                .padding(start = cardCorner, end = cardCorner, bottom = spacer*2),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(listStats) { stat ->
                                val groupName = getGroupName(stat.name, chartStats)
                                val color = colorMap[groupName] ?: Color.Gray
                                DashBoardStaticsRow(color, stat.name, stat.count)
                            }
                        }

                        if (listState.canScrollForward) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .align(Alignment.BottomCenter)
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                Color.Transparent,
                                                Color.Gray.copy(alpha = 0.4f)
                                            )
                                        ),
                                        RoundedCornerShape(cardCorner)
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}