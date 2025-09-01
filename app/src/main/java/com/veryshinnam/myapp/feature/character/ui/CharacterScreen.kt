package com.veryshinnam.myapp.feature.character.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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

    when (val state = uiState) {
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
            Text(state.message)
        }

        // 조회 성공
        is CharacterUiState.Success -> {
            CharacterSuccessScreen(
                data = state.data,
            )
        }
    }
}