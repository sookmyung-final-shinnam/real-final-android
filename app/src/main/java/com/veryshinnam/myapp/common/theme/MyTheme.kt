package com.veryshinnam.myapp.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * 앱 전체 테마
 * - adaptiveTypography(): 기기 크기별 폰트 스케일 적용된 폰트 스타일
 */
@Composable
fun MyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = adaptiveTypography(),
        content = content
    )
}