package com.veryshinnam.myapp;

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest
import com.veryshinnam.myapp.feature.creation.conversation.ConversationScreen
import com.veryshinnam.myapp.feature.creation.conversation.ConversationStartScreen
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
class ConversationActivity: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 캐릭터 요청 가져옴
        val req = intent.getParcelableExtra<StartConversationRequest>("request")

        setContent {
            val convNavController = rememberNavController()

            NavHost(
                navController = convNavController,
                startDestination = "conversationStart"
            ) {
                composable("conversationStart") {
                    ConversationStartScreen(
                        onBack = { finish() }, // ← 뒤로 버튼 Activity 종료
                        req = req,
                        onNext = { sessionId, step ->
                            convNavController.navigate("conversation/$sessionId/$step") {
                                launchSingleTop = true
                                popUpTo("conversationStart") { inclusive = true }
                            }
                        }
                    )
                }

                composable("conversation/{sessionId}/{step}",
                    arguments = listOf(
                        navArgument("sessionId") { type = NavType.LongType },
                        navArgument("step") { type = NavType.StringType }
                    )
                ) { be ->
                    val sid = be.arguments?.getLong("sessionId")!!
                    val step = be.arguments?.getString("step") ?: "START"
                    ConversationScreen(
                        onBack = { convNavController.popBackStack() },
                        sessionId = sid,
                        step = step
                    )
                }
            }
        }
    }
}
