package com.veryshinnam.myapp.feature.character.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R

@Composable
fun CharacterImage (
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var bottomHeightDp by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        // 발판
        Image(
            painter = painterResource(R.drawable.img_character_bottom),
            contentDescription = "발판",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.6f)
                .aspectRatio(2f)
                .onGloballyPositioned { it ->
                    // 현재 기기 화면에서의 해당 이미지 객체
                    bottomHeightDp = with(density) { it.size.height.toDp() }
                },
            contentScale = ContentScale.Fit
        )

        // 캐릭터
        AsyncImage(
            model = imageUrl,
            contentDescription = "캐릭터",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -(bottomHeightDp/2 + 50.dp)) // 발판 높이의 절반만큼
                .zIndex(1f)              // 위로
                .fillMaxHeight(0.5f),
            contentScale = ContentScale.Fit
        )
    }
}
