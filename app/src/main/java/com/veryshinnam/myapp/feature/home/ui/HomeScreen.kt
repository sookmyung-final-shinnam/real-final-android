package com.veryshinnam.myapp.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.veryshinnam.myapp.feature.home.data.dto.CharacterShortResult
import com.veryshinnam.myapp.feature.home.ui.HomeViewModel

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit = {},
    onCreationClick: () -> Unit = {},
    onStorageClick: () -> Unit = {},
    onCharacterClick: (Long) -> Unit = {},
    vm: HomeViewModel =  hiltViewModel()
) {
    // 홈화면 상태 관리
    val uiState by vm.homeUiState.collectAsState()

    if (uiState.isLoading) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // 1. 사용자 정보 컨테이너
                HomeUserInfo(
                    nickname = uiState.nickname,
                    points = uiState.points,
                    cCnt = uiState.characterCnt,
                    sCnt = uiState.storyCnt,
                    onSettingsClick = onSettingsClick
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 2. 즐겨찾기 캐릭터 컨테이너
                HomeFavoriteCharacter(
                    characters = uiState.characters,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // 남은 높이 모두 차지
                    onCharacterClick = onCharacterClick
                )
            }

            // 3. 메뉴 버튼 그룹
            HomeMenuGroup(
                onCreationClick = onCreationClick,
                onStorageClick = onStorageClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd) // 우측 하단
            )
        }
    }
}

@Composable
fun HomeUserInfo(
    nickname: String?,
    points: Int,
    cCnt: Int,
    sCnt: Int,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEDEDED))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("마스코트 캐릭터", modifier = Modifier.padding(end = 12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text("안녕, ${nickname ?: "사용자"}!", style = MaterialTheme.typography.titleMedium)
            Text("생성한 캐릭터: $cCnt, 동화: $sCnt")
            Text("보유 포인트: $points")
        }

        Button(onClick = onSettingsClick) {
            Text("환경설정")
        }
    }
}

@Composable
fun HomeFavoriteCharacter(
    characters: List<CharacterShortResult>,
    modifier: Modifier = Modifier,
    onCharacterClick: (Long) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        if (characters.isEmpty()) {
            Text("즐겨찾는 요정이 없습니다.")
        } else {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(characters) { character ->
                    AsyncImage(
                        model = character.firstImage,   // 이미지 URL
                        contentDescription = "캐릭터 이미지",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onCharacterClick(character.id)}
                    )
                }
            }
        }
    }
}

@Composable
fun HomeMenuGroup(
    onCreationClick: () -> Unit,
    onStorageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .padding(end = 16.dp, bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally, // 가운데 정렬
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SquareButton(
            text = "생성",
            desc = "생성 버튼",
            icon = Icons.Default.Add,
            onClick = onCreationClick
        )

        SquareButton(
            text = "보관함",
            desc = "보관함 버튼",
            icon = Icons.Default.Lock,
            onClick = onStorageClick
        )
    }
}


// 정사각형 버튼
@Composable
fun SquareButton(
    text: String,
    desc: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.12f)  // 너비를 전체의 25%로 설정
            .aspectRatio(1f)     // 정사각형 유지
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // 가운데 정렬
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = desc,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text)
            }
        }
    }
}