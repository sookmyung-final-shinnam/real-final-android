package com.veryshinnam.myapp.feature.character.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.character.model.CharacterData

@Composable
fun CharacterImageCard(
    character: CharacterData, // 캐릭터 정보
    onFavoriteClick: (Long) -> Unit, // 즐찾 대상 캐릭터 id
    modifier: Modifier = Modifier    // 부모가 넘겨준 크기
) {
    // 즐찾 아이콘

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            // 캐릭터 이미지에 배경 없으면 오렌지
            containerColor = colorResource(R.color.main_orange)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 캐릭터 이미지
            AsyncImage(
                model = character.image,
                contentDescription = "캐릭터 이미지",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

//            // 즐찾 이미지
//            Image(
//                painter = painterResource(id = iconRes),
//                contentDescription = "즐겨찾기 아이콘",
//                modifier = Modifier
//                    .fillMaxWidth(0.3f)
//                    .aspectRatio(1f)
//                    .padding(16.dp)
//                    .clickable { onFavoriteClick(character.id) },
//                contentScale = ContentScale.Fit
//            )
        }
    }
}