package com.veryshinnam.myapp.feature.creation.select.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectPaletteGrid
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectStyleButtons
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons


@Composable
fun SelectionFaceContent(
    eyeColor: String,
    hairColor: String,
    hairStyle: String,
    onSelectEyeColor: (String) -> Unit,
    onSelectHairColor: (String) -> Unit,
    onSelectHairStyle: (String) -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier,
) {
    // 기본 팔레트 색상 및 머리 스타일
    val colors = listOf(
        "빨강색" to R.color.palette_red,
        "분홍색" to R.color.palette_pink,
        "주황색" to R.color.palette_orange,
        "노랑색" to R.color.palette_yellow,
        "초록색" to R.color.palette_green,
        "연두색" to R.color.palette_lgreen,
        "파랑색" to R.color.palette_blue,
        "하늘색" to R.color.palette_sblue,
        "보라색" to R.color.palette_purple,
        "갈색" to R.color.palette_brown,
        "흰색" to R.color.palette_white,
        "검정색" to R.color.palette_black
    )
    val styles = listOf("단발 머리", "긴 머리", "곱슬 머리", "대머리")

    // 유효성 체크 (모두 선택)
    val isValid = eyeColor.isNotEmpty() && hairColor.isNotEmpty() && hairStyle.isNotEmpty()

    Column(modifier = modifier) {

        // 파레트 및 스타일
        Column(
            modifier = Modifier.fillMaxWidth().weight(8f),
            horizontalAlignment = Alignment.CenterHorizontally // 가로 중앙
        ) {
            // 눈색 선택 파레트
            SelectPaletteGrid(
                title = "눈동자 색",
                colors = colors,
                selectedColorName = eyeColor,
                onSelect = { onSelectEyeColor(it) },
                modifier = Modifier.weight(0.4f)
            )
            Spacer(Modifier.weight(0.05f))

            // 머리색 선택 파레트
            SelectPaletteGrid(
                title = "머리카락 색",
                colors = colors,
                selectedColorName = hairColor,
                onSelect = { onSelectHairColor(it) },
                modifier = Modifier.weight(0.4f)
            )

            Spacer(Modifier.weight(0.05f))

            // 머리 모양 선택 버튼
            SelectStyleButtons(
                title = "머리 모양",
                styles = styles,
                selected = hairStyle,
                onSelect = { onSelectHairStyle(it)},
                modifier = Modifier.weight(0.2f)
            )
        }

        // 하단 버튼 영역
        SelectTripleButtons(
            isLeft = true,     // 이전 버튼
            isCenter = false,  // 없음
            isRight = true,    // 다음 버튼
            onLeftClick = { onPrevClick() },  // 이전 단계로 이동
            onRightClick = {
                if (isValid) {
                    onNextClick()
                }
            },
            modifier = Modifier.fillMaxWidth().weight(2f),
        )
    }
}