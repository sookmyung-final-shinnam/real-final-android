package com.veryshinnam.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.veryshinnam.myapp.feature.character.ui.CharacterScreen
import com.veryshinnam.myapp.feature.creation.route.SelectRoutes
import com.veryshinnam.myapp.feature.creation.route.selectNavGraph
import com.veryshinnam.myapp.feature.home.ui.HomeScreen
import com.veryshinnam.myapp.feature.settings.ui.SettingsScreen
import com.veryshinnam.myapp.feature.collection.ui.CollectionScreen
import com.veryshinnam.myapp.feature.creation.route.CreationRoutes
import com.veryshinnam.myapp.feature.creation.route.creationNavGraph
import com.veryshinnam.myapp.feature.story.ui.StoryScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val mainNavController = rememberNavController()

            NavHost(
                navController = mainNavController,
                startDestination = "home"
            ) {
                // 홈 화면
                composable("home") {
                    HomeScreen(
                        onSettingsClick = {
                            // 환경 설정
                            mainNavController.navigate("settings")
                        },
                        onCreationClick = {
                            // 캐릭터 생성
                            mainNavController.navigate(CreationRoutes.ROOT)
                        },
                        onCollectionClick = {
                            // 보관함
                            mainNavController.navigate("collection")
                        },
                        onCharacterClick = { charId ->
                            // 캐릭터 상세 보기
                            mainNavController.navigate("character/$charId")
                        }
                    )
                }

                // 설정 화면
                composable("settings") {
                    SettingsScreen(
                        onBack = { mainNavController.popBackStack() },
                        onClickLogout = {    },
                        onClickDelete = {    }
                    )
                }

                // 보관함
                composable("collection") {
                    CollectionScreen(
                        onBack = { mainNavController.popBackStack() },
                        onItemClick = { id -> mainNavController.navigate("character/$id") }
                    )
                }

                // 캐릭터 상세 보기
                composable("character/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.LongType })
                ) { backStackEntry ->
                    val characterId = backStackEntry.arguments?.getLong("id") ?: return@composable
                    CharacterScreen(
                        id = characterId,
                        onBack = { mainNavController.popBackStack() },
                        onStoryClick = { storyId ->
                            mainNavController.navigate("story/$storyId")
                        },
                        onVideoClick = {}
                    )
                }

                // 동화 상세 보기
                composable("story/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.LongType })
                ) { backStackEntry ->
                    val storyId = backStackEntry.arguments?.getLong("id") ?: return@composable
                    StoryScreen(
                        storyId = storyId,
                        onBack = { mainNavController.popBackStack() },
                        onHome = {
                            // 스택 다 비워서 홈으로 이동
                            mainNavController.navigate("home") {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                // 캐릭터 생성 플로우
                creationNavGraph(mainNavController)
            }
        }
    }
}
