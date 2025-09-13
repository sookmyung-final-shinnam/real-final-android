package com.veryshinnam.myapp.feature.creation.route

import android.content.Intent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veryshinnam.myapp.feature.creation.conversation.ui.ConversationViewModel
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
    const val ROOT      = "select"  // 그래프 루트
    const val THEME      = "select/theme"
    const val BACKGROUND = "select/background"
    const val GENDER     = "select/gender"
    const val AGE        = "select/age"
    const val NAME       = "select/name"
    const val FACE       = "select/face"
}

// 선택 플로우 네비게이션 그래프
fun NavGraphBuilder.selectNavGraph(navController: NavController) {
    navigation(
        route = SelectRoutes.ROOT,
        startDestination = SelectRoutes.THEME
    ) {
        // 테마
        composable(SelectRoutes.THEME) { backStackEntry ->
            // SELECT 그래프 범위 ViewModel 공유용 BackStackEntry 생성
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.ROOT) }
            val vm: SelectViewModel = hiltViewModel(parentEntry)
            SelectThemeScreen(
                onNextClick = { navController.navigate(SelectRoutes.BACKGROUND) },
                onBackClick = { navController.popBackStack() },
                vm = vm
            )
        }

        // 배경
        composable(SelectRoutes.BACKGROUND) { backStackEntry ->
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.ROOT) }
            val vm: SelectViewModel = hiltViewModel(parentEntry)
            SelectBackgroundScreen(
                onNextClick = { navController.navigate(SelectRoutes.GENDER) },
                onBackClick = { navController.popBackStack() },
                vm = vm
            )
        }

        // 성별
        composable(SelectRoutes.GENDER) { backStackEntry ->
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.ROOT) }
            val vm: SelectViewModel = hiltViewModel(parentEntry)
            SelectGenderScreen(
                onNextClick = { navController.navigate(SelectRoutes.AGE) },
                onBackClick = { navController.popBackStack() },
                vm = vm
            )
        }

        // 나이
        composable(SelectRoutes.AGE) { backStackEntry ->
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.ROOT) }
            val vm: SelectViewModel = hiltViewModel(parentEntry)
            SelectAgeScreen(
                onNextClick = { navController.navigate(SelectRoutes.NAME) },
                onBackClick = { navController.popBackStack() },
                vm = vm
            )
        }

        // 이름
        composable(SelectRoutes.NAME) { backStackEntry ->
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.ROOT) }
            val vm: SelectViewModel = hiltViewModel(parentEntry)

            SelectNameScreen(
                onNextClick = { navController.navigate(SelectRoutes.FACE) },
                onBackClick = { navController.popBackStack() },
                vm = vm
            )
        }

        // 외형 (마지막)
        composable(SelectRoutes.FACE) { backStackEntry ->
            val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(SelectRoutes.ROOT) }
            val selectVm: SelectViewModel = hiltViewModel(parentEntry)

            SelectFaceScreen(
                onNextClick = {
                    val uiState = selectVm.selectUiState.value
                    val req = StartConversationRequest(
                        themeNames    = uiState.themes,
                        backgroundName = uiState.background,
                        characterName  = uiState.name,
                        gender         = uiState.gender,
                        characterAge   = uiState.age,
                        hairColor      = uiState.hairColor,
                        eyeColor       = uiState.eyeColor,
                        hairStyle      = uiState.hairStyle
                    )

                    // SavedStateHandle로 전달
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("request", req)

                    // Conversation 시작 화면으로 이동
                    navController.navigate(ConversationRoutes.ROOT) {
                        launchSingleTop = true
                    }
                },
                onBackClick = { navController.popBackStack() },
                vm = selectVm
            )
        }
    }
}