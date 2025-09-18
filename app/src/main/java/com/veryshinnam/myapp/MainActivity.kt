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
import com.veryshinnam.myapp.feature.attendance.ui.AttendanceScreen
import com.veryshinnam.myapp.feature.home.ui.HomeScreen
import com.veryshinnam.myapp.feature.settings.ui.SettingsScreen
import com.veryshinnam.myapp.feature.collection.ui.CollectionScreen
import com.veryshinnam.myapp.feature.creation.route.CreationRoutes
import com.veryshinnam.myapp.feature.creation.route.creationNavGraph
import com.veryshinnam.myapp.feature.dashboard.ui.DashboardScreen
import com.veryshinnam.myapp.feature.permit.ui.LoginScreen
import com.veryshinnam.myapp.feature.permit.ui.SplashScreen
import com.veryshinnam.myapp.feature.story.model.StoryType
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
                startDestination = "login"
            ) {
                // 스플래시
                composable("splash") {
                    SplashScreen(
                        onLogin = {
                            mainNavController.navigate("login") {
                                popUpTo("splash") { inclusive = true } // 스플래시 백스택 제거
                                launchSingleTop = true // 이미 Home이 있으면 재사용
                            }
                        },
                        onHome = {
                            mainNavController.navigate("home") {
                                popUpTo("splash") { inclusive = true } // 스플래시 백스택 제거
                                launchSingleTop = true // 이미 Home이 있으면 재사용
                            }
                        }
                    )
                }

                composable("login") {
                    LoginScreen(
                        onSignup = {
                            mainNavController.navigate("signup")
                        },
                        onHome = {
                            mainNavController.navigate("home") {
                                popUpTo("login") { inclusive = true } // 스플래시 백스택 제거
                                launchSingleTop = true // 이미 Home이 있으면 재사용
                            }
                        }
                    )
                }

                // 홈 화면
                composable("home") {
                    HomeScreen(
                        onSettingsClick = {
                            // 환경 설정
                            mainNavController.navigate("settings")
                        },
                        onAttendanceClick = {
                            // 환경 설정
                            mainNavController.navigate("attendance")
                        },
                        onDashboardClick = {
                            // 대시보드
                            mainNavController.navigate("dashboard")
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

                // 출석체크
                composable("attendance") {
                    AttendanceScreen(
                        onBack = { mainNavController.popBackStack() },
                    )
                }

                // 대시보드
                composable("dashboard") {
                    DashboardScreen(
                        onBack = { mainNavController.popBackStack() },
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
                        onStoryClick = { storyId, type ->
                            mainNavController.navigate("story/$storyId/${type.name}")
                        }
                    )
                }

                // 동화 상세 보기
                composable( route = "story/{id}/{type}",
                    arguments = listOf(
                        navArgument("id") { type = NavType.LongType },    // 동화 아이디
                        navArgument("type") { type = NavType.StringType } // 동화 타입
                    )
                ) { backStackEntry ->
                    val storyId = backStackEntry.arguments?.getLong("id") ?: return@composable
                    val storyType = backStackEntry.arguments?.getString("type")?.let { StoryType.valueOf(it) } ?: StoryType.IMAGE

                    StoryScreen(
                        storyId = storyId,
                        storyType = storyType,
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
