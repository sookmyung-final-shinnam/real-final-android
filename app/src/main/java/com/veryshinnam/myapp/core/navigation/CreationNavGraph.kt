package com.veryshinnam.myapp.core.navigation

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

// 캐릭터 생성 플로우 네비게이션 그래프
fun NavGraphBuilder.creationNavGraph(navController: NavController) {
    navigation(
        route = NavGraphs.CREATION,
        startDestination = "selection"
    ) {
        // 선택 화면
        composable("selection") { backStackEntry ->
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

                    navController.navigate("conversation") {
                        launchSingleTop = true
                    }
                },
                vm = vm
            )
        }

        // llm 대화 화면
        composable("conversation") { backStackEntry ->
            val vm: ConversationViewModel = hiltViewModel()

            val req = remember(backStackEntry) {
                navController.getBackStackEntry("selection")
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
                    // 선택 화면 스택 정리 후 홈으로
                    navController.popBackStack("selection", inclusive = true)
                    navController.navigate("home") {
                        launchSingleTop = true
                    }
                },
                vm = vm
            )
        }
    }
}