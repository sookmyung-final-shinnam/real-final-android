package com.veryshinnam.myapp.feature.creation.select.route

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
import com.veryshinnam.myapp.feature.creation.select.ui.SelectAgeScreen
import com.veryshinnam.myapp.feature.creation.select.ui.SelectBackgroundScreen
import com.veryshinnam.myapp.feature.creation.select.ui.SelectFaceScreen
import com.veryshinnam.myapp.feature.creation.select.ui.SelectGenderScreen
import com.veryshinnam.myapp.feature.creation.select.ui.SelectNameScreen
import com.veryshinnam.myapp.feature.creation.select.ui.SelectThemeScreen
import com.veryshinnam.myapp.feature.creation.select.ui.SelectViewModel
import kotlin.jvm.java


// SelectNavigation에 필요한 라우트 상수
object SelectRoutes {
    const val SELECT      = "select"  // 그래프 루트
    const val THEME      = "select_theme"
    const val BACKGROUND = "select_background"
    const val GENDER     = "select_gender"
    const val AGE        = "select_age"
    const val NAME       = "select_name"
    const val FACE       = "select_face"
}

// 선택 플로우 네비게이션 그래프
fun NavGraphBuilder.selectNavGraph(navController: NavController) {
    navigation(
        route = SelectRoutes.SELECT,
        startDestination = SelectRoutes.THEME
    ) {
        // 테마
        composable(SelectRoutes.THEME) { backStackEntry ->
            // SELECT 그래프 범위 ViewModel 공유용 BackStackEntry 생성
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.SELECT) }
            val vm: SelectViewModel = hiltViewModel(parentEntry)
            SelectThemeScreen(
                onNextClick = { navController.navigate(SelectRoutes.BACKGROUND) },
                onBackClick = { navController.popBackStack() },
                vm = vm
            )
        }

        // 배경
        composable(SelectRoutes.BACKGROUND) { backStackEntry ->
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.SELECT) }
            val vm: SelectViewModel = hiltViewModel(parentEntry)
            SelectBackgroundScreen(
                onNextClick = { navController.navigate(SelectRoutes.GENDER) },
                onBackClick = { navController.popBackStack() },
                vm = vm
            )
        }

        // 성별
        composable(SelectRoutes.GENDER) { backStackEntry ->
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.SELECT) }
            val vm: SelectViewModel = hiltViewModel(parentEntry)
            SelectGenderScreen(
                onNextClick = { navController.navigate(SelectRoutes.AGE) },
                onBackClick = { navController.popBackStack() },
                vm = vm
            )
        }

        // 나이
        composable(SelectRoutes.AGE) { backStackEntry ->
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.SELECT) }
            val vm: SelectViewModel = hiltViewModel(parentEntry)
            SelectAgeScreen(
                onNextClick = { navController.navigate(SelectRoutes.NAME) },
                onBackClick = { navController.popBackStack() },
                vm = vm
            )
        }

        // 이름
        composable(SelectRoutes.NAME) { backStackEntry ->
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.SELECT) }
            val vm: SelectViewModel = hiltViewModel(parentEntry)

            SelectNameScreen(
                onNextClick = { navController.navigate(SelectRoutes.FACE) },
                onBackClick = { navController.popBackStack() },
                vm = vm
            )
        }

        // 외형 (마지막)
        composable(SelectRoutes.FACE) { backStackEntry ->
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.SELECT) }
            val vm: SelectViewModel = hiltViewModel(parentEntry)

            val context = LocalContext.current

            SelectFaceScreen(
                onNextClick = {
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
                        popUpTo(SelectRoutes.SELECT) { inclusive = true }
                        launchSingleTop = true
                    }

                    // ConversationActivity로 전달
                    context.startActivity(
                        Intent(context, ConversationActivity::class.java).apply {
                            putExtra("request", req)
                        }
                    )
                },
                onBackClick = { navController.popBackStack() },
                vm = vm
            )
        }
    }
}