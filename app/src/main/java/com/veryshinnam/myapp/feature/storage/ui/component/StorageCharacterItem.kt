package com.veryshinnam.myapp.feature.storage.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.StrokeText

@Composable
fun StorageCharacterItem(
    cId: Long,       // 캐릭터 아이디
    cName: String,   // 캐릭터 이름
    cImage: String?, // 캐릭터 이미지
    isFavorite: Boolean, // 캐릭터 즐찾 여부
    onFavoriteClick: (cId: Long) -> Unit,   // 클릭 시 외부 처리
    modifier: Modifier   // 부모가 넘겨준 크기
) {
    // 즐찾 아이콘
    val iconRes = if (isFavorite) R.drawable.img_star_on else R.drawable.img_star_off

    // 캐릭터 아이템 박스
    Surface(
        // 테두리
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, colorResource(id = R.color.blue_gray)),
        color = Color.White,
        tonalElevation = 2.dp,
        modifier = modifier
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            // 캐릭터 이미지

            // 1. 동화 완성인 경우
            if (!cImage.isNullOrEmpty()) {

                // 캐릭터 이미지
                AsyncImage(
                    model = cImage,
                    contentDescription = "캐릭터 이미지",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                // 즐찾 이미지
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "즐겨찾기 아이콘",
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .clickable { onFavoriteClick(cId)  }
                        .aspectRatio(1f)
                        .padding(16.dp) // 모든 방량 패딩
                        .align(Alignment.TopStart),
                    contentScale = ContentScale.Fit
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

                // 물음표 아이콘
                Image(
                    painter = painterResource(id = R.drawable.img_question),
                    contentDescription = "미완성 아이콘",
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    contentScale = ContentScale.Fit
                )
            }

            // 캐릭터 이름
            StrokeText(
                text = cName,
                tColor = Color.White,
                oColor = Color.Black,
                oWidth = 2f,
                fStyle = MaterialTheme.typography.headlineMedium,
                fWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp) // 아래 패딩
            )
        }
    }
}