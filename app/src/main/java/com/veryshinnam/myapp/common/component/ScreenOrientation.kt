package com.veryshinnam.myapp.common.component

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

/**
 * 스크린 화면 방향 고정
 *
 * orientation에 따라 화면을
 * 세로 모드(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
 * 또는 가로 모드(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)로 고정
 */
@Composable
fun ScreenOrientation(
    orientation: Int
) {
    val context = LocalContext.current

    DisposableEffect(orientation) {
        val activity = context as? Activity ?: return@DisposableEffect onDispose {}
        val previous = activity.requestedOrientation

        // 진입 시 방향 고정
        activity.requestedOrientation = orientation

        onDispose {
            // 사라질 때 원래 방향 복구
            activity.requestedOrientation = previous
        }
    }
}