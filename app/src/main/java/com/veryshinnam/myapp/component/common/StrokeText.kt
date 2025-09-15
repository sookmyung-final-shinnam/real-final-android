package com.veryshinnam.myapp.component.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer

@Composable
fun StrokeText(
    text: String,
    tColor: Color, // 텍스트 컬러
    oColor: Color, // 테두리 컬러
    oWidth: Float, // 테두리 두께
    fStyle: TextStyle,
    fWeight: FontWeight,
    modifier: Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    Text(
        text = text,
        color = tColor,
        style = fStyle.copy(fontWeight = fWeight),
        modifier = modifier.drawWithContent {
            val layout = textMeasurer.measure(
                AnnotatedString(text),
                style = fStyle.copy(fontWeight = fWeight)
            )

            drawText(
                textLayoutResult = layout,
                color = oColor,
                drawStyle = Stroke(width = oWidth)
            )

            drawContent()
        }
    )
}