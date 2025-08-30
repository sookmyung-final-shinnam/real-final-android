package com.veryshinnam.myapp.feature.creation.ui.select

import android.widget.NumberPicker
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SelectAgeScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    vm: SelectViewModel = hiltViewModel()
) {

    val uiState by vm.selectUiState.collectAsState()

    // 나이는 바텀 버튼에서 업데이트
    var age by rememberSaveable(uiState.age) {
        mutableIntStateOf(uiState.age)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.fillMaxWidth()) {

            // 1. 상단 뒤로
            TextButton(onClick = onBack) { Text("뒤로") }
            Spacer(Modifier.height(8.dp))

            // 2. 나이 선택 컨테이너
            AgePickerContainer(
                age = age,
                onAgeChange = { age = it }
            )
        }

        // 하단 공통 버튼: 누를 때만 VM 업데이트
        BottomButton(
            text = "다음 단계로",
            enabled = age in 1..100,
            onClick = {
                vm.setAge(age)
                onNext()
            }
        )
    }
}

// 나이 선택 컨테이너
@Composable
private fun AgePickerContainer(
    age: Int,
    onAgeChange: (Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Text(text = "나이", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                factory = { context ->
                    NumberPicker(context).apply {
                        minValue = 1
                        maxValue = 100
                        wrapSelectorWheel = false
                        value = age
                        setOnValueChangedListener { _, _, newVal ->
                            onAgeChange(newVal)
                        }
                    }
                },
                update = { picker ->
                    // 값 동기화
                    if (picker.value != age) picker.value = age
                }
            )
        }
    }
}