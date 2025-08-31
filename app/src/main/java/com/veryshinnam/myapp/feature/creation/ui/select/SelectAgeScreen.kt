package com.veryshinnam.myapp.feature.creation.ui.select

import android.widget.NumberPicker
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.R

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
    ) {
        // 1. 상단 뒤로
        TextButton(onClick = onBack) { Text("뒤로") }
        Spacer(Modifier.height(8.dp))

        // 2. 나이 선택 컨테이너
        Box(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AgePickerContainer(
                age = age,
                onAgeChange = { age = it },
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }

        Spacer(Modifier.weight(0.1f))
        // 하단 공통 버튼: 누를 때만 VM 업데이트
        BottomButton(
            text = "다음 단계로",
            enabled = age in 1..100,
            onClick = {
                vm.selectAge(age)
                onNext()
            },
            modifier = Modifier
                .navigationBarsPadding()
                .weight(.1f)
        )
    }
}

// 나이 선택 컨테이너
@Composable
private fun AgePickerContainer(
    age: Int,
    onAgeChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.yellow_80)
        ),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .weight(1f),
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