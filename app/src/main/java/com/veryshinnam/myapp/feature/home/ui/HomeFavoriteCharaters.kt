package com.veryshinnam.myapp.feature.home.ui

import android.R.attr.shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.home.data.dto.FavoriteCharacter

@Composable
fun HomeFavoriteCharacters (
    modifier: Modifier = Modifier,
    characters: List<FavoriteCharacter>,
    cornerRadius: Dp = 20.dp,
    spaceBetween: Dp = 12.dp,
    onCharacterClick: (Long) -> Unit = {}
) {

    val n = characters.size
    var center by remember { mutableStateOf(0) }

    fun next() { if (n > 0) center = (center + 1) % n }
    fun prev() { if (n > 0) center = (center - 1 + n) % n }

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            // 액자 이미지 배경
            Image(
                painter = painterResource(id = R.drawable.img_home_frame),
                contentDescription = "액자 이미지",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillWidth
            )

            // 왼쪽 화살표
            IconButton(
                onClick = { prev() },
                enabled = n > 1,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    Icons.Filled.ArrowBack, contentDescription = "이전",
                    tint = colorResource(id = R.color.main_orange)
                )
            }

            // 캐릭터 3명 표시
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val (leftIdx, centerIdx, rightIdx) = when {
                    n >= 3 -> {
                        val l = (center - 1 + n) % n
                        val r = (center + 1) % n
                        Triple(l, center, r)
                    }

                    /// 3명 이하의 경우
                    n == 2 -> Triple(null, center, (center + 1) % 2)
                    n == 1 -> Triple(null, 0, null)
                    else -> Triple(null, null, null)
                }

                CharacterSlot(
                    character = leftIdx?.let { characters[it] },
                    modifier = Modifier.weight(0.25f).fillMaxHeight(),
                    cornerRadius = cornerRadius
                )

                Spacer(Modifier.width(spaceBetween))

                CharacterSlot(
                    character = centerIdx?.let { characters[it] },
                    modifier = Modifier.weight(0.5f).fillMaxHeight(),
                    cornerRadius = cornerRadius,
                    isCenter = true,
                     onClick = { c -> c?.let { onCharacterClick(it.id) }}
                )

                Spacer(Modifier.width(spaceBetween))

                CharacterSlot(
                    character = rightIdx?.let { characters[it] },
                    modifier = Modifier.weight(0.25f).fillMaxHeight(),
                    cornerRadius = cornerRadius
                )
            }

            // 오른쪽 화살표
            IconButton(
                onClick = { next() },
                enabled = n > 1,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    Icons.Filled.ArrowForward, contentDescription = "다음",
                    tint = colorResource(id = R.color.main_orange)
                )
            }
        }
}

@Composable
private fun CharacterSlot(
    character: FavoriteCharacter?,
    modifier: Modifier,
    cornerRadius: Dp,
    isCenter: Boolean = false,
    dimAlpha: Float = 0.5f,
    onClick: (FavoriteCharacter?) -> Unit = {}
) {
    val isClickable = isCenter && character != null
    val shape = RoundedCornerShape(cornerRadius)

    Card(
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier.then(
            if (isClickable) {
                // 가운데 슬롯만 클릭 가능 + 기본 리플 표시
                Modifier.clickable(
                    indication = rememberRipple(
                        color = colorResource(id = R.color.main_orange),
                        bounded = false
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                ) { onClick(character) }
            } else {
                Modifier    // 양옆 슬롯은 클릭 x
            }
        )
    ) {
        if (character == null) {
            Box(Modifier.fillMaxSize())
        } else {
            AsyncImage(
                model = character.imageUrl,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape),

                // 양 옆 이미지 어둡게
                colorFilter = if (!isCenter)
                    ColorFilter.tint(
                        color = Color.Black.copy(alpha = dimAlpha),
                        blendMode = BlendMode.Multiply
                    )
                else null
            )
        }
    }
}