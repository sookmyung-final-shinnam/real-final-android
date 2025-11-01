package com.veryshinnam.myapp.core.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veryshinnam.myapp.feature.creation.ui.conversation.ConversationScreen
import com.veryshinnam.myapp.feature.creation.ui.conversation.ConversationViewModel
import com.veryshinnam.myapp.feature.creation.data.dto.StartRequest
import com.veryshinnam.myapp.feature.creation.data.dto.toStartRequest
import com.veryshinnam.myapp.feature.creation.ui.selection.SelectionScreen

// 캐릭터 및 동화 생성 플로우 네비게이션 그래프
fun NavGraphBuilder.creationNavGraph(navController: NavController) {
    navigation(
        route = NavGraphs.CREATION,
        startDestination = "selection"
    ) {
        // 선택 화면
        composable("selection") {
            SelectionScreen(
                onHome = { navController.popBackStack() },
                onLogoClick = {
                    navController.navigate("home") {
                        popUpTo(NavGraphs.MAIN) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onFinish = { data ->
                    val req = data.toStartRequest()

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("request", req)

                    navController.navigate("conversation") {
                        launchSingleTop = true
                    }
                }
            )
        }

        // llm 대화 화면
        composable("conversation") { backStackEntry ->
            val vm: ConversationViewModel = hiltViewModel()

            val req = remember(backStackEntry) {
                navController.getBackStackEntry("selection")
                    .savedStateHandle
                    .get<StartRequest>("request")
            }

            Log.d("conversation", "$req")
            LaunchedEffect(req) {
                if (req != null) {
                    vm.startConversation(req) // api 호출, 동화 세선 시작
                }
            }

            ConversationScreen(
                onBack = {
                    // 선택 화면 스택 정리 후 홈으로
                    navController.popBackStack("selection", inclusive = true)
                    navController.navigate("home") {
                        launchSingleTop = true
                    }
                },
//                onLogoClick = {
//                    navController.popBackStack("selection", inclusive = true)
//                    navController.navigate("home") {
//                        popUpTo(NavGraphs.MAIN) { inclusive = false }
//                        launchSingleTop = true
//                    }
//                },
                vm = vm
            )
        }
    }
}