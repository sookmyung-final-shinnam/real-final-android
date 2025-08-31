package com.veryshinnam.myapp.feature.creation.ui.conversation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ConversationScreen(
    onBack: () -> Unit,
    sessionId: Long,
    step: String,
    vm: ConversationViewModel = hiltViewModel()
) {

    val uiState by vm.convUiState
    val ttsReady by vm.isTtsReady.collectAsStateWithLifecycle(initialValue = false)
    val sttReady by vm.isSttReady.collectAsStateWithLifecycle(initialValue = false)
    val sttListening by vm.isSttListening.collectAsStateWithLifecycle(initialValue = false)

    var isBackDialogShow by remember { mutableStateOf(false) }

    BackHandler { isBackDialogShow = true }
    BackHomeDialog(
        isShow = isBackDialogShow,
        onDismiss = { isBackDialogShow = false },
        onConfirm = {
            isBackDialogShow = false
            vm.stopAll()   // tts 중단
            onBack()
        }
    )

    LaunchedEffect(sessionId, step) {
        // START -> STEP_01 로 질문 받아오고 자동 TTS
        vm.processConversation(sessionId, step)
    }

    Column(Modifier.padding(16.dp)) {
        Text("대화시작", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        if (uiState.isLoading) {
            Text("불러오는 중…")
        }

        // 다음 이야기 & 질문
        uiState.nextStory?.takeIf { it.isNotBlank() }?.let {
            Text(it)
            Spacer(Modifier.height(8.dp))
        }
        uiState.llmQuestion?.takeIf { it.isNotBlank() }?.let {
            Text(it)
            Spacer(Modifier.height(12.dp))
        }

        // 버튼들
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { vm.replayTts() }, // 다시 듣기: 스토리 → 질문 순서로 재생
                enabled = !uiState.isLoading
                        && ttsReady
                        // 처음엔 비활성화
                        && (!uiState.nextStory.isNullOrBlank() || !uiState.llmQuestion.isNullOrBlank())
            ) {
                Text("다시 듣기")
            }

            // 녹음 시작: 누르면 TTS 끊고 STT 시작 (VM에서 처리)
            Button(
                onClick = { vm.startStt() },
                enabled = !uiState.isLoading && !sttListening
            ) {
                Text(if (sttListening) "듣고 있어요..." else "녹음 시작")
            }
        }

        Spacer(Modifier.height(16.dp))

        // STT 인식 결과 (부분/최종)
        uiState.partialAnswer?.takeIf { it.isNotBlank() }?.let {
            Text("인식 중: $it")
            Spacer(Modifier.height(8.dp))
        }
        uiState.userAnswer?.takeIf { it.isNotBlank() }?.let {
            Text("내 답변: $it")
            Spacer(Modifier.height(8.dp))
        }

        Spacer(Modifier.height(12.dp))

        // 다음 단계로 진행 (STEP_01 → 02 → 03 → END)

        // 에러 표시
        uiState.errorMessage?.takeIf { it.isNotBlank() }?.let {
            Spacer(Modifier.height(12.dp))
            Text("에러: $it", color = MaterialTheme.colorScheme.error)
        }
    }


    // 처음엔 tts 진행때는 녹음 버튼 비활성화
    // 다시듣기 이후로부터 tts 중단하고 녹음 가능
    // 녹음시에 tts 끊기
    // 인식후에 피드백api하지만 일단 더미데이터
}