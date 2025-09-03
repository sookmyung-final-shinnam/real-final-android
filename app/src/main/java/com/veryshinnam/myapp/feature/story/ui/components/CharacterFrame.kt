package com.veryshinnam.myapp.feature.story.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R

@Composable
fun CharacterFrame(
    cImage: String
) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // 중앙 정렬
    ) {

        // 액자 이미지
        Image(
            painter = painterResource(R.drawable.img_frame),
            contentDescription = "캐릭터 액자",
            modifier = Modifier
                .fillMaxHeight()
                .zIndex(1f),
            contentScale = ContentScale.FillHeight
        )


        // 캐릭터
        // 인데 액자안에 들어 올수있도록 위 액자 이미지의 너비의 0.7, 세로의 0.75
        AsyncImage(
            model = cImage,
            contentDescription = "캐릭터 이미지",
            modifier = Modifier
                .fillMaxHeight(0.75f) // 액자 높이의 0.75
                .fillMaxWidth(0.7f)   // 액자 너비의 0.7
                .zIndex(0f),           // 액자 안에 배치,
            contentScale = ContentScale.Crop
        )
    }
}

// 테스트
@Preview(showBackground = true)
@Composable
fun CharacterFramePreview() {
    CharacterFrame(
        cImage = "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/characters/character_18.png"
    )
}