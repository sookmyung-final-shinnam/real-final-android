package com.veryshinnam.myapp.feature.creation.ui.select

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R


@Composable
fun SelectFaceScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    vm: SelectViewModel = hiltViewModel()
) {

    val uiState by vm.selectUiState.collectAsState()

    // 기본 팔레트 색상 및 머리 스타일
    val basicColors = listOf(
        "빨강색" to R.color.palette_red,
        "노랑색" to R.color.palette_yellow,
        "초록색" to R.color.palette_green,
        "파랑색" to R.color.palette_blue,
        "보라색" to R.color.palette_purple
    )
    val basicStyles = listOf("단발 머리", "긴 머리", "곱슬 머리", "대머리")

    // 초기 팔레트 세팅
    var selectEyeColor by rememberSaveable(uiState.eyeColor) {
        mutableStateOf(uiState.eyeColor.ifEmpty { basicColors.first().first })
    }
    var selectHairColor by rememberSaveable(uiState.hairColor) {
        mutableStateOf(uiState.hairColor.ifEmpty { basicColors.first().first })
    }

    var selectHairStyle by rememberSaveable(uiState.hairStyle) {
        mutableStateOf(uiState.hairStyle)
    }

    // 유효성 체크 (모두 선택)
    val isValid = selectEyeColor.isNotEmpty() && selectHairColor.isNotEmpty() && selectHairStyle.isNotEmpty()

    BackHandler { onBack() }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.fillMaxWidth()) {
            TextButton(onClick = onBack) { Text("뒤로") }
            Spacer(Modifier.height(8.dp))

            // 1-1. 눈색 선택 컨테이너
            PaletteContainer(
                title = "눈동자 색",
                colors = basicColors,
                selectedColorName = selectEyeColor,
                onSelect = { selectEyeColor = it }
            )
            Spacer(Modifier.height(16.dp))

            // 1-2. 머리색 선택 컨테이너
            PaletteContainer(
                title = "머리카락 색",
                colors = basicColors,
                selectedColorName = selectHairColor,
                onSelect = { selectHairColor = it }
            )
            Spacer(Modifier.height(16.dp))

            // 2. 머리모양 선택 컨테이너
            HairStyleContainer(
                title = "머리 모양",
                styles = basicStyles,
                selected = selectHairStyle,
                onSelect = { selectHairStyle = it }
            )
        }

        BottomButton(
            text = "이야기 만들기",
            enabled = isValid,
            onClick = {
                vm.setEyeColor(selectEyeColor)
                vm.setHairColor(selectHairColor)
                vm.setHairStyle(selectHairStyle)
                onNext()
            }
        )
    }
}

// 눈/머리색 선택 컨테이너
@Composable
private fun PaletteContainer(
    title: String,
    colors: List<Pair<String, Int>>,
    selectedColorName: String,
    onSelect: (String) -> Unit
) {
    // 팔레트 위 제목
    Text(title,
        style = MaterialTheme.typography.titleMedium,
        color = colorResource(R.color.main_orange)
    )
    Spacer(Modifier.height(6.dp))

    // 팔레트
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = colorResource(R.color.yellow_80)),
        shape = RoundedCornerShape(24.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(colors) { (label, resId) ->
                val color = colorResource(resId)
                val selected = label == selectedColorName
                ColorCircleButton(
                    color = color,
                    selected = selected,
                    onClick = { onSelect(label) }
                )
            }
        }
    }
}

@Composable
private fun ColorCircleButton(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        border = if (selected) BorderStroke(3.dp, colorResource(R.color.main_orange)) else null,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(56.dp)
    ) {}
}

@Composable
private fun HairStyleContainer(
    title: String,
    styles: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    // 컨테이너 위 제목
    Text(title,
        style = MaterialTheme.typography.titleMedium,
        color = colorResource(R.color.main_orange))
    Spacer(Modifier.height(6.dp))

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(styles) { style ->
            val isSelected = style == selected
            val container =
                if (isSelected) colorResource(R.color.main_orange) else colorResource(R.color.yellow_80)

            Button(
                onClick = { onSelect(style) },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = container),
                modifier = Modifier.fillMaxWidth()
            ) { Text(style, color = Color.Black) }
        }
    }
}