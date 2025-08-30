package com.veryshinnam.myapp.feature.creation.route

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veryshinnam.myapp.feature.creation.ui.select.SelectAgeScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectBackgroundScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectFaceScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectGenderScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectNameScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectThemeScreen
import com.veryshinnam.myapp.feature.creation.ui.select.SelectViewModel


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
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(SelectRoutes.ROOT)
            }
            val vm: SelectViewModel = hiltViewModel(parentEntry)

            SelectThemeScreen(
                onNext = { navController.navigate(SelectRoutes.Background) },
                onBack = { navController.popBackStack() },
                vm = vm
            )
        }

        // 배경
        composable(SelectRoutes.Background) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(SelectRoutes.ROOT)
            }
            val vm: SelectViewModel = hiltViewModel(parentEntry)

            SelectBackgroundScreen(
                onNext = { navController.navigate(SelectRoutes.Gender) },
                onBack = { navController.popBackStack() },
                vm = vm
            )
        }

        // 성별
        composable(SelectRoutes.Gender) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(SelectRoutes.ROOT)
            }
            val vm: SelectViewModel = hiltViewModel(parentEntry)

            SelectGenderScreen(
                onNext = { navController.navigate(SelectRoutes.Age) },
                onBack = { navController.popBackStack() },
                vm = vm
            )
        }

        // 나이
        composable(SelectRoutes.Age) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(SelectRoutes.ROOT)
            }
            val vm: SelectViewModel = hiltViewModel(parentEntry)

            SelectAgeScreen(
                onNext = { navController.navigate(SelectRoutes.Name) },
                onBack = { navController.popBackStack() },
                vm = vm
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
                    // TODO: request 맞추기
                    val uiState = vm.selectUiState.value
//                    val req = GenerateStoryRequest(
//                        themes      = uiState.themes.joinToString(","),
//                        backgrounds = uiState.background,
//                        name        = uiState.name,
//                        gender      = uiState.gender,
//                        age         = uiState.age,
//                        hairColor   = uiState.hairColor,
//                        eyeColor    = uiState.eyeColor,
//                        hairStyle   = uiState.hairStyle
//                    )
//                    // AI 대화 액티비티 시작 (원하면 DialogActivity로 변경)
//                    context.startActivity(
//                        Intent(context, ChatActivity::class.java).putExtra("request", req)
//                    )
                },
                onBack = { navController.popBackStack() },
                vm = vm
            )
        }
    }
}