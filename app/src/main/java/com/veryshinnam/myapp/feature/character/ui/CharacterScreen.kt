package com.veryshinnam.myapp.feature.character.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.common.component.ScreenOrientation
import com.veryshinnam.myapp.common.component.ShareSheet
import com.veryshinnam.myapp.common.component.WarningSheet
import com.veryshinnam.myapp.feature.character.component.CharacterCardLeft
import com.veryshinnam.myapp.feature.character.component.CharacterCardRight
import com.veryshinnam.myapp.feature.story.model.StoryType


@Composable
fun CharacterScreen(
    id: Long,
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    onStoryClick: (Long, StoryType) -> Unit,
    xMoving: Dp = 60.dp,
    verticalPadding: Dp = 10.dp,
    vm: CharacterViewModel = hiltViewModel()
) {
    val uiState by vm.charUiState.collectAsStateWithLifecycle()

    // 공유 및 클립보드
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var isWarning by remember { mutableStateOf(false) }
    var warnedStoryId by remember { mutableStateOf<Long?>(null) }

    var isSharing by remember { mutableStateOf(false) }
    var sharedStoryUrl by remember { mutableStateOf<String?>(null) }

    // 가로 모드
    ScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    // 캐릭터 id 바뀌면 재로딩
    LaunchedEffect(id) { vm.fetchCharacter(id) }

    // 뒤로 가기
    BackHandler { onBack() }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_yellow)),
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
                .padding(WindowInsets.statusBars.asPaddingValues())
                .zIndex(1f)
                .align(Alignment.TopStart),
            onBackClick = onBack
        )

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = verticalPadding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End // Row 안 가로 배치 방향
                ) {
                    // 왼쪽 캐릭터 이미지 카드
                    CharacterCardLeft(
                        character = state.characterData,
                        onFavoriteClick = { id -> vm.updateFavorite(id) },
                        modifier = Modifier
                            .aspectRatio(0.8f) // 카드 비율
                            .offset(x = xMoving)       // 오른쪽 이동
                    )

                    // 오른쪽 캐릭터 정보 카드
                    CharacterCardRight(
                        character = state.characterData,
                        onStoryClick = onStoryClick,
                        onLockerClick = { storyId ->
                            isWarning = true
                            warnedStoryId = storyId },
                        onFlip = { isFront ->
                            if (!isFront) {
                                vm.refreshStories(state.characterData.id)
                            } },
                        onShareClick = { storyUrl ->
                            isSharing = true
                            sharedStoryUrl = "https://youtu.be/cX2PU3aEBL8" },
                        modifier = Modifier
                            .aspectRatio(2f) // 카드 비율
                            .zIndex(1f)
                    )
                }
            }
        }
    }

    if (isWarning && warnedStoryId != null) {
        WarningSheet(
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
            urlText = "https://youtu.be/cX2PU3aEBL8",
//            shareUrl = sharedStoryUrl!!,
            confirmText = "카카오톡으로 공유하기",
            onDismiss = { isSharing = false },
            onShare = {
                try {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "https://youtu.be/cX2PU3aEBL8")
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