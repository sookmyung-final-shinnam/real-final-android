package com.veryshinnam.myapp.core.navigation.utils

import androidx.navigation.NavController
import com.veryshinnam.myapp.core.navigation.graphs.NavGraphs
import com.veryshinnam.myapp.core.navigation.routes.MainRoutes

// route로 이동
fun NavController.navigateTo(route: String) {
    navigate(route) {
        launchSingleTop = true // 백스택에 있으면 재사용
    }
}

// 홈으로 이동
fun NavController.navigateToHome() {
    navigate(MainRoutes.HOME) {
        popUpTo(NavGraphs.MAIN) {
            inclusive = false   // 그래프 안 홈 위 백스택 비우기
        }
        launchSingleTop = true  // 백스택에 있으면 재사용
    }
}

// 홈으로 강제 리셋
fun NavController.resetToHome() {
    navigate(MainRoutes.HOME) {
        popUpTo(0) // 백스택 모두 비우기
        launchSingleTop = true // 있으면 재사용
    }
}