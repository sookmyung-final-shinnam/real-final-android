package com.veryshinnam.myapp.feature.creation.content.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionPaletteGrid
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionStyleButtons
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionTripleButtons


@Composable
fun SelectionFaceContent(
    eyeColor: String,
    hairColor: String,
    hairStyle: String,
    onSelectEyeColor: (String) -> Unit,
    onSelectHairColor: (String) -> Unit,
    onSelectHairStyle: (String) -> Unit,
    onPrevClick: () -> Unit,
    onSimpleWarning: (String) -> Unit,    // 경고 콜백
    onWarning: (String, String) -> Unit,
    modifier: Modifier,
    horizontalPadding: Dp = 5.dp,
    spacePadding: Dp = 24.dp,
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
            modifier = Modifier
                .fillMaxWidth()
                .weight(8f)
                .padding(horizontal = horizontalPadding),
            verticalArrangement = Arrangement.spacedBy(spacePadding),
            horizontalAlignment = Alignment.CenterHorizontally // 가로 중앙
        ) {
            // 눈색 선택 파레트
            SelectionPaletteGrid(
                title = "눈동자 색",
                colors = colors,
                selectedColorName = eyeColor,
                onSelect = { onSelectEyeColor(it) },
                modifier = Modifier.weight(0.4f)
            )

            // 머리색 선택 파레트
            SelectionPaletteGrid(
                title = "머리카락 색",
                colors = colors,
                selectedColorName = hairColor,
                onSelect = { onSelectHairColor(it) },
                modifier = Modifier.weight(0.4f)
            )


            // 머리 모양 선택 버튼
            SelectionStyleButtons(
                title = "머리 모양",
                styles = styles,
                selected = hairStyle,
                onSelect = { onSelectHairStyle(it)},
                modifier = Modifier.weight(0.3f)
            )
        }

        Spacer(Modifier.height(spacePadding))

        // 하단 버튼 영역
        SelectionTripleButtons(
            isLeft = true,     // 이전 버튼
            isCenter = false,  // 없음
            isRight = true,    // 다음 버튼
            onLeftClick = { onPrevClick() },  // 이전 단계로 이동
            onRightClick = {
                if (isValid) {
                    onWarning("선택한 정보로 동화를 시작할까요?", "시작하기")
                    // > 아이템 1개가 소진됩니다.
                }
                else {
                    onSimpleWarning("아직 머리 스타일을 선택하지 않았어요!")
                }
            },
            modifier = Modifier.fillMaxWidth().weight(2f),
        )
    }
}