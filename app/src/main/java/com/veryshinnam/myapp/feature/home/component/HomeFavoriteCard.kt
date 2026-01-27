package com.veryshinnam.myapp.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.home.model.FavoriteData

@Composable
fun HomeFavoriteCard(
    character: FavoriteData?,
    index: Int,
    modifier: Modifier,
    isCenter: Boolean = false,
    onClick: (FavoriteData?) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            Color.Transparent
        ),
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(R.color.main_orange),
                        colorResource(R.color.dark_orange)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .then(
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
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .semantics {
                        contentDescription =
                            "즐겨찾기한 캐릭터 카드, ${index}번째 ${character.name}"
                        role = Role.Button
                    }
                    .graphicsLayer {
                        alpha = if (isCenter) 1f else 0.5f   // 중앙은 100%, 양옆은 50% 투명
                    },
                contentScale = ContentScale.Crop
            )
        }
    }
}