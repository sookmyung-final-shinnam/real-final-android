package com.veryshinnam.myapp.common.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.sp

// 폰트 관련 설정

/**
 * 기기 크기별 폰트 스케일 반환
 * - 태블릿(600dp 이상): 1.5
 * - 폰(360dp 이상): 1.2
 */
@Composable
fun adaptiveFontScale(): Float {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp

    return when {
        screenWidthDp >= 600 -> 1.5f // 태블릿
        else -> 1.2f                 // 폰
    }
}

/**
 * 기기 크기별 폰트 스케일 적용된 폰트 스타일 재정의
 * - TODO: fontFamily (폰트 종류) 변경
 * - fontScale(= 폰트 사이즈) 변경
 */
@Composable
fun adaptiveTypography(): Typography {
    val fontScale = adaptiveFontScale()
    val typography = Typography()
//    val typography = Typography(defaultFontFamily = fontScale)

    return typography.copy(
        displayLarge = typography.displayLarge.copy(fontSize = 57.sp * fontScale),
        displayMedium = typography.displayMedium.copy(fontSize = 45.sp * fontScale),
        displaySmall = typography.displaySmall.copy(fontSize = 36.sp * fontScale),

        headlineLarge = typography.headlineLarge.copy(fontSize = 32.sp * fontScale),
        headlineMedium = typography.headlineMedium.copy(fontSize = 28.sp * fontScale),
        headlineSmall = typography.headlineSmall.copy(fontSize = 24.sp * fontScale),

        titleLarge = typography.titleLarge.copy(fontSize = 22.sp * fontScale),
        titleMedium = typography.titleMedium.copy(fontSize = 16.sp * fontScale),
        titleSmall = typography.titleSmall.copy(fontSize = 14.sp * fontScale),

        bodyLarge = typography.bodyLarge.copy(fontSize = 16.sp * fontScale),
        bodyMedium = typography.bodyMedium.copy(fontSize = 14.sp * fontScale),
        bodySmall = typography.bodySmall.copy(fontSize = 12.sp * fontScale),

        labelLarge = typography.labelLarge.copy(fontSize = 14.sp * fontScale),
        labelMedium = typography.labelMedium.copy(fontSize = 12.sp * fontScale),
        labelSmall = typography.labelSmall.copy(fontSize = 11.sp * fontScale),
    )
}