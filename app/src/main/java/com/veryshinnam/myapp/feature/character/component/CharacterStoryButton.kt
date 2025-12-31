package com.veryshinnam.myapp.feature.character.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.model.ImageType
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun CharacterStoryButton(
    storyId: Long,          // 동화 아이디
    imageUrl: ImageType, // 종이 동화 표지
    youTubeLink: String?,    // 유튜브 링크
    infoText: String,  // 동화 타입 텍스트
    storySize: Float = 0.7f,
    storyRadius: Dp = 16.dp,
    kakaoSize: Float = 0.35f,
    kakaoRadius: Dp = 12.dp,
    onStoryClick: (Long, StoryType) -> Unit,
    onKakaoClick: (String?) -> Unit,
    modifier: Modifier
) {
    // 동화 버튼
    Box(
        modifier = modifier.fillMaxHeight(storySize),
        contentAlignment = Alignment.TopEnd
    ) {
        Button(
            onClick = {
                onStoryClick(storyId, StoryType.IMAGE) }, // IMAGE는 항상 이동
            modifier = Modifier.aspectRatio(1f),
            shape = RoundedCornerShape(storyRadius),
            contentPadding = PaddingValues(0.dp) // 패딩 제거
        ) {
            when (imageUrl) {
                is ImageType.Url -> {
                    AsyncImage(
                        model = imageUrl.url,
                        contentDescription = "$infoText 버튼",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                is ImageType.Resource -> {
                    Image(
                        painter = painterResource(id = imageUrl.resId),
                        contentDescription = "$infoText 버튼",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Button(
            onClick = { onKakaoClick(youTubeLink) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxHeight(kakaoSize)
                .aspectRatio(1f)
                .zIndex(1f),
            shape = RoundedCornerShape(kakaoRadius),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
        ) {
            Image(
                painter = painterResource(R.drawable.img_kakao),
                contentDescription = "카카오 버튼",
                contentScale = ContentScale.Fit
            )
        }
    }
}
