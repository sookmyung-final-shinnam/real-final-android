package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// 보관함 상단
@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    isItem: Boolean = false,
    itemCount: Int = 0,
    itemImage: Painter? = null,
    itemDescription: String = "",
    animalImage: Painter,
    animalAlignment: Alignment = Alignment.BottomCenter,
    animalDescription: String,
    bottomPadding: Dp = 10.dp,
    cardPadding: Dp = 16.dp,
    cardColor: Color,
    cardText: String,
    cardTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    spanText: String? = null,               // 강조할 텍스트
    spanColor: Color = Color.Unspecified,   // 강조할 색상
    spanTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    onItemRect: (Rect) -> Unit = {},
    onAnimalRect: (Rect) -> Unit = {},
    onMessageRect: (Rect) -> Unit = {},
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.12f) // 화면의 12% 고정
        ) {
            Image(
                painter = animalImage,
                contentDescription = animalDescription,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp)
                    .onGloballyPositioned { onAnimalRect(it.boundsInRoot()) },
                contentScale = ContentScale.Fit
            )

            // 아이템 설명이 있는 경우만 표시
            if (isItem && itemImage != null) {
                UserItem(
                    painter = itemImage,
                    contentDescription = itemDescription,
                    value = "$itemCount",
                    color = cardColor,
                    modifier = Modifier
                        .padding(bottom = bottomPadding)
                        .align(animalAlignment)
                        .fillMaxWidth(0.3f)
                        .fillMaxHeight(0.6f)
                        .onGloballyPositioned { onItemRect(it.boundsInRoot()) }
                )
            }
        }

        // 설명 텍스트
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(
                    width = 4.dp,
                    color = cardColor,
                    shape = RoundedCornerShape(16.dp))
                .onGloballyPositioned { onMessageRect(it.boundsInRoot()) }
                .padding(cardPadding),
            contentAlignment = Alignment.TopStart
        ) {
            // 강조 텍스트 처리
            val annotatedText = remember(cardText, spanText, spanColor) {
                if (spanText.isNullOrBlank()) {
                    AnnotatedString(
                        text = cardText.map { ch ->
                            if (ch == '\n') ch.toString() else "$ch\u200B" // 줄바꿈 문자 무시
                        }.joinToString("")
                    )
                } else { // 강조 텍스트 있는 경우
                    buildAnnotatedString {
                        var i = 0
                        while (i < cardText.length) {
                            val remaining = cardText.substring(i)

                            // 강조 텍스트 색상 및 크기 적용
                            if (remaining.startsWith(spanText)) {
                                withStyle(
                                    style = spanTextStyle.toSpanStyle().copy(
                                        color = spanColor,
                                        fontSize = spanTextStyle.fontSize,
                                        textDecoration = TextDecoration.Underline
                                    )
                                ) { append(spanText) }
                                append("\u200B")
                                i += spanText.length
                            } else {
                                val ch = cardText[i]
                                append(ch)
                                if (ch != '\n') append("\u200B")
                                i++
                            }
                        }
                    }
                }
            }

            Text(
                text = annotatedText,
                style = cardTextStyle.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    lineBreak = LineBreak.Simple,
                ),
                softWrap = true
            )
        }
    }
}