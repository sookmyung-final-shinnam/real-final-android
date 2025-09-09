package com.veryshinnam.myapp.feature.creation.route

import android.content.Intent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veryshinnam.myapp.ConversationActivity
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest
import com.veryshinnam.myapp.feature.creation.ui.select.SelectAgeScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectBackgroundScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectFaceScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectGenderScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectNameScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectThemeScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectViewModel
import kotlin.jvm.java


// SelectNavigation에 필요한 라우트 상수
object SelectRoutes {
    const val ROOT      = "select_root"  // 그래프 루트
    const val Theme      = "select_theme"
    const val Background = "select_background"
    const val Gender     = "select_gender"
    const val Age        = "select_age"
    const val Name       = "select_name"
    const val Face       = "select_face"
}

// 선택 플로우 네비게이션 그래프
fun NavGraphBuilder.selectNavGraph(navController: NavController) {
    navigation(
        route = SelectRoutes.ROOT,
        startDestination = SelectRoutes.Theme
    ) {
        // 테마
        composable(SelectRoutes.Theme) { backStackEntry ->

            SelectThemeScreen(
                onNextClick = { navController.navigate(SelectRoutes.Background) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // 배경
        composable(SelectRoutes.Background) { backStackEntry ->

            SelectBackgroundScreen(
                onNextClick = { navController.navigate(SelectRoutes.Gender) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // 성별
        composable(SelectRoutes.Gender) { backStackEntry ->
            SelectGenderScreen(
                onNextClick = { navController.navigate(SelectRoutes.Age) },
                onBackClick = { navController.popBackStack() },
            )
        }

        // 나이
        composable(SelectRoutes.Age) { backStackEntry ->
            SelectAgeScreen(
                onNextClick = { navController.navigate(SelectRoutes.Name) },
                onBackClick = { navController.popBackStack() },
            )
        }

        // 이름
        composable(SelectRoutes.Name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(SelectRoutes.ROOT)
            }
            val vm: SelectViewModel = hiltViewModel(parentEntry)

            SelectNameScreen(
                onNext = { navController.navigate(SelectRoutes.Face) },
                onBack = { navController.popBackStack() },
                vm = vm
            )
        }

        // 외형 (마지막)
        composable(SelectRoutes.Face) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(SelectRoutes.ROOT)
            }
            val vm: SelectViewModel = hiltViewModel(parentEntry)

            val context = LocalContext.current

            SelectFaceScreen(
                onNext = {
                    val uiState = vm.selectUiState.value
                    val req = StartConversationRequest(
                        themeNames      = uiState.selectedThemes,
                        backgroundName = uiState.selectedBackground,
                        characterName        = uiState.name,
                        gender      = uiState.gender!!, // null이 아님을 보장
                        characterAge         = uiState.age,
                        hairColor   = uiState.hairColor,
                        eyeColor    = uiState.eyeColor,
                        hairStyle   = uiState.hairStyle
                    )

                    // 생성 플로우 스택 모두 삭제 > 홈으로 이동
                    navController.navigate("home") {
                        popUpTo(SelectRoutes.ROOT) { inclusive = true }
                        launchSingleTop = true
                    }

                    // ConversationActivity로 전달
                    context.startActivity(
                        Intent(context, ConversationActivity::class.java).apply {
                            putExtra("request", req)
                        }
                    )
                },
                onBack = { navController.popBackStack() },
                vm = vm
            )
        }
    }
}