package com.veryshinnam.myapp.feature.dashboard.ui.interest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.dashboard.model.StatItem

/** 도넛 차트 색상 */
val CHART_COLORS = listOf(
    Color(0xFFFFC6C6),
    Color(0xFFFFE3A9),
    Color(0xFFBFF0D4),
    Color(0xFFBFD7FF),
    Color(0xFFE4C6FF)
)

/** 관심사 통계 섹션 (Theme/Background) */
@Composable
fun InterestSection(
    themeStats: List<StatItem>,
    backgroundStats: List<StatItem>
) {
    Text("✨ 관심사 섹션", style = MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(12.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AnalyticsCard(title = "테마", stats = themeStats, modifier = Modifier.weight(1f))
        AnalyticsCard(title = "배경", stats = backgroundStats, modifier = Modifier.weight(1f))
    }
}

/** 통계 카드 */
@Composable
private fun AnalyticsCard(
    title: String,
    stats: List<StatItem>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(12.dp))

            DonutChart(stats)
            Spacer(Modifier.height(12.dp))

            // 범례
            stats.forEachIndexed { i, s ->
                LegendItem(
                    color = CHART_COLORS[i % CHART_COLORS.size],
                    text = "${s.name}  ${String.format("%.0f", s.percent)}%"
                )
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

/** 도넛 차트 */
@Composable
private fun DonutChart(stats: List<StatItem>, size: Dp = 120.dp) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(size)) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            var start = -90f

            stats.forEachIndexed { i, item ->
                val sweep = (item.percent.toFloat() / 100f) * 360f

                drawArc(
                    color = CHART_COLORS[i % CHART_COLORS.size],
                    startAngle = start,
                    sweepAngle = sweep,
                    useCenter = false,
                    style = Stroke(width = 22.dp.toPx(), cap = StrokeCap.Round)
                )

                start += sweep
            }
        }
        Text("💖") // 중앙 아이콘
    }
}

/** 도넛 범례 */
@Composable
private fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(10.dp)) {
            drawCircle(color)
        }
        Spacer(Modifier.width(6.dp))
        Text(text, style = MaterialTheme.typography.bodySmall)
    }

}