package com.veryshinnam.myapp.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.dashboard.model.LanguageData
import com.veryshinnam.myapp.feature.dashboard.model.PlayData

@Composable
fun DashboardTotalInfo(
    modifier: Modifier = Modifier,
    username: String,
    playData: PlayData,
    languageData: LanguageData,
    spacerPadding: Dp,
    listState: LazyListState = rememberLazyListState()
) {
    // 맨 위가 아닌지 체크
    // 스크롤 시작 시 페이드 효과 시작
    val isTop by remember {
        derivedStateOf {
//            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0
            listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
        }
    }

    Box(modifier = modifier) {

        // 스크롤 전체 구성
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(spacerPadding),
        ) {
            // 언어 분석 섹션
            item {
                DashboardPlayInfo(
                    modifier = Modifier.fillMaxWidth(),
                    username = username,
                    playData = playData
                )
            }

            // 언어별 데이터 섹션
            item {
                DashboardLanguageInfo(
                    modifier = Modifier.fillMaxWidth(),
                    username = username,
                    languageData = languageData
                )
            }
        }

        // 페이드 효과
        if (!isTop) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.08f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(R.color.background_yellow),
                                Color.Transparent
                            )
                        )
                    )
                    .align(Alignment.TopCenter)
            )
        }
    }
}