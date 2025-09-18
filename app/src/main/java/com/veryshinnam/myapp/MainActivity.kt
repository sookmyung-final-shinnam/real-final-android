package com.veryshinnam.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.veryshinnam.myapp.core.navigation.NavGraphs
import com.veryshinnam.myapp.core.navigation.creationNavGraph
import com.veryshinnam.myapp.core.navigation.mainNavGraph
import com.veryshinnam.myapp.core.navigation.permitNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()   // 시스템 스플래시

        super.onCreate(savedInstanceState)
        splash.setKeepOnScreenCondition { false } // 시스템 스플래시 즉시 사라지도록

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = NavGraphs.PERMIT
            ) {
                // 로그인 관련 그래프
                permitNavGraph(navController)

                // 메인 기능 관련 그래프
                mainNavGraph(navController)

                // 캐릭터 생성 그래프
                creationNavGraph(navController)
            }
        }
    }
}
