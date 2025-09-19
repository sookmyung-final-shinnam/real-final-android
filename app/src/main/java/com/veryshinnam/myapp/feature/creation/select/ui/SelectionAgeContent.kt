package com.veryshinnam.myapp.feature.creation.select.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectAgeScroll
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectTripleButtons

@Composable
fun SelectionAgeContent(
    age: Int,
    listState: LazyListState,
    onSelectAge: (Int) -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {

        // 나이 스크롤
        SelectAgeScroll(
            age = age,
            listState = listState,
            onAgeConfirm = { onSelectAge(it) },
            modifier = Modifier.fillMaxWidth().weight(8f)
        )

        // 하단 버튼 영역
        SelectTripleButtons(
            isLeft = true,     // 이전 버튼
            isCenter = false,  // 없음
            isRight = true,    // 다음 버튼
            onLeftClick = { onPrevClick() },  // 이전 단계로 이동
            onRightClick = {
                if (age != -1) {
                    onNextClick()
                } else {
                    // TODO: 나이를 선택하세요
                }
            },
            modifier = Modifier.fillMaxWidth().weight(2f)
        )
    }
}