package com.veryshinnam.myapp.feature.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit = {},
    onCheckInClick: () -> Unit = {},
    onDashboardClick: () -> Unit = {},
    onCreationClick: () -> Unit = {},
    onStorageClick: () -> Unit = {},
    onCharacterClick: (Long) -> Unit = {},
    vm: HomeViewModel =  hiltViewModel()
) {
    // 홈화면 상태 관리
    val uiState by vm.homeUiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is HomeUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is HomeUiState.Error -> {
            ErrorView(
                message = state.message,
                onRetry = { vm.reload() } // 뷰모델에 로드 함수 하나 두세요
            )
        }
        is HomeUiState.Success -> {
            HomeScaffoldScreen(
                data = state.data,
                onSettingsClick = onSettingsClick,
                onCheckInClick = onCheckInClick,
                onDashboardClick = onDashboardClick,
                onCreationClick = onCreationClick,
                onStorageClick = onStorageClick,
                onCharacterClick = onCharacterClick,
            )
        }
    }
}

@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("다시 시도")
        }
    }
}