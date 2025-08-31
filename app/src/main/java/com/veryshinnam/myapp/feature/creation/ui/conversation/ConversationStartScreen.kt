package com.veryshinnam.myapp.feature.creation.ui.conversation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest

@Composable
fun ConversationStartScreen(
    onBack: () -> Unit,
    req: StartConversationRequest?,
    vm: ConversationViewModel = hiltViewModel()
) {
    val uiState by vm.convUiState
    val ttsReady by vm.isTtsReady.collectAsStateWithLifecycle(initialValue = false)

    // 뒤로가기 > 다이얼로그 창
    var isBackDialogShow by remember { mutableStateOf(false) }

    // 넘어온 캐릭터 생성 요청 텍스트 확인
    LaunchedEffect(req) {
        req?.let { vm.startConversationDummy(it) }
    }

    // nextStory가 갱신되면 자동 낭독
    LaunchedEffect(uiState.nextStory, ttsReady) {
        if (ttsReady && !uiState.nextStory.isNullOrBlank()) {
            vm.startTts(uiState.nextStory!!)
        }
    }

    // 0. 뒤로가기
    BackHandler {
        isBackDialogShow = true
    }

    BackHomeDialog(
        isShow = isBackDialogShow,
        onDismiss = { isBackDialogShow = false },
        onConfirm = {
            isBackDialogShow = false
            vm.stopTts()   // tts 중단
            onBack()
        }
    )

    Column {
        Text("AI 대화 첫 화면입니다.")
        Spacer(Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Text("불러오는 중...")
            }
            uiState.errorMessage != null -> {
                Text("에러: ${uiState.errorMessage}")
            }
            uiState.nextStory != null -> {
                Text(uiState.nextStory ?: "")
            }
        }

        // 다시 듣기
        Button(
            onClick = { uiState.nextStory?.let { vm.startTts(it) } },
            enabled = ttsReady && !uiState.nextStory.isNullOrBlank()
        ) {
            Text("다시 듣기")
        }

        // 다음 버튼
        Button(
            onClick = {
                vm.stopTts()
//                next()
            },
            enabled = !uiState.isLoading
        ) {
            Text("다음 버튼")
        }
    }
}


// 뒤로가기 대화창
@Composable
fun BackHomeDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    if (!isShow) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("정말 그만 두시겠습니까?") },
        text = { Text("현재까지 진행한 정보들은 저장되지 않으며, 홈 화면으로 이동합니다.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("예")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("아니오")
            }
        }
    )
}