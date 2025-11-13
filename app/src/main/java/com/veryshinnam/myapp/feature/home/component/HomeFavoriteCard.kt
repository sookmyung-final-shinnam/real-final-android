package com.veryshinnam.myapp.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.home.model.FavoriteData

@Composable
fun HomeFavoriteCard(
    character: FavoriteData?,
    modifier: Modifier,
    isCenter: Boolean = false,
    onClick: (FavoriteData?) -> Unit
) {

    Card(
        colors = CardDefaults.cardColors(colorResource(R.color.main_orange)),
        modifier = modifier.then(
            if (isCenter && character != null) {
                Modifier.clickable { onClick(character) }
            } else Modifier
        )
    ) {
        if (character == null) {
            // 널카드
            HomeNullCard(modifier = Modifier.fillMaxSize())
        } else {
            // 캐릭터 이미지
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = if (isCenter) 1f else 0.5f   // 중앙은 100%, 양옆은 50% 투명
                    },
                contentScale = ContentScale.Crop
            )
        }
    }
}