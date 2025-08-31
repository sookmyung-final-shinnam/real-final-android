package com.veryshinnam.myapp.feature.character.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun CharacterScreen(
    id: Long,
    onBack: () -> Unit,
    vm: CharacterViewModel = hiltViewModel()
) {
    BackHandler { onBack() }

    // id가 바뀌면 재로딩
    LaunchedEffect(id) { vm.loadDummyCharacter(id) }

    val uiState by vm.charUiState.collectAsStateWithLifecycle()

    when (uiState) {
        // 조회 로딩 중
        is CharacterUiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        // 조회 오류
        is CharacterUiState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text((uiState as CharacterUiState.Error).message)
        }

        // 조회 성공
        is CharacterUiState.Success -> {
            val data = (uiState as CharacterUiState.Success).data

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "캐릭터아이디: $id",
                )
                // 캐릭터 이미지
                Card {
                    AsyncImage(
                        model = data.imageUrl,
                        contentDescription = "${data.name} 이미지",
                        modifier = Modifier
                            .fillMaxWidth(.3f)
                    )
                }

                // 이름 / 기본 정보
                Text(
                    text = data.name,
                )
                Text("성별: ${data.gender} · 나이: ${data.age}")

                // 즐겨찾기 여부 (로컬 토글)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("즐겨찾기 : ${if (data.important) "예" else "아니오"}")
                    AssistChip(
                        onClick = { vm.toggleImportantLocal() },
                        label = { Text(if (data.important) "해제" else "표시") }
                    )
                }

                // 성격
                Text("나는 ${data.personality}한 성격을 가졌어.")

                // 책(스토리) 정보
                Text("책: ${data.storyTitle} (#${data.storyId})")

                // 생성 시각
                Text("생성일시: ${data.createTime}")
            }
        }
    }
}