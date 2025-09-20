package com.veryshinnam.myapp.feature.story.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun StoryPageButtons(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    pageCount: Int,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 이전 버튼
        Button(
            onClick = {
                if (pagerState.currentPage > 0) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.main_orange),
                contentColor = Color.White ),
            modifier = Modifier.fillMaxHeight(0.5f).aspectRatio(1f)
        ) { Text("◀") }

        // 다음 버튼
        Button(
            onClick = {
                if (pagerState.currentPage < pageCount) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.main_orange),
                contentColor = Color.White ),
            modifier = Modifier.fillMaxHeight(0.5f).aspectRatio(1f)
        ) { Text("▶") }
    }
}
