package com.veryshinnam.myapp.feature.creation.content.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.common.enums.Gender
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionGenderButton
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionTripleButtons

@Composable
fun SelectionGenderContent(
    gender: Gender,                     // 현재 선택된 성별
    onSelectGender: (Gender) -> Unit,   // 성별 선택 콜백
    onPrevClick: () -> Unit,            // 이전 단계 이동
    onNextClick: () -> Unit,            // 다음 단계 이동
    onSimpleWarning: (String) -> Unit,  // 경고
    modifier: Modifier,
    spacePadding: Dp = 24.dp
) {
    // 성별 버튼 체크 용도
    val isFemaleSelected = gender == Gender.FEMALE
    val isMaleSelected = gender == Gender.MALE

    // 성별 버튼
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(8f)
                .fillMaxSize()
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // 여자 버튼
            SelectionGenderButton(
                gender = Gender.FEMALE,
                isSelected = isFemaleSelected,
                onButtonClick = { onSelectGender(Gender.FEMALE) },
                modifier = Modifier.fillMaxHeight().weight(1f)
            )

            Spacer(Modifier.height(spacePadding/2))

            // 남자 버튼
            SelectionGenderButton(
                gender = Gender.MALE,
                isSelected = isMaleSelected,
                onButtonClick = { onSelectGender(Gender.MALE) },
                modifier = Modifier.fillMaxHeight().weight(1f)
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
                if (gender != Gender.ALL) {
                    onNextClick() // 다음 단계로 이동
                } else {
                    onSimpleWarning("아직 성별을 선택하지 않았어요!")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        )
    }
}