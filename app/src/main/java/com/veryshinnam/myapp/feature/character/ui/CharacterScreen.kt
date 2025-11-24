package com.veryshinnam.myapp.feature.character.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.common.component.ShareSheet
import com.veryshinnam.myapp.common.component.TargetImage
import com.veryshinnam.myapp.common.component.WarningConfirmSheet
import com.veryshinnam.myapp.common.component.WarningSheet
import com.veryshinnam.myapp.common.model.ManualState
import com.veryshinnam.myapp.core.orientation.OrientationManager
import com.veryshinnam.myapp.feature.character.component.CharacterCardLeft
import com.veryshinnam.myapp.feature.character.component.CharacterCardRight
import com.veryshinnam.myapp.feature.character.component.CharacterTabButton
import com.veryshinnam.myapp.feature.story.model.StoryType


@Composable
fun CharacterScreen(
    id: Long,
    onBack: () -> Unit,
    onLogoClick: () -> Unit,
    onStoryClick: (Long, StoryType) -> Unit,
    goToNextManual: () -> Unit,
    xMoving: Dp = 60.dp,
    verticalPadding: Dp = 10.dp,
    manualTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = Bold),
    vm: CharacterViewModel = hiltViewModel()
) {
    val density = LocalDensity.current

    // 상태 구독
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val manualState by vm.manualState.collectAsStateWithLifecycle()
    val manualStep by vm.manualStep.collectAsStateWithLifecycle()
    val manualMessage by vm.manualMessage.collectAsStateWithLifecycle()

    // 공유 및 클립보드
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var warnedStoryId by remember { mutableStateOf<Long?>(null) }
    var isWarning by remember { mutableStateOf(false) }

    var isVideoMaking by remember { mutableStateOf(false) }
    var isLinkNotExisting by remember { mutableStateOf(false) }
    var isLinkSharing by remember { mutableStateOf(false) }

    var youtubeLink by remember { mutableStateOf<String?>(null) }

    // ui 변수
    var isFront by rememberSaveable { mutableStateOf(true) } // 카드 앞뒷면 구분

    // 매뉴얼 변수
    var tabRect by remember { mutableStateOf<Rect?>(null) }  // 탭 버튼 위치
    var lockerRect by remember { mutableStateOf<Rect?>(null) } // 움직이는 동화 잠금 위치


    // 가로 모드 고정
    SideEffect {
        OrientationManager.setOrientation?.invoke(
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        )
    }

    // 캐릭터 id 바뀌면 재로딩
    LaunchedEffect(id, manualState) {
        if (manualState != ManualState.NONE || id == -1L) {
            vm.startManual()
        } else {
            vm.fetchCharacter(id)
        }
    }

    LaunchedEffect(manualStep) {
        if (manualStep == 3) {
            isFront = false
        }
        if (manualStep == vm.manuals.size) {
            goToNextManual()
        }
    }

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
                        isFront = isFront,
                        onFlip = { newFront ->
                            val prevFront = isFront // 이전 면
                            isFront = newFront      // 업데이트된 면

                            // 뒤 > 앞 때만 refresh
                            if (!prevFront && newFront) {
                                vm.refreshStories(state.characterData.id)
                            }
                        },
                        onStoryClick = onStoryClick,
                        onLockerClick = { storyId ->
                            isWarning = true
                            warnedStoryId = storyId },
                        onMakingClick = { isVideoMaking = true },
                        onShareClick = { storyYLink ->
                            if (!storyYLink.isNullOrBlank()) {
                                isLinkSharing = true
                                youtubeLink = storyYLink
                            } else {
                                // 링크 없음 처리
                                isLinkNotExisting = true
                            }
                        },
                        onTabRect = { rect ->
                            if (manualState == ManualState.START && tabRect == null) {
                                tabRect = rect
                            }
                        },
                        onLockerRect = { rect ->
                            if (manualState == ManualState.START
                                && manualStep == 3 && lockerRect == null) {
                                lockerRect = rect
                                Log.d("manual", "rect final update: $rect")
                            }
                        },
                        modifier = Modifier
                            .aspectRatio(2f) // 카드 비율
                            .zIndex(1f)
                    )
                }
            }
        }
    }

    // 움직이는 동화 잠금 해제
    if (isWarning && warnedStoryId != null) {
        WarningConfirmSheet(
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

    // 움직이는 동화 제작중
    if (isVideoMaking) {
        WarningSheet(
            warningText = "아직 움직이는 동화를 만들고 있는 중이에요.\n조금만 더 기다려주세요!\n\n" +
                    "혹시 오래 기다렸는데도 동화가 안 뜨면,\n저희에게 알려주세요!",
            onDismiss = { isVideoMaking = false },
        )
    }

    // 동화 유튜브 링크 없음
    if (isLinkNotExisting) {
        WarningSheet(
            warningText = "아직 카카오톡 링크를 만들고 있는 중이에요.\n조금만 더 기다려주세요!\n\n" +
                    "혹시 오래 기다렸는데도 링크가 안 뜨면,\n저희에게 알려주세요!",
            onDismiss = { isLinkNotExisting = false },
        )
    }

    // 카톡으로 공유
    if (isLinkSharing && !youtubeLink.isNullOrBlank()) {
        ShareSheet(
            urlText = youtubeLink!!,
            confirmText = "카카오톡으로 공유하기",
            onDismiss = { isLinkSharing = false },
            onShare = {
                try {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, youtubeLink)
                        setPackage("com.kakao.talk") // 카톡 패키지 지정
                    }
                    context.startActivity(intent)
                } catch (_: Exception) {
                    Toast.makeText(context, "카카오톡이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            },
            onCopy = {
                clipboardManager.setText(AnnotatedString(youtubeLink!!))
            }
        )
    }

    if (manualState != ManualState.NONE) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(20f)
                .background(
                    if (manualStep == 1 || manualStep == 3)
                        Color.Transparent
                    else
                        Color.Black.copy(alpha = 0.5f)
                )
                .then(
                    when (manualState) {
                        ManualState.START -> Modifier.pointerInput(Unit) {
                            detectTapGestures { vm.nextManual() }
                        }

                        ManualState.STOP -> Modifier.pointerInput(Unit) {
                            detectTapGestures { vm.hideManual() }
                        }

                        else -> Modifier
                    }
                )
        ) {
            Text(
                text = "그만 들을래요.",
                color = colorResource(R.color.main_orange),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(30.dp)
                    .clickable {
                        when (manualState) {
                            ManualState.START -> vm.stopManual()
                            ManualState.STOP -> vm.hideManual()
                            else -> {}
                        }
                    }
                    .zIndex(11f)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                // 토끼 이미지
                Image(
                    painter = painterResource(R.drawable.img_home_squirrel),
                    contentDescription = "다람쥐 이미지",
                    modifier = Modifier
                        .fillMaxHeight(0.22f)
                        .padding(start = 26.dp),
                    contentScale = ContentScale.Fit
                )

                // 메시지 박스
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .border(
                            4.dp,
                            colorResource(R.color.blue_gray),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                        .zIndex(50f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = manualMessage.replace("", "\u200B"),
                        style = manualTextStyle.copy(
                            lineHeight = manualTextStyle.fontSize * 1.2f
                        ),
                    )
                }
            }

            when (manualStep) {
                2 -> { tabRect?.let { it ->
                    // 탭버튼
                    CharacterTabButton(
                        modifier = Modifier
                            .absoluteOffset(
                                x = with(density) { it.left.toDp() },
                                y = with(density) { it.top.toDp() }
                            )
                            .size(
                                with(density) { it.width.toDp() },
                                with(density) { it.height.toDp() }
                            ),
                        onClick = {
                            vm.nextManual()
                        },
                        alpha = 0.5f,
                    )
                }
                }
                4 -> { lockerRect?.let { it ->
                    // 잠금 해제
                    Box(
                        modifier = Modifier
                            .absoluteOffset(
                                x = with(density) { it.left.toDp() },
                                y = with(density) { it.top.toDp() })
                            .size(
                                with(density) { it.width.toDp() },
                                with(density) { it.height.toDp() }
                            )
                            .clip(RoundedCornerShape(16.dp))   // 여기에 clip
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_locker),
                            contentDescription = "잠금 이미지",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
                }
                5 -> { lockerRect?.let { it ->
                    // 도토리
                    Box(
                        modifier = Modifier
                            .absoluteOffset(
                                x = with(density) { it.left.toDp() },
                                y = with(density) { it.top.toDp() })
                            .size(
                                with(density) { it.width.toDp() },
                                with(density) { it.height.toDp() }
                            )
                            .clip(RoundedCornerShape(16.dp))   // 여기에 clip
                    ) {

                    }
                }
                }
                6 -> {
                    // 카톡 버튼

                }
            }
        }
    }
}
