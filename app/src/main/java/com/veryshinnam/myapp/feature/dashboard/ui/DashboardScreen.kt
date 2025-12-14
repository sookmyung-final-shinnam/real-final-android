package com.veryshinnam.myapp.feature.dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.feature.dashboard.model.DashboardResult

@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

    when {
        state.isLoading -> {
            Text("로딩중...")
        }
        state.errorMessage != null -> {
            Text("❌ 대시보드를 불러올 수 없어요.")
        }
        state.data != null -> {
            DashboardContent(state.data!!)
        }
    }
}

@Composable
fun DashboardContent(data: DashboardResult) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text("📊 Dashboard ID: ${data.dashboardId}")

        // -------------------- 1. Background Stats --------------------
        Spacer(Modifier.height(12.dp))
        Text("🎨 배경 사용 비율")
        data.backgroundStats.forEach {
            Text("• ${it.name} : ${it.count}회 (${String.format("%.2f", it.percent)}%)")
        }

        // -------------------- 2. Theme Stats --------------------
        Spacer(Modifier.height(12.dp))
        Text("🧩 테마 사용 비율")
        data.themeStats.forEach {
            Text("• ${it.name} : ${it.count}회 (${String.format("%.2f", it.percent)}%)")
        }

        // -------------------- 3. Language Stats 상세 출력 --------------------
        Spacer(Modifier.height(12.dp))
        Text("🗣️ 언어 학습 데이터")

        data.languageStats.forEach { item ->
            Spacer(Modifier.height(6.dp))
            Text("📘 Story ID: ${item.storyId}")
            Text("🕒 생성시간: ${item.createdAt}")
            Text("📌 평균 단계 당 시도: ${item.avgAttemptPerStage}")
            Text("✍ 평균 답변 길이: ${item.avgAnswerLength}")

            Text("🔁 시도 기록:")
            Text("  - 기: ${item.attemptStats.giCount}")
            Text("  - 승: ${item.attemptStats.seungCount}")
            Text("  - 전: ${item.attemptStats.jeonCount}")
            Text("  - 결: ${item.attemptStats.gyeolCount}")

            Text("🆕 새로운 단어:")
            item.newWords.forEach { word ->
                Text("  • $word")
            }
        }

        // -------------------- 4. Emotions Stats 상세 출력 --------------------
        Spacer(Modifier.height(12.dp))
        Text("😊 정서 분석")

        data.emotionsStats.forEach { item ->
            Spacer(Modifier.height(6.dp))
            Text("📘 Story ID: ${item.storyId}")
            Text("🕒 생성시간: ${item.createdAt}")
            Text("🎭 감정 비율")
            Text("  - joy: ${item.joy}")
            Text("  - sadness: ${item.sadness}")
            Text("  - anger: ${item.anger}")
            Text("  - fear: ${item.fear}")
            Text("  - surprise: ${item.surprise}")
            Text("  - neutral: ${item.neutral}")
            Text("📖 감정 요약: ${item.summary}")
        }

        // -------------------- 5. Parent Advice --------------------
        Spacer(Modifier.height(12.dp))
        Text("👪 부모 조언")
        Text(data.parentAdvice)
    }

}