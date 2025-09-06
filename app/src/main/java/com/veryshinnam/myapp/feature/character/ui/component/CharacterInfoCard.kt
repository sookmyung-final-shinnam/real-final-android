package com.veryshinnam.myapp.feature.character.ui.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun CharacterInfoCard(
    cName: String,   // 캐릭터 이름
    cGender: String, // 캐릭터 성별
    cAge: Int,       // 캐릭터 나이
    cPersonality: String,   // 캐릭터 성격
    cBirth: String?,        // 캐릭터 생일
    modifier: Modifier      // 부모가 넘겨준 크기
) {
    // 카드 앞뒷면 구분
    var isFront by rememberSaveable { mutableStateOf(true) }

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


    Column(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {         // 카드 회전
                    rotationX = rotation // x축 기준
                    cameraDistance = cameraDistancePx
                }
                .clickable { isFront = !isFront }, // 카드 전체 클릭시 뒤집기
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.card_orange_90)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // 90도 전까지는 앞면
                if (rotation <= 90f) {
                    CharacterCardFront(
                        cName, cGender, cAge, cPersonality, cBirth)
                } else {
                    // 넘어가면 뒷면 표시
                    // 회전으로 인한 보정
                    Box(Modifier.graphicsLayer { rotationX = 180f }) {
                        BackFace()
                    }
                }

                // 공통 탭버튼

            }
        }
    }
}

@Composable
private fun FrontFace() {
    // ✅ 여기 원하는 "앞면" UI 작성
    Text("캐릭터 기본 정보(앞면)")
}

@Composable
private fun BackFace() {
    // ✅ 여기 원하는 "뒷면" UI 작성
    Text("캐릭터 성격 설명(뒷면)")
}