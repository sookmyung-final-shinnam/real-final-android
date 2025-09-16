package com.veryshinnam.myapp.feature.creation.route

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veryshinnam.myapp.feature.creation.conversation.ui.ConversationScreen
import com.veryshinnam.myapp.feature.creation.conversation.ui.ConversationViewModel
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest
import com.veryshinnam.myapp.feature.creation.select.ui.SelectViewModel
import com.veryshinnam.myapp.feature.creation.select.ui.SelectionScreen

// creationNavGraph에 필요한 라우트 상수
object CreationRoutes {
    const val ROOT = "creation"
    const val SELECTION = "selection"
    const val CONVERSATION = "conversation"
}

// 캐릭터 생성 플로우 네비게이션 그래프
fun NavGraphBuilder.creationNavGraph(navController: NavController) {
    navigation(
        route = CreationRoutes.ROOT,
        startDestination = CreationRoutes.SELECTION
    ) {
        // 선택 단계
        composable(CreationRoutes.SELECTION) { backStackEntry ->
            val vm: SelectViewModel = hiltViewModel(backStackEntry)

            SelectionScreen(
                onHome = { navController.popBackStack() },
                onFinish = { data ->
                    val req = StartConversationRequest(
                        themeNames = data.themes,
                        backgroundName = data.background,
                        characterName = data.name,
                        gender = data.gender,
                        characterAge = data.age,
                        hairColor = data.hairColor,
                        eyeColor = data.eyeColor,
                        hairStyle = data.hairStyle
                    )

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("request", req)

                    navController.navigate(CreationRoutes.CONVERSATION) {
                        launchSingleTop = true
                    }
                },
                vm = vm
            )
        }

        // 대화 단계
        composable(CreationRoutes.CONVERSATION) { backStackEntry ->
            val vm: ConversationViewModel = hiltViewModel()

            val req = remember(backStackEntry) {
                navController.getBackStackEntry(CreationRoutes.SELECTION)
                    .savedStateHandle
                    .get<StartConversationRequest>("request")
            }

            Log.d("Conversation", "req: $req")

            LaunchedEffect(req) {
                if (req != null) {
                    vm.startConversation(req)
                }
            }

            ConversationScreen(
                onBack = {
                    // 선택 스택 정리 후 홈으로
                    navController.popBackStack(CreationRoutes.SELECTION, inclusive = true)
                    navController.navigate("home") {
                        launchSingleTop = true
                    }
                },
                vm = vm
            )
        }
    }
}