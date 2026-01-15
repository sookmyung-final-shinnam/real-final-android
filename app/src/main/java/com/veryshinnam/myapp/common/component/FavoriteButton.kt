package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.stateDescription
import com.veryshinnam.myapp.R

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    characterId: Long,      // 캐릭터 id 받기
    isFavorite: Boolean,    // 즐겨 찾기 여부
    onFavoriteClick: (cId: Long) -> Unit,   // 클릭 시 외부 처리
) {
    Box(
        modifier = modifier
            .clearAndSetSemantics {
                role = Role.Button
                contentDescription = "즐겨찾기"
                stateDescription = if (isFavorite) "즐겨찾기 켜짐" else "즐겨찾기 꺼짐"
            }
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
            onClick = { onFavoriteClick(characterId) },
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = if (isFavorite) colorResource(id = R.color.main_orange) else Color.White,
                modifier = Modifier
                    .fillMaxSize(0.92f)
            )
        }
    }
}