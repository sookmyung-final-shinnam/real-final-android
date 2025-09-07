package com.veryshinnam.myapp.feature.character.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar
import com.veryshinnam.myapp.common.component.LoadErrorView


@Composable
fun CharacterScreen(
    id: Long,
    onBack: () -> Unit,
    onStoryClick: (Long) -> Unit,
    onVideoClick:  (Long) -> Unit,
    vm: CharacterViewModel = hiltViewModel()
) {
    val uiState by vm.charUiState.collectAsStateWithLifecycle()
    val activity = LocalActivity.current

    // 가로모드 + id가 바뀌면 재로딩
    LaunchedEffect(id) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        vm.loadDummyCharacter(id)
    }

    // 뒤로가기 세로모드
    BackHandler {
        onBack()
    }

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = {
            Column (Modifier.fillMaxWidth()) {
                AppTopBar() // 기존 바
                Row(
                    modifier = Modifier
                        .clickable(onClick = {
                            onBack()
                        })
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "이전")
                    Spacer(Modifier.width(4.dp))
                    Text("이전", fontWeight = FontWeight.Medium)
                }
            }
        },
        bottomBar = {
            Spacer( // 네비게이션 바만큼 여백
                modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                // 조회 로딩 중
                is CharacterUiState.Loading -> {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.main_orange), // 주황색
                        trackColor = Color.Gray.copy(alpha = 0.5f),
                        strokeWidth = 4.dp
                    )
                }
                // 조회 오류
                is CharacterUiState.Error -> {
                    LoadErrorView(
                        message = state.message,
                        onRetry = { vm.reload(id) }
                    )
                }
                // 조회 성공
                is CharacterUiState.Success -> {
                    CharacterCardScreen(
                        cData = state.characterData,
                        sData = state.storyData,
                        onFavoriteClick = { id -> vm.updateFavorite(id) },
                        onStoryClick = onStoryClick,
                        onVideoClick = onVideoClick
                    )
                }
            }
        }
    }
}