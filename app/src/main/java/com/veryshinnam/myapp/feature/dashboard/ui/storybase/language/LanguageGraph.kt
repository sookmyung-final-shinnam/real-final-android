package com.veryshinnam.myapp.feature.dashboard.ui.storybase.language

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** 언어 발달 통계 그래프 (기승전결) */
@Composable
fun LanguageGraph(
    // lang: LanguageItem
) {

//    val attempts = listOf(
//        "기" to lang.attemptStats.giCount,
//        "승" to lang.attemptStats.seungCount,
//        "전" to lang.attemptStats.jeonCount,
//        "결" to lang.attemptStats.gyeolCount
//    )

    var showDialog by remember { mutableStateOf(false) }

    Column {

        // 평균 답변 길이
//        Text(
//            "🗒️ 평균 답변 길이: **${lang.avgAnswerLength}자**",
//            style = MaterialTheme.typography.bodyLarge
//        )
//        Spacer(Modifier.height(8.dp))

//        attempts.forEach { (stage, count) ->
//            val progress = lang.avgAttemptPerStage.toFloat().takeIf { it != 0f }?.let { avg ->
//                (count.toFloat() / (avg * 2f)).coerceIn(0f, 1f)
//            } ?: 1f
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    "$stage 단계: ${count}회",
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.width(80.dp)
//                )
//                Text(
//                    "(${(progress * 100).toInt()}%)",
//                    style = MaterialTheme.typography.bodyMedium,
//                )
//            }
//
//            LinearProgressIndicator(
//                progress = progress,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(8.dp)
//                    .padding(vertical = 2.dp),
//                color = MaterialTheme.colorScheme.primary
//            )
//            Spacer(Modifier.height(4.dp))
//        }
//
//        // 새 단어 버튼
//        if (lang.newWords.isNotEmpty()) {
//            Spacer(Modifier.height(6.dp))
//            Text(
//                "🆕 ${lang.newWords.size}개 새 단어 보기",
//                modifier = Modifier.clickable { showDialog = true },
//                color = MaterialTheme.colorScheme.primary,
//                style = MaterialTheme.typography.bodyMedium
//            )
//        }
//
//        // 단어 목록 팝업
//        if (showDialog) {
//            AlertDialog(
//                onDismissRequest = { showDialog = false },
//                title = { Text("새 단어 목록") },
//                text = {
//                    Column(
//                        modifier = Modifier
//                            .heightIn(max = 300.dp)
//                            .verticalScroll(rememberScrollState())
//                    ) {
//                        lang.newWords.forEach { Text("• $it") }
//                    }
//                },
//                confirmButton = {
//                    TextButton(onClick = { showDialog = false }) {
//                        Text("닫기")
//                    }
//                }
//            )
//        }
    }
}