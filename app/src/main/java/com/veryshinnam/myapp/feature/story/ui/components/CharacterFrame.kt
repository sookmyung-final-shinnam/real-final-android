package com.veryshinnam.myapp.feature.story.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.zIndex
import androidx.xr.compose.testing.toDp
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R

@Composable
fun CharacterFrame(
    imageUrl: String,
    modifier: Modifier = Modifier // 부모에게서 받은 높이를 가져옴
) {
//    // ui 위 액자 크기
//    var frameWidth by remember { mutableStateOf(0) }
//    var frameHeight by remember { mutableStateOf(0) }
//
//    val density = LocalDensity.current

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center // 중앙 정렬
    ) {

        // 액자 이미지
        Image(
            painter = painterResource(R.drawable.img_frame),
            contentDescription = "캐릭터 액자",
            modifier = Modifier
                .matchParentSize()
                .zIndex(1f),
            contentScale = ContentScale.Fit
        )


        AsyncImage(
            model = imageUrl,
            contentDescription = "캐릭터 이미지",
            modifier = Modifier
                .fillMaxWidth(0.64f)   // Box 전체 기준 68%
                .fillMaxHeight(0.75f),
            contentScale = ContentScale.Crop
        )
    }
}