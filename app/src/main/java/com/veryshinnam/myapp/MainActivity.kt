package com.veryshinnam.myapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.veryshinnam.myapp.common.theme.MyTheme
import com.veryshinnam.myapp.core.navigation.graphs.NavGraphs
import com.veryshinnam.myapp.core.navigation.graphs.creationNavGraph
import com.veryshinnam.myapp.core.navigation.graphs.mainNavGraph
import com.veryshinnam.myapp.core.navigation.graphs.permitNavGraph
import com.veryshinnam.myapp.core.orientation.OrientationManager
import com.veryshinnam.myapp.core.session.SessionManager
import com.veryshinnam.myapp.core.speech.tts.TtsManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var sessionManager: SessionManager
    @Inject lateinit var ttsManager: TtsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()        // 시스템 자체 스플래시

        super.onCreate(savedInstanceState)
        splash.setKeepOnScreenCondition { false } // 시스템 자체 스플래시 즉시 사라지도록

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
        )

        // 스크린 방향 설정
        OrientationManager.setOrientation = { orientation ->
            requestedOrientation = orientation
        }

        // 리뷰 기간 종료 감지 (백그라운드 체크)
        lifecycleScope.launch {
            while (isActive) {
                delay(60_000L) // 1분마다 체크

                val isReviewPeriod = sessionManager.isReviewPeriod()
                val isUsingReview = sessionManager.isUsingReviewToken()

                // 리뷰 기간 끝났는데 리뷰 토큰 쓰는 중 -> 로그아웃
                if (!isReviewPeriod && isUsingReview) {
                    sessionManager.clearToken()
                    sessionManager.clearReviewState()
                }
            }
        }

        setContent {
            MyTheme {
                val navController = rememberNavController()

                // 전역에서 401 감지
                val requireLogin by sessionManager.requireLogin.collectAsStateWithLifecycle()

                LaunchedEffect(requireLogin) {
                    if (requireLogin) {
                        navController.navigate(NavGraphs.PERMIT) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }

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

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()

        ttsManager.stop()
    }
}
