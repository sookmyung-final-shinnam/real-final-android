package com.veryshinnam.myapp.feature.creation.select.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
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
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectAgeScroll
import com.veryshinnam.myapp.feature.creation.select.componenet.SelectInfo
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