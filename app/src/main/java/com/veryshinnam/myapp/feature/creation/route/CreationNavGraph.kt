package com.veryshinnam.myapp.feature.creation.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

// creationNavGraph에 필요한 라우트 상수
object CreationRoutes {
    const val ROOT = "creation"
}

// 캐릭터 생성 플로우 네비게이션 그래프
fun NavGraphBuilder.creationNavGraph(navController: NavController) {
    navigation(
        route = CreationRoutes.ROOT,
        startDestination = SelectRoutes.ROOT
    ) {
        // select 단계
        selectNavGraph(navController)

        // conversation 단계
        conversationNavGraph(navController)
    }
}