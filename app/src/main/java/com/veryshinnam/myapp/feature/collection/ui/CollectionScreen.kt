package com.veryshinnam.myapp.feature.collection.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoBar
import com.veryshinnam.myapp.common.component.BackButton
import com.veryshinnam.myapp.common.component.LoadErrorView
import com.veryshinnam.myapp.common.component.ScreenOrientation
import com.veryshinnam.myapp.common.component.UserInfo
import com.veryshinnam.myapp.common.component.WarningSimpleSheet
import com.veryshinnam.myapp.feature.collection.component.CollectionCharacterGrid
import com.veryshinnam.myapp.feature.collection.component.CollectionFilterButtons
import com.veryshinnam.myapp.common.component.UserItem

@Composable
fun CollectionScreen(
    onBack: () -> Unit,
    onItemClick: (Long) -> Unit,
    onLogoClick: () -> Unit,
    spacerPadding: Dp = 10.dp,
    horizontalPadding: Dp = 16.dp,
    vm: CollectionViewModel = hiltViewModel()
) {
    // ViewModel 상태 구독
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    // 캐릭터 상세 > 보관함 화면: 세로 모드
//    val context = LocalContext.current
//    SideEffect { (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }

    var isSimpleWarning by remember { mutableStateOf(false) } // 단순 경고창
    var SimpleWarningText by remember { mutableStateOf("") }

    // 세로 모드
    ScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    // 뒤로 가기
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
            // 뒤로 가기 버튼
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
                        UserInfo(
                            modifier = Modifier,
                            isItem = true, // 아이템 설명 존재
                            itemCount = state.collectionDataList.size,
                            itemImage =  painterResource(R.drawable.img_character_yellow),
                            itemDescription = "보관함 캐릭터 수",
                            animalImage = painterResource(R.drawable.img_rabbit_cut),
                            animalDescription = "보관함 설명 토끼 이미지",
                            cardColor = colorResource(R.color.blue_gray),
                            cardText =  "지금까지 만든 캐릭터들이에요.\n좋아하는 캐릭터 5명을 표시해 주세요. 그러면 홈 화면에서 바로 만나 볼 수 있어요!",
                            spanText = "캐릭터 5명",
                            spanColor = colorResource(R.color.blue_sky)
                        )

                        // 캐릭터 성별 필터 버튼
                        Spacer(Modifier.height(spacerPadding))
                        CollectionFilterButtons(
                            selectedFilter = state.selectedFilter,
                            onFilterClick = { filter -> vm.selectFilter(filter) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // 캐릭터 아이템 그리드 (3*3)
                        Spacer(Modifier.height(spacerPadding /2))
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
                            cellPadding = spacerPadding / 2,
                            modifier = Modifier.fillMaxSize()
                        )

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