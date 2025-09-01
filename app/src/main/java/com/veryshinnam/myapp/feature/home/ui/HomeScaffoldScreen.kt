package com.veryshinnam.myapp.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar

@Composable
fun HomeScaffoldScreen (
    data: HomeUiState.HomeData,
    onSettingsClick: () -> Unit,
    onCheckInClick: () -> Unit,
    onDashboardClick: () -> Unit,   // bottom bar
    onCreationClick: () -> Unit,    // bottom bar
    onStorageClick: () -> Unit,     // bottom bar
    onCharacterClick: (Long) -> Unit
) {
    val config = LocalConfiguration.current // 현재 기기 화면 정보
    val screenW = config.screenWidthDp.dp
    val screenH = config.screenHeightDp.dp

    // 캘린더 사이즈
    val calendarSize = minOf(screenW, screenH) * 0.12f

    // 아이콘 사이즈
    val iconSize = minOf(screenW, screenH) * 0.08f

    // 폰트 사이즈
    val density = LocalDensity.current
    val fontSize = with(density) { (minOf(screenW, screenH) * 0.05f).toSp() }

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = { AppTopBar() },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.12f)
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.img_home_dashboard),
                    contentDescription = "대시보드",
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable(
                            indication = rememberRipple(
                                color = colorResource(id = R.color.main_orange),
                                bounded = false
                            ),
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onDashboardClick }
                )
                Image(
                    painter = painterResource(R.drawable.img_home_create),
                    contentDescription = "캐릭터 생성",
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable(
                            indication = rememberRipple(
                                color = colorResource(id = R.color.main_orange),
                                bounded = false
                            ),
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onCreationClick }
                )
                Image(
                    painter = painterResource(R.drawable.img_home_collection),
                    contentDescription = "보관함",
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable(
                            indication = rememberRipple(
                                color = colorResource(id = R.color.main_orange),
                                bounded = false
                            ),
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onStorageClick }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // 1 메인(유저 정보/즐찾 캐릭터)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // 유저 정보 - 생성한 캐릭터 수, 포인트
                Row(
                    modifier = Modifier.weight(0.2f).padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    // 마스코트 이미지
                    Image(
                        painter = painterResource(R.drawable.img_home_logo),
                        contentDescription = "마스코트 이미지",
                        modifier = Modifier.weight(0.4f)
                    )

                    // 캐릭터 수
                    UserInfoBox(
                        painter = painterResource(R.drawable.img_home_book),
                        contentDescription = "생성한 캐릭터 수",
                        value = "${data.myCharacters}",
                        iconSize = iconSize,
                        fontSize = fontSize,
                        modifier = Modifier
                            .weight(0.3f)
                            .padding(bottom = 8.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // 포인트 수
                    UserInfoBox(
                        painter = painterResource(R.drawable.img_point),
                        contentDescription = "모은 포인트 수",
                        value = "${data.points}",
                        iconSize = iconSize,
                        fontSize = fontSize,
                        modifier = Modifier
                            .weight(0.3f)
                            .padding(bottom = 8.dp)
                    )
                }

                // 유저 정보 - 닉네임
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .background(Color.White, shape = RoundedCornerShape(24.dp))
                        .border(
                            width = 4.dp,
                            color = colorResource(id = R.color. main_orange),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding( horizontal = 30.dp, vertical = 16.dp)
                ) {
                    Text(
                        // 닉네임
                        text = "안녕 ${data.username}! \n오늘은 무엇을 해볼까?",
                        fontSize = fontSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    // 환경 설정 버튼
                    IconButton(
                        onClick = { onSettingsClick() },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "환경설정",
                            modifier = Modifier.size(iconSize),
                            tint = colorResource(id = R.color.main_orange)
                        )
                    }
                }

                // 즐찾 캐릭터 액자 섹션
                HomeFavoriteCharacters(
                    modifier = Modifier.weight(0.8f),
                    characters = data.favoriteCharacters,
                    onCharacterClick = onCharacterClick
                )
            }

            // 2. 달력(출석체크)
            Image(
                painter = painterResource(R.drawable.img_home_check),
                contentDescription = "출석체크",
                modifier = Modifier
                    .size(calendarSize)
                    .align(Alignment.TopEnd)
                    .padding(end =12.dp)
                    .clickable(
                        indication = rememberRipple(
                            color = colorResource(id = R.color.main_orange),
                            bounded = false
                        ),
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onCheckInClick() }
            )
        }
    }
}

@Composable
fun UserInfoBox(
    painter: Painter,
    contentDescription: String,
    value: String,
    iconSize: Dp,
    fontSize: TextUnit,
    modifier: Modifier = Modifier
) {
    Box(
        // 테두리
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(24.dp))
            .border(
                width = 4.dp,
                color = colorResource(id = R.color.main_orange),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 아이콘 이미지
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                // 개수
                text = value,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}