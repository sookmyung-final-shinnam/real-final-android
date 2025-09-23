package com.veryshinnam.myapp.feature.story.ui

import androidx.activity.compose.BackHandler
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
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun StoryScreen(
    storyId: Long,        // 동화 아이디
    storyType: StoryType, // 동화 타입
    onBack: () -> Unit,
    onHome: () -> Unit,
    onLogoClick: () -> Unit,
    vm: StoryViewModel = hiltViewModel()
) {
    val uiState by vm.storyUiState.collectAsStateWithLifecycle()
    val isTtsReady by vm.isTtsReady.collectAsStateWithLifecycle()

    val layoutDirection = LocalLayoutDirection.current

    // storyId 바뀔 때마다 데이터 로드
    LaunchedEffect(storyId, storyType) {
        vm.fetchStory(storyId, storyType)
    }

    BackHandler { onBack() }

    Scaffold(
        topBar = {
            Column (Modifier.fillMaxWidth()) {
                AppTopBar(onLogoClick=onLogoClick) // 기존 바
                Row(
                    modifier = Modifier
                        .clickable{
                            when (val state = uiState) {
                                is StoryUiState.Success -> {
                                   if (state.isPrologue) onBack() // 프롤로그 > 이전 스크린
                                   else vm.goToPrologue() // 엔딩 포함 페이지 > 프롤로그 스크린
                                }
                                else -> onBack()
                            }
                        }
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
            // 네비게이션 바만큼 여백
            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    // top 부분 제외 여백 설정
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    end = innerPadding.calculateEndPadding(layoutDirection),
                    bottom = innerPadding.calculateBottomPadding()),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                // 조회 로딩 중
                is StoryUiState.Loading -> {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.main_orange), // 주황색
                        trackColor = Color.Gray.copy(alpha = 0.5f),
                        strokeWidth = 4.dp
                    )
                }
                // 조회 오류
                is StoryUiState.Error -> {
                    LoadErrorView(
                        message = state.message,
                        onRetry = {  }
                    )
                }
                // 조회 성공
                is StoryUiState.Success -> {

                    // 맨 처음 프롤로그 화면
                    if (state.isPrologue) {
                        StoryPrologueScreen(
                            story = state.storyData,
                            storyType = storyType,
                            onReadClick = { vm.goToReader() }
                        )
                    } else {
                        // 동화 진행 화면
                        StoryReadingScreen(
                            pages = state.pagesData,
                            storyType = storyType,
                            isTtsMode = state.isTtsMode,
                            isReady = isTtsReady,
                            onTtsModeChange = { vm.changeTtsMode() },
                            onHome = onHome,
                            onPrologue = { vm.goToPrologue() },
                            onSpeakPage = { vm.speakPage(it) },
                            onStopSpeaking = { vm.stopSpeaking() }
                        )
                    }
                }
            }
        }
    }
}