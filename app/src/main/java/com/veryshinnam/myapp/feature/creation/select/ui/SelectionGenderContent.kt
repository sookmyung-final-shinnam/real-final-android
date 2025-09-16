package com.veryshinnam.myapp.feature.creation.select.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.AppTopBar
import com.veryshinnam.myapp.component.common.BackButton
import com.veryshinnam.myapp.feature.creation.model.Gender
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectGenderButton
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectInfo
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons

@Composable
fun SelectionGenderContent(
    gender: Gender,                     // 현재 선택된 성별
    onSelectGender: (Gender) -> Unit,   // 성별 선택 콜백
    onPrevClick: () -> Unit,            // 이전 단계 이동
    onNextClick: () -> Unit,            // 다음 단계 이동
    modifier: Modifier
) {
    // 성별 버튼 체크 용도
    val isFemaleSelected = gender == Gender.FEMALE
    val isMaleSelected = gender == Gender.MALE

    // 성별 버튼
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth(0.4f)
                .align(Alignment.CenterHorizontally),
        ) {
            // 여자 버튼
            SelectGenderButton(
                gender = Gender.FEMALE,
                isSelected = isFemaleSelected,
                onButtonClick = { onSelectGender(Gender.FEMALE) },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )

            Spacer(Modifier.weight(0.2f))

            // 남자 버튼
            SelectGenderButton(
                gender = Gender.MALE,
                isSelected = isMaleSelected,
                onButtonClick = { onSelectGender(Gender.MALE) },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }

        // 하단 버튼 영역
        SelectTripleButtons(
            isLeft = true,     // 이전 버튼
            isCenter = false,  // 없음
            isRight = true,    // 다음 버튼
            onLeftClick = { onPrevClick() },  // 이전 단계로 이동
            onRightClick = {
                if (gender != Gender.NONE) {
                    onNextClick() // 다음 단계로 이동
                } else {
                    // TODO: 성별을 선택하세요
                }
            },
            modifier = Modifier.fillMaxWidth().weight(2f)
        )
    }
}