package com.veryshinnam.myapp.feature.story.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.core.orientation.OrientationManager
import com.veryshinnam.myapp.feature.story.content.StoryPrologueContent
import com.veryshinnam.myapp.feature.story.content.StoryReadingContent
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun StoryScreen(
    storyId: Long,        // 동화 아이디
    storyType: StoryType, // 동화 타입
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    vm: StoryViewModel = hiltViewModel()
) {
    val uiState by vm.storyUiState.collectAsStateWithLifecycle()
    val isPrologue by vm.isPrologue.collectAsStateWithLifecycle()
    val isTtsReady by vm.isTtsReady.collectAsStateWithLifecycle()

    // 가로 모드 고정
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        )
    }

    // storyId 바뀔 때마다 데이터 로드
    LaunchedEffect(storyId, storyType) {
        vm.fetchStory(storyId, storyType)
    }

    // 뒤로 가기
    BackHandler { if (isPrologue) onBack() else vm.goToPrologue() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 상단 로고
        LogoBar(
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues())
                .zIndex(2f)
                .align(Alignment.TopCenter),
            onLogoClick = onLogoClick
        )


        // 뒤로 가기 버튼
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(WindowInsets.statusBars.asPaddingValues())
                .zIndex(1f)
                .clearAndSetSemantics {
                    contentDescription = if (isPrologue) "이전 화면으로 돌아가기" else "프롤로그 화면으로 돌아가기"
                    role = Role.Button
                },
            onBackClick = { if (isPrologue) onBack() else vm.goToPrologue() }
        )

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
                if (isPrologue) {
                    StoryPrologueContent(
                        story = state.storyData,
                        storyType = storyType,
                        onReadClick = { vm.goToReader() }
                    )
                } else {
                    // 동화 진행 화면
                    StoryReadingContent(
                        pages = state.pagesData,
                        storyType = storyType,
                        isTtsMode = state.isTtsMode,
                        isReady = isTtsReady,
                        onTtsModeChange = { vm.changeTtsMode() },
                        onHome = onLogoClick,
                        onPrologue = { vm.goToPrologue() },
                        onSpeakPage = { vm.speakPage(it) },
                        onStopSpeaking = { vm.stopSpeaking() }
                    )
                }
            }
        }
    }
}
