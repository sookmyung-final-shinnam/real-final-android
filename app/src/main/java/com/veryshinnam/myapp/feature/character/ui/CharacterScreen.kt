package com.veryshinnam.myapp.feature.character.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CharacterScreen(
    id: Long,
    onBack: () -> Unit,
) {
    BackHandler { onBack() }

    Text(modifier = Modifier.fillMaxSize(1f), text = "캐릭터아이디: ${id}")

    // 캐릭터 이미지

    // 책 아이디
    Text("text = \"책: ${id}\"")
    // 책 이미지
}