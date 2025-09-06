package com.veryshinnam.myapp.feature.character.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CharafcterInfoCard(
    name: String,
    gender: String,
    isFavorite: Boolean,
    createdAt: String,
    onFavoriteClick: () -> Unit = {},
    modifier: Modifier
) {
}