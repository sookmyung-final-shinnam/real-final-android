package com.veryshinnam.myapp.common.component


import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {

    // 화면 높이에 따라 폰트 사이즈 계산
    val configuration = LocalConfiguration.current       // 현재 기기 화면 정보
    val screenHeightDp = configuration.screenHeightDp.dp // 화면 높이 px > dp 변환
    val density = LocalDensity.current                   // 현재 기기 density 정보
    val fontSize = with(density) { (screenHeightDp * 0.03f).toSp() }

    // drawText 측 기본 리졸버/밀도 값이 자동 주입
    val textMeasurer = rememberTextMeasurer()

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Storicter",
                color = colorResource(id = R.color.brand_orange),
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.drawWithContent {

                    // 현재 Text와 동일한 스타일로 측정
                    val layout = textMeasurer.measure(
                        AnnotatedString("Storicter"),
                        style = TextStyle(
                            fontSize = fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    // 텍스트 그림자
                    drawText(
                        textLayoutResult = layout,
                        color = Color.Black.copy(alpha = 0.5f),
                        topLeft = Offset(4f, 8f)
                    )

                    // 윤곽선 그린 후
                    drawText(
                        textLayoutResult = layout,
                        color = Color.White,
                        drawStyle = Stroke(width = 10f) // 두께
                    )
                    // 윤곽선 안을 채움
                    drawContent()
                }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent // 투명
        )
    )
}