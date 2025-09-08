package com.veryshinnam.myapp.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.home.model.FavoriteData
import com.veryshinnam.myapp.feature.home.model.UserData
import com.veryshinnam.myapp.feature.home.ui.component.HomeBottomButtons
import com.veryshinnam.myapp.feature.home.ui.component.HomeFavoriteCarousel
import com.veryshinnam.myapp.feature.home.ui.component.HomeUserInfo

@Composable
fun HomeMainScreen (
    user: UserData,
    favorites: List<FavoriteData>,
    lastSelectedId: Long?,
    randomMessage: String,
    onSettingsClick: () -> Unit,
    onCheckInClick: () -> Unit,
    onDashboardClick: () -> Unit,   // bottom bar
    onCreationClick: () -> Unit,    // bottom bar
    onCollectionClick: () -> Unit,     // bottom bar
    onCharacterClick: (Long) -> Unit, // 캐릭터 상세
    onUpdateLastSelected: (Long) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 1 메인(유저 정보/즐찾 캐릭터)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 유저 정보
            HomeUserInfo(user, randomMessage, onSettingsClick,
                modifier = Modifier.padding(horizontal = 16.dp).weight(0.25f).fillMaxWidth())

            // 즐찾 캐릭터 캐러셀
            HomeFavoriteCarousel(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxWidth(),
                characters = favorites,
                lastSelectedId = lastSelectedId,
                onCharacterClick = { id ->
                    onUpdateLastSelected(id)
                    onCharacterClick(id)
                }
            )

            // 바텀 버튼 (대시보드 - 생성 - 보관함)
            HomeBottomButtons(onDashboardClick, onCreationClick, onCollectionClick,
                modifier = Modifier.padding(horizontal = 16.dp).weight(0.15f).fillMaxWidth())
        }

        // 2. 달력(출석체크)
        Image(
            painter = painterResource(R.drawable.img_home_check),
            contentDescription = "출석체크",
            modifier = Modifier
                .fillMaxHeight(0.06f)
                .padding(horizontal = 16.dp)
                .align(Alignment.TopEnd)
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