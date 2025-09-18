package com.veryshinnam.myapp.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veryshinnam.myapp.feature.permit.ui.LoginScreen
import com.veryshinnam.myapp.feature.permit.ui.SplashScreen

fun NavGraphBuilder.permitNavGraph(navController: NavController) {
    navigation(
        startDestination = "splash",
        route = NavGraphs.PERMIT
    ) {
        // 스플래시 화면
        composable("splash") {
            SplashScreen(
                onLogin = {
                    navController.navigate("login") {
                        popUpTo(NavGraphs.PERMIT) { inclusive = true } // 스플래시 백스택 제거
                        launchSingleTop = true // 이미 login 있으면 재사용
                    }
                },
                onHome = {
                    navController.navigate(NavGraphs.MAIN) {
                        popUpTo(NavGraphs.PERMIT) { inclusive = true } // 스플래시 백스택 제거
                        launchSingleTop = true // 이미 home 있으면 재사용
                    }
                }
            )
        }

        // 로그인 화면
        composable("login") {
            LoginScreen(
                onSignup = { navController.navigate("signup") },
                onHome = {
                    navController.navigate(NavGraphs.MAIN) {
                        popUpTo(NavGraphs.PERMIT) { inclusive = true } // 스플래시 백스택 제거
                        launchSingleTop = true // 이미 home 있으면 재사용
                    }
                }
            )
        }

        // 회원가입 화면
        composable("signup") {
        }
    }
}