package com.veryshinnam.myapp.common.component


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun LogoBar(
    modifier: Modifier = Modifier,
    logoText: String = "Storictor",
    logoStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    verticalPadding: Dp = 4.dp,
    onLogoClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding),
        contentAlignment = Alignment.Center
    ) {
        // 윤곽선 + 그림자
        Text(
            text = logoText,
            color = Color.White,
            style = logoStyle.copy(
                fontWeight = FontWeight.Black,
                drawStyle = Stroke(width = 8f),
                shadow = Shadow(
                    color = Color.Gray.copy(alpha = 0.6f),
                    offset = Offset(4f, 4f),
                    blurRadius = 8f
                )
            )
        )

        // 실제 텍스트
        Text(
            text = logoText,
            color = colorResource(R.color.brand_orange),
            style = logoStyle.copy(
                fontWeight = FontWeight.Black
            ),
            modifier = if (onLogoClick != null) {
                Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onLogoClick() }
            } else Modifier
        )
    }
}