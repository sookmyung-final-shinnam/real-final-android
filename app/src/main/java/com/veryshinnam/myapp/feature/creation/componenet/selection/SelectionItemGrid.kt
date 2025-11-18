package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun SelectionItemGrid(
    items: List<String>,         // 아이템 리스트
    selectedItems: List<String>, // 선택된 아이템 리스트
    customItem: String?,         // 직접 추가 아이템
    onItemClick: (String) -> Unit, // 선택 이벤트
    onFirstBRect: (Rect) -> Unit = { },
    modifier: Modifier,
    textextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold),
    rowPadding: Dp = 12.dp,
    columnPadding: Dp = 10.dp
) {

    val totalSlots = 7 // 직접 추가 포함 7개
    val rows = (0 until totalSlots).chunked(2)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(rowPadding) // 행 사이 간격
    ) {
        rows.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .align(Alignment.CenterHorizontally)
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(columnPadding) // 열 사이 간격
            ) {
                row.forEach { index ->
                    val isLastCell = index == totalSlots - 1

                    if (isLastCell) {
                        if (!customItem.isNullOrBlank()) {
                            val isSelected = selectedItems.contains(customItem)

                            // 직접추가 값이 있으면 버튼 표시
                            Button(
                                onClick = { onItemClick(customItem) },
                                shape = RoundedCornerShape(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) colorResource(R.color.main_orange)
                                                     else colorResource(R.color.lemon_yellow),
                                    contentColor = if (isSelected) Color.White else Color.Black),
                                border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            ) {
                                Text(customItem, style = textextStyle)
                            }
                        } else {
                            // 없으면 그냥 빈자리
                            Spacer(Modifier.weight(1f))
                        }
                    } else {
                        // 기본 6개 버튼
                        val item = items.getOrNull(index) ?: ""
                        val isSelected = selectedItems.contains(item)

                        Button(
                            onClick = { onItemClick(item) },
                            shape = RoundedCornerShape(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) colorResource(R.color.main_orange)
                                                 else colorResource(R.color.lemon_yellow),
                                contentColor = if (isSelected) Color.White else Color.Black),
                            border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .then(
                                    if (index == 0) {
                                        Modifier.onGloballyPositioned {
                                            onFirstBRect(it.boundsInRoot())
                                        }
                                    } else Modifier
                                )
                        ) {
                            Text(item,
                                style = textextStyle
                            )
                        }
                    }
                }
            }
        }
    }
}