package com.veryshinnam.myapp.feature.character.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.common.component.ShareSheet
import com.veryshinnam.myapp.common.component.WarningButtonSheet
import com.veryshinnam.myapp.feature.home.ui.HomeViewModel
import com.veryshinnam.myapp.feature.story.model.StoryType


@Composable
fun CharacterScreen(
    id: Long,
    onBack: () -> Unit,
    onStoryClick: (Long, StoryType) -> Unit,
    vm: CharacterViewModel = hiltViewModel()
) {
    val uiState by vm.charUiState.collectAsStateWithLifecycle()
    val activity = LocalActivity.current

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var isWarning by remember { mutableStateOf(false) }
    var warnedStoryId by remember { mutableStateOf<Long?>(null) }

    var isSharing by remember { mutableStateOf(false) }
    var sharedStoryUrl by remember { mutableStateOf<String?>(null) }

    // 가로모드 + id가 바뀌면 재로딩
    LaunchedEffect(id) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        vm.fetchCharacter(id)
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
                        character = state.characterData,
                        onFavoriteClick = { id -> vm.updateFavorite(id) },
                        onStoryClick = onStoryClick,
                        onLockerClick = { storyId ->
                            isWarning = true
                            warnedStoryId = storyId
                        },
                        onFlip = { isFront ->
                            if (!isFront) {
                                vm.refreshStories(state.characterData.id)
                            }
                        },
                        onShareClick = { storyUrl ->
                            isSharing = true
                            sharedStoryUrl = storyUrl
                        }
                    )
                }
            }
        }
    }

    if (isWarning && warnedStoryId != null) {
        WarningButtonSheet(
            warningText = "동영상 동화의 잠금을 해제할까요?\n" +
                    "아이템 1개가 소진돼요!",
            confirmText = "해제하기",
            onDismiss = { isWarning = false },
            onConfirm = {
                    vm.fetchVideoStory(warnedStoryId!!)
                    isWarning = false
            }
        )
    }

    if (isSharing && sharedStoryUrl != null) {
        ShareSheet(
            shareUrl = "https://youtu.be/CNACFyk39ZE",
//            shareUrl = sharedStoryUrl!!,
            shareText = "카카오톡으로 공유하기",
            onDismiss = { isSharing = false },
            onShare = {
                try {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "https://youtu.be/CNACFyk39ZE")
                        setPackage("com.kakao.talk") // 카톡 패키지 지정
                    }
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "카카오톡이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            },
            onCopy = {
                clipboardManager.setText(AnnotatedString(sharedStoryUrl!!))
//                Toast.makeText(context, "링크가 클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
            }
        )
    }
}