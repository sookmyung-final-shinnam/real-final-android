package com.veryshinnam.myapp.feature.creation.ui.conversation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest

@Composable
fun ConversationScreen(
    onBack: () -> Unit,
    req: StartConversationRequest?
) {
    // 뒤로가기 > 다이얼로그 창
    var isBackDialogShow by remember { mutableStateOf(false) }

    Text("ai대화첫시작화면입니다.")

    // 넘어온 캐릭터 생성 요청 텍스트 확인
    LaunchedEffect(req) {
        android.util.Log.d("ConversationScreen", "넘겨받은 req = $req")
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
            onBack()  // 원래 함수
        }
    )
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