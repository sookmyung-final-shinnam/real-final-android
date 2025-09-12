package com.veryshinnam.myapp.feature.creation.select.componenet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun SelectItemGrid(
    items: List<String>,         // 아이템 리스트
    selectedItems: List<String>, // 선택된 아이템 리스트
    maxSelectCount: Int,         // 선택 가능 아이템 수
    customItem: String?,         // 직접 추가 아이템
    onItemClick: (String) -> Unit, // 선택 이벤트
    modifier: Modifier = Modifier,
) {

    val totalSlots = 7 // 직접 추가 포함 7개
    val rows = (0 until totalSlots).chunked(2)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp) // 행 사이 간격
    ) {
        rows.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier.fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally)
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp) // 열 사이 간격
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
                                modifier = Modifier.fillMaxHeight().weight(1f)
                            ) {
                                Text(customItem,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold)
                                )
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
                            modifier = Modifier.fillMaxHeight().weight(1f)
                        ) {
                            Text(item,
                                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
        Spacer(Modifier.weight(0.3f)) // 그리드 아래 공간
    }
}