package com.veryshinnam.myapp.feature.creation.content.selection

import android.util.Log
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.CircleIconButton
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionAgeScroll
import com.veryshinnam.myapp.feature.creation.componenet.selection.SelectionBottomButtons
import com.veryshinnam.myapp.feature.creation.componenet.selection.scrollToAge

@Composable
fun SelectionAgeContent(
    age: Int,
    listState: LazyListState,
    flingBehavior: FlingBehavior,
    onIncreaseAge: () -> Unit,
    onDecreaseAge: () -> Unit,
    onSelectAge: (Int) -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onSimpleWarning: (String) -> Unit,    // 경고 콜백
    modifier: Modifier,
    spacePadding: Dp = 24.dp
) {
    val range = 1..100

    // 버튼 클릭 감지
    var buttonClicked by remember { mutableStateOf(false) }

    // 버튼 클릭 시 스크롤 실행
    LaunchedEffect(buttonClicked) {
        scrollToAge(listState, age, range)
    }


    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // 1. 나이 동작 버튼
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .zIndex(30f)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                CircleIconButton(
                    icon = Icons.Rounded.Remove,
                    desc = "나이 감소",
                    onClick = {
                        onDecreaseAge()
                        buttonClicked = !buttonClicked
                    },
                    modifier = Modifier.height(80.dp)
                        .semantics(true) {
                            contentDescription = "나이 감소"
                            role = Role.Button
                            stateDescription = "현재 나이: $age"
                        },
                    containerColor = colorResource(R.color.main_orange)
                )

                CircleIconButton(
                    icon = Icons.Rounded.Add,
                    desc = "나이 증가",
                    onClick = {
                        onIncreaseAge()
                        buttonClicked = !buttonClicked
                    },
                    modifier = Modifier.height(80.dp)
                            .semantics(true) {
                            contentDescription = "나이 감소"
                            role = Role.Button
                            stateDescription = "현재 나이: $age 세"
                        },
                    containerColor = colorResource(R.color.main_orange)
                )
            }

            // 2. 나이 피커
            SelectionAgeScroll(
                age = age,
                range = range,
                listState = listState,
                flingBehavior = flingBehavior,
                onSelectAge = onSelectAge,
                modifier = Modifier.fillMaxWidth()
                    .clearAndSetSemantics { }
            )
        }

        Spacer(Modifier.height(spacePadding))

        // 하단 버튼 영역
        SelectionBottomButtons(
            isLeft = true,     // 이전 버튼
            isCenter = false,  // 없음
            isRight = true,    // 다음 버튼
            onLeftClick = { onPrevClick() },  // 이전 단계로 이동
            onRightClick = {
                if (listState.isScrollInProgress) {
                    onSimpleWarning("스크롤을 멈춘 다음 이동해주세요!")
                } else if (age != -1) {
                    onNextClick()
                    Log.d("SelectScreen", "select age: $age")
                } else {
                    onSimpleWarning("아직 나이를 선택하지 않았어요!")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        )
    }
}