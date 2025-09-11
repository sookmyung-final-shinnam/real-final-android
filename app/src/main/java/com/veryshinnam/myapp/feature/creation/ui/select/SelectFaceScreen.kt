package com.veryshinnam.myapp.feature.creation.ui.select

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.feature.creation.model.Gender
import com.veryshinnam.myapp.feature.creation.ui.select.componenet.SelectGenderButton
import com.veryshinnam.myapp.feature.creation.ui.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.ui.select.componenet.SelectTripleButtons


@Composable
fun SelectFaceScreen(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    vm: SelectViewModel = hiltViewModel()
) {

    val uiState by vm.selectUiState.collectAsState()
    val horizontalPadding = 16.dp

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


    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = { AppTopBar() }, // 상단 로고
        contentWindowInsets = WindowInsets.navigationBars // 네비게이션 여백
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            BackButton(
                onBackClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
            )

            // TODO: 프로그레스바

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = horizontalPadding)
            ) {
                // 현재 스크린 설명
                SelectInfo(
                    text = "동화 속 주인공의 모습은 어떻게 할까?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25f)
                )

                // 파레트
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.55f),
                    verticalArrangement = Arrangement.Center
                ) {
                    // 1-1. 눈색 선택 컨테이너
                    PaletteContainer(
                        title = "눈동자 색",
                        colors = basicColors,
                        selectedColorName = selectEyeColor,
                        onSelect = { selectEyeColor = it }
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    // 1-2. 머리색 선택 컨테이너
                    PaletteContainer(
                        title = "머리카락 색",
                        colors = basicColors,
                        selectedColorName = selectHairColor,
                        onSelect = { selectHairColor = it }
                    )
                    Spacer(Modifier.weight(1f))

                    // 2. 머리모양 선택 컨테이너
                    HairStyleContainer(
                        title = "머리 모양",
                        styles = basicStyles,
                        selected = selectHairStyle,
                        onSelect = { selectHairStyle = it }
                    )
                }

                // 하단 버튼 영역
                SelectTripleButtons(
                    isLeft = true,     // 이전 버튼
                    isCenter = false,  // 없음
                    isRight = true,    // 다음 버튼
                    onLeftClick = { onBackClick() },  // 이전 단계로 이동
                    onRightClick = {
                        if(isValid) {
                            vm.setEyeColor(selectEyeColor)
                            vm.setHairColor(selectHairColor)
                            vm.setHairStyle(selectHairStyle)
                            onNextClick()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f),
                )
            }
        }
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
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))

    Spacer(modifier = Modifier.height(6.dp))
    // 팔레트
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.lemon_yellow)),
        shape = RoundedCornerShape(48.dp),
        border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 컬러 원형 버튼
            colors.forEach { (label, resId) ->
                val color = colorResource(resId)
                val selected = label == selectedColorName
                ColorCircleButton(
                    color = color,
                    selected = selected,
                    onClick = { onSelect(label) },
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }

            // 직접 추가버튼
                Button(
                    onClick = { /* TODO: 직접 추가 로직 */ },
                    shape = CircleShape,
                    border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.lemon_yellow)),
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                ) {
                    Text(text = "키보드")
                }
        }
    }
}

@Composable
private fun ColorCircleButton(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        border = if (selected) BorderStroke(8.dp, colorResource(R.color.main_orange)) else null,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = modifier
    ) {}
}

// 머리 모양 컨테이너
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