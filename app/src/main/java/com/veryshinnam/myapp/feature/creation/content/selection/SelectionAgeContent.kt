package com.veryshinnam.myapp.feature.creation.content.selection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionAgeScroll
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionTripleButtons

@Composable
fun SelectionAgeContent(
    age: Int,
    listState: LazyListState,
    onSelectAge: (Int) -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onSimpleWarning: (String) -> Unit,    // 경고 콜백
    modifier: Modifier
) {
    Column(modifier = modifier) {

        // 나이 스크롤
        SelectionAgeScroll(
            age = age,
            listState = listState,
            onAgeConfirm = { onSelectAge(it) },
            modifier = Modifier.fillMaxWidth().weight(8f)
        )

        // 하단 버튼 영역
        SelectionTripleButtons(
            isLeft = true,     // 이전 버튼
            isCenter = false,  // 없음
            isRight = true,    // 다음 버튼
            onLeftClick = { onPrevClick() },  // 이전 단계로 이동
            onRightClick = {
                if (age != -1) {
                    onNextClick()
                } else {
                    onSimpleWarning("아직 나이를 선택하지 않았어요!")
                }
            },
            modifier = Modifier.fillMaxWidth().weight(2f)
        )
    }
}