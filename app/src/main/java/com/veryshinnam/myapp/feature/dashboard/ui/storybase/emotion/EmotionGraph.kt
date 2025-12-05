package com.veryshinnam.myapp.feature.dashboard.ui.storybase.emotion

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.feature.dashboard.model.EmotionItem
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun EmotionGraph(emotion: EmotionItem) {

    val values = listOf(
        emotion.joy.toFloat(),
        emotion.sadness.toFloat(),
        emotion.anger.toFloat(),
        emotion.fear.toFloat(),
        emotion.surprise.toFloat(),
        emotion.neutral.toFloat()
    )

    val labels = listOf("기쁨", "슬픔", "분노", "두려움", "놀람", "평온")

    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.inversePrimary,
        MaterialTheme.colorScheme.outline
    )

    val sum = values.sum().takeIf { it != 0f } ?: 1f

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(12.dp))

        Box(modifier = Modifier.size(180.dp), contentAlignment = Alignment.Center) {

            Canvas(modifier = Modifier.matchParentSize()) {

                val radius = size.width / 2f
                var startAngle = -90f

                values.forEachIndexed { index, v ->

                    if (v <= 0f) return@forEachIndexed // 값이 0이면 생략

                    val sweep = (v / sum) * 360f

                    drawArc(
                        color = colors[index],
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter = true,
                        size = Size(size.width, size.height)
                    )

                    val angleInRad = Math.toRadians((startAngle + sweep / 2).toDouble())
                    val labelRadius = radius * 0.55f // 안쪽에 배치

                    val labelX = (radius + cos(angleInRad) * labelRadius).toFloat()
                    val labelY = (radius + sin(angleInRad) * labelRadius).toFloat()

                    // 그래프 파이별 라벨 달기
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            "${labels[index]} ${(v * 100 / sum).toInt()}%",
                            labelX,
                            labelY,
                            android.graphics.Paint().apply {
                                color = Color.White.hashCode()
                                textAlign = android.graphics.Paint.Align.CENTER
                                textSize = 30f
                                isFakeBoldText = true
                            }
                        )
                    }

                    startAngle += sweep
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // 동화별 감정 요약
        Text(
            "📖 요약 : ${emotion.summary}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
    }

}