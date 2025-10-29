package com.veryshinnam.myapp.feature.collection.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeText
import com.veryshinnam.myapp.common.component.StrokeTitle

@Composable
fun CollectionCharacterItem(
    cId: Long,       // 캐릭터 아이디
    cName: String,   // 캐릭터 이름
    cImage: String?, // 캐릭터 이미지
    isFavorite: Boolean, // 캐릭터 즐찾 여부
    onFavoriteClick: (cId: Long) -> Unit,   // 클릭 시 외부 처리
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    modifier: Modifier   // 부모가 넘겨준 크기
) {
    // 캐릭터 아이템 박스
    Box(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp,
                colorResource(id = R.color.blue_gray),
                RoundedCornerShape(16.dp))
    ) {
        // 1. 동화 완성인 경우
        if (!cImage.isNullOrEmpty()) {

            // 캐릭터 이미지
            AsyncImage(
                model = cImage,
                contentDescription = "캐릭터 이미지",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // 즐찾
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.44f)
                    .aspectRatio(1f)
                    .align(Alignment.TopStart)
                    .padding(4.dp)
            ) {
                // 보더
                Icon(
                    imageVector = Icons.Rounded.StarBorder,
                    contentDescription = null,
                    tint = colorResource(id = R.color.main_orange),
                    modifier = Modifier
                        .fillMaxSize()
                )

                // 내부
                IconButton(
                    onClick = { onFavoriteClick(cId) },
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "즐겨찾기 아이콘",
                        tint = if (isFavorite) colorResource(id = R.color.main_orange) else Color.White,
                        modifier = Modifier
                            .fillMaxSize(0.92f)
                    )
                }
            }

            // 캐릭터 이름
            StrokeTitle(
                titleText = cName,
                titleColor = Color.White,
                strokeColor = Color.Black,
                titleTextStyle = textStyle,
                strokeWidth = 4f,
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp) // 아래 패딩
            )
        } else {
            // 2. 동화 미완인 경우

            // 기본 마네킹 이미지
            Image(
                painter = painterResource(id = R.drawable.img_character),
                contentDescription = "기본 이미지",
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.Center),
                contentScale = ContentScale.Fit
            )

            // 오버레이 (검정 50% 투명)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            // 물음표 이미지
            Image(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp), // 아래 패딩
                painter = painterResource(id = R.drawable.img_question),
                contentDescription = "미완성 아이콘",
            )

            // 캐릭터 이름
            StrokeTitle(
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp), // 아래 패딩
                titleText = cName,
                titleColor = Color.White,
                strokeColor = Color.Black,
                strokeWidth = 4f,
                titleTextStyle = textStyle
            )
        }
    }
}
