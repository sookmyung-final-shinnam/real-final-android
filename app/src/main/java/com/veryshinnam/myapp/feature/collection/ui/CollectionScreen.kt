package com.veryshinnam.myapp.feature.collection.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.common.component.WarningSimpleSheet
import com.veryshinnam.myapp.feature.collection.component.CollectionCharacterGrid
import com.veryshinnam.myapp.feature.collection.component.CollectionFilterButtons
import com.veryshinnam.myapp.feature.home.component.HomeUserItem

@Composable
fun CollectionScreen(
    onBack: () -> Unit,
    onItemClick: (Long) -> Unit,
    onLogoClick: () -> Unit,
    horizontalPadding: Dp = 16.dp,
    cardPadding: Dp = 20.dp, // 텍스트 양옆 패딩
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    vm: CollectionViewModel = hiltViewModel()
) {
    // ViewModel 상태 구독
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    // 캐릭터 상세 > 보관함 화면: 세로 모드
    val context = LocalContext.current
    SideEffect { (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }

    var isSimpleWarning by remember { mutableStateOf(false) } // 단순 경고창
    var SimpleWarningText by remember { mutableStateOf("") }


    BackHandler { onBack() }

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = {
            // 상태바 만큼 여백 & 상단 로고
            Column {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                LogoBar(onLogoClick = onLogoClick)
            }
        },
        bottomBar = {
            // 네비게이션바 만큼 여백
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            BackButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .zIndex(1f),
                onBackClick = onBack
            )

            when (val state = uiState) {
                // 조회 로딩
                is CollectionUiState.Loading -> {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.main_orange), // 주황색
                        trackColor = Color.Gray.copy(alpha = 0.5f),
                        strokeWidth = 4.dp
                    )
                }

                // 조회 오류
                is CollectionUiState.Error -> {
                    LoadErrorView(
                        message = state.message,
                        onRetry = {
                            val lastFilter = (vm.selectedFilter.value)
                            vm.selectFilter(lastFilter)
                        }
                    )
                }

                // 조회 성공
                is CollectionUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = horizontalPadding)
                    ) {
                        // 보관함 상단
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.12f) // 화면의 40% 고정
                        ) {
                            Image(
                                painter = painterResource(R.drawable.img_rabbit_cut),
                                contentDescription = "설명하는 토끼 이미지",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 20.dp),
                                contentScale = ContentScale.Fit
                            )

                            HomeUserItem(
                                painter = painterResource(R.drawable.img_character_yellow),
                                contentDescription = "모은 캐릭터 수",
                                value = "${state.collectionDataList.size}",
                                color = colorResource(R.color.blue_gray),
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth(0.3f)
                                    .fillMaxHeight(0.6f)
                            )
                        }

                        // 설명 텍스트
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(Color.White, shape = RoundedCornerShape(16.dp))
                                .border(
                                    4.dp,
                                    colorResource(R.color.blue_gray),
                                    RoundedCornerShape(16.dp))
                                .padding(horizontal = cardPadding, vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
//                            Text(
//                                text = "지금까지 만든 캐릭터들을 모아봤어요.\n 좋아하는 캐릭터 5명을 표시해주면 홈 화면에서도 만나 볼 수 있어요!",
//                                style = textStyle.copy(
//                                    fontWeight = FontWeight.Bold,
//                                    lineBreak = LineBreak.Heading,
//                                    hyphens = Hyphens.None,
//                                    localeList = LocaleList("ko")
//                                ),
//                                softWrap = true
//                            )
                            Text(
                                text = "지금까지 만든 캐릭터들을 모아봤어요.\n 좋아하는 캐릭터 5명을 표시해주면 홈 화면에서도 만나 볼 수 있어요!".replace("", "\u200B"),
                                style = textStyle.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                                softWrap = true
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            // 캐릭터 성별 필터
//                            Spacer(modifier = Modifier.height(sectionSpacing))
                            CollectionFilterButtons(
                                selectedFilter = state.selectedFilter,
                                onFilterClick = { filter -> vm.selectFilter(filter) },
                                modifier = Modifier.fillMaxWidth()
                            )
//                            Spacer(modifier = Modifier.height(sectionSpacing))

                            // 캐릭터 아이템 그리드 (3*3)
                            CollectionCharacterGrid(
                                data = state.collectionDataList,
                                onFavoriteClick = { id ->
                                    vm.updateFavorite(id) { text ->
                                        isSimpleWarning = true
                                        SimpleWarningText = text
                                    }
                                },
                                onItemClick = { item ->
                                    if (item.image.isNullOrBlank()) {
                                        isSimpleWarning = true
                                        SimpleWarningText = "아직 캐릭터와 동화가 만들어지고 있어요.\n조금만 기다려주세요!"
                                    } else {
                                        onItemClick(item.id)
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }

    if (isSimpleWarning) {
        WarningSimpleSheet(
            warningText = SimpleWarningText,
            onDismiss = { isSimpleWarning = false}
        )
    }
}