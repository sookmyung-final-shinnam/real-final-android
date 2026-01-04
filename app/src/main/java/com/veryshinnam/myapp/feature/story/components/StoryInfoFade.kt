package com.veryshinnam.myapp.feature.story.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.story.model.StoryData

@Composable
fun StoryInfoFade(
    story: StoryData,
    spacerPadding: Dp = 10.dp,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
    tagTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(color = colorResource(R.color.background_yellow), fontWeight = FontWeight.SemiBold),
    descTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
    modifier: Modifier
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val scrollState = rememberScrollState()

    Row(modifier = modifier.fillMaxSize()) {
        // 페이드 부분
        Box(
            modifier = Modifier
                .fillMaxWidth(0.45f)
                .fillMaxHeight()
                .onSizeChanged { size = it }
        ) {
            if (size != IntSize.Zero) {
                // 페이드 오버레이
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.radialGradient(
                                colorStops = arrayOf(
                                    0.0f to Color.Transparent,
                                    0.7f to Color.Transparent,
                                    1.0f to colorResource(R.color.main_orange)
                                ),
                                center = Offset.Zero,
                                radius = size.width.toFloat()
                            )
                        )
                )
            }
        }

        // 동화 정보
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, bottom = 16.dp)
                .background(colorResource(R.color.main_orange))
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .align(Alignment.TopStart)
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)
            ) {
                Text( // 제목
                    text = story.title,
                    style = titleTextStyle.copy(
                        lineHeight = titleTextStyle.lineHeight * 1.2f
                    )
                )
                Spacer(modifier = Modifier.height(spacerPadding))
                Text( // 태그
                    text = story.tags,
                    style = tagTextStyle.copy(
                        lineHeight = titleTextStyle.lineHeight * 1.1f
                    )
                )
                Spacer(modifier = Modifier.height(spacerPadding * 2))
                Text( // 줄거리
                    text = story.description.replace("", "\u200B"),
                    style = descTextStyle
                )
            }

            if (scrollState.canScrollForward) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart  = 16.dp, bottomEnd  = 16.dp))
                        .fillMaxWidth()
                        .height(60.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Gray.copy(alpha = 0.4f)
                                )
                            )
                        )
                )
            }
        }
    }
}