package com.veryshinnam.myapp.feature.character.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.character.model.CharacterData
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun CharacterCardRight(
    character: CharacterData,     // 캐릭터 정보
    onFlip: (Boolean) -> Unit,
    onStoryClick: (Long, StoryType) -> Unit,
    onLockerClick: (Long) -> Unit,
    onMakingClick: () -> Unit,
    onShareClick: (String?) -> Unit,
    cardPadding: Dp = 24.dp,
    tabPadding: Dp = 12.dp,
    modifier: Modifier = Modifier // 부모가 넘겨준 크기
) {
    // 카드 앞뒷면 구분
    var isFront by rememberSaveable(character.id) { mutableStateOf(true) }

    val density = LocalDensity.current.density
    val cameraDistancePx = 20 * density * 100f // 원근감

    // 0f(앞면) <-> 180f(뒷면)로 회전
    val rotation by animateFloatAsState(
        targetValue = if (isFront) 0f else 180f, // 회전 값
        animationSpec = tween(
            durationMillis = 550,        // 애니메이션 지속 시간
            easing = FastOutSlowInEasing // 빠르게 시작 → 천천히 끝
        )
    )

    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {         // 카드 회전
                    rotationX = rotation // x축 기준
                    cameraDistance = cameraDistancePx
                }
                .clickable {
                    isFront = !isFront
                    onFlip(isFront) // 앞 > 뒤 새로고침
                    }, // 카드 전체 클릭시 뒤집기
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.card_orange_90)
            )
        ) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // 90도 전까지는 앞면
                if (rotation <= 90f) {

                    // 앞면: 캐릭터 이름 + 기본 정보
                    CharacterRightFront(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = cardPadding, horizontal = cardPadding / 2),
                        character = character
                    )

                    // 공통 탭버튼
                    CharacterTabButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = tabPadding, end = tabPadding),
                        onClick = {
                            isFront = !isFront
                            onFlip(isFront) // 앞 > 뒤 새로고침
                        }
                    )
                } else {
                    // 넘어가면 뒷면 표시 + 회전 다시 보정
                    Box(Modifier.graphicsLayer { rotationX = 180f }) {

                        // 뒷면: 동화 정보
                        CharacterRightBack(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = cardPadding, horizontal = cardPadding / 2),
                            stories = character.stories,
                            onStoryClick = onStoryClick,
                            onLockerClick = onLockerClick,
                            onMakingClick = onMakingClick,
                            onShareClick = onShareClick
                        )

                        // 공통 탭버튼
                        CharacterTabButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = tabPadding, end = tabPadding),
                            onClick = { isFront = !isFront })
                    }
                }
            }
        }
    }
}