package com.veryshinnam.myapp.feature.dashboard.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veryshinnam.myapp.feature.dashboard.model.DashboardResult
import com.veryshinnam.myapp.feature.dashboard.ui.interest.InterestSection
import com.veryshinnam.myapp.feature.dashboard.ui.storybase.StoryLearningSection

/** 메인 대시보드 화면 */
@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    onCharacterNavigate: (Long) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        state.errorMessage != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("❌ 대시보드를 불러올 수 없어요: ${state.errorMessage}")
        }
        state.data != null -> DashboardContent(
            data = state.data,
            onCharacterClick = onCharacterNavigate
        )
    }
}

/** 대시보드 내용 */
@Composable
fun DashboardContent(
    data: DashboardResult,
    onCharacterClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        // 1. 관심사 통계 (도넛 차트)
        InterestSection(
            themeStats = data.themeStats,
            backgroundStats = data.backgroundStats
        )

        Spacer(Modifier.height(32.dp))

        // 2. 스토리에서 학습한 언어 + 정서 분석
        Text("📘 스토리 언어 학습 & 정서 분석", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        // storyId 기준 매칭 (동화와 언어/감정이 연결되는 구조)
        val stories = data.languageStats.mapNotNull { lang ->
            val emo = data.emotionsStats.find { it.storyId == lang.storyId }
            emo?.let { lang to emo }
        }

        StoryLearningSection(stories) { lang, emotion ->
            onCharacterClick(lang.storyId)
        }

        Spacer(Modifier.height(32.dp))

        // 3. 부모 조언
        Text("💡 부모 조언", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        ParentAdviceSection(data.parentAdvice)
    }

}

/** 부모 조언 섹션 */
@Composable
fun ParentAdviceSection(advice: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(
            text = advice,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}