package com.veryshinnam.myapp.feature.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R

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
    var lastSelectedId by rememberSaveable { mutableStateOf<Long?>(null) }

    when (val state = uiState) {
        is HomeUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.main_orange), // 주황색
                    trackColor = Color.Gray.copy(alpha = 0.5f),
                    strokeWidth = 4.dp
                )
            }
        }
        is HomeUiState.Error -> {
            ErrorView(
                message = state.message,
                onRetry = { vm.reload() }
            )
        }
        is HomeUiState.Success -> {
            HomeScaffoldScreen(
                data = state.data,
                lastSelectedId = lastSelectedId,
                onSettingsClick = onSettingsClick,
                onCheckInClick = onCheckInClick,
                onDashboardClick = onDashboardClick,
                onCreationClick = onCreationClick,
                onStorageClick = onStorageClick,
                onCharacterClick =  { id ->
                    lastSelectedId = id
                    onCharacterClick(id)
                }
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