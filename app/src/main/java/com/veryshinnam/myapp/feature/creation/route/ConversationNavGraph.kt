package com.veryshinnam.myapp.feature.creation.route

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.veryshinnam.myapp.feature.creation.conversation.ui.ConversationScreen
import com.veryshinnam.myapp.feature.creation.conversation.ui.ConversationViewModel
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest

// conversationNavGraph에 필요한 라우트 상수
object ConversationRoutes {
    const val ROOT = "conversation"
}

// 대화 플로우 네비게이션 그래프
fun NavGraphBuilder.conversationNavGraph(
    navController: NavController
) {
    composable(ConversationRoutes.ROOT) { backStackEntry ->
        val vm: ConversationViewModel = hiltViewModel()

        val req = remember(backStackEntry) {
            navController.getBackStackEntry(SelectRoutes.FACE)
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
            onBack = { navController.popBackStack() },
            vm = vm
        )
    }
}