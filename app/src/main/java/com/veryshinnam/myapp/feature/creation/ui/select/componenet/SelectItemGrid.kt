package com.veryshinnam.myapp.feature.creation.ui.select.componenet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun SelectItemGrid(
    items: List<String>, // 기본 아이템 목록 (ex. ["숲 속","바다","직접추가"])
    maxSelectCount: Int,             // 선택 가능 개수 (테마=3, 배경=1)
    onSelectClick: (String) -> Unit, /// 선택 이벤트
    onCustomClick: () -> Unit,       // 직접추가 눌렀을 때
    modifier: Modifier = Modifier,
) {

    var isInputVisible by remember { mutableStateOf(false) }
    var customInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // 1. 일반 아이템 (2열 고정)
        val rows = items.chunked(2)
        rowItems.forEach { item ->
            val isSelected = /* 조건: 배경이면 item == selectedBack, 테마면 selectedThemes.contains(item) */

                Button(
                    onClick = { onSelectClick(item) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) colorResource(R.color.main_orange)
                        else colorResource(R.color.lemon_yellow),
                        contentColor = if (isSelected) Color.White else Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(item)
                }
        }

        // 2. 인라인 입력창 (직접추가 버튼 누른 경우에만 보임)
        if (isInputVisible) {
            OutlinedTextField(
                value = customInput,
                onValueChange = { customInput = it },
                placeholder = { Text("새 아이템 입력") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    TextButton(onClick = {
                        if (customInput.isNotBlank()) {
                            onCustomAdd(customInput)
                            customInput = ""
                            isInputVisible = false
                        }
                    }) { Text("확인") }
                }
            )
        }

        // 3. 반영된 Custom Item (맨 마지막 행, 가로 전체 차지)
        customItem?.let {
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                SelectItemButton(
                    text = it,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSelectClick(it) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // 4. 바닥의 직접추가 버튼
        Button(
            onClick = { isInputVisible = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("직접추가")
        }
    }
}