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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.veryshinnam.myapp.common.theme.MyTheme
import com.veryshinnam.myapp.core.navigation.graphs.NavGraphs
import com.veryshinnam.myapp.core.navigation.graphs.creationNavGraph
import com.veryshinnam.myapp.core.navigation.graphs.mainNavGraph
import com.veryshinnam.myapp.core.navigation.graphs.permitNavGraph
import com.veryshinnam.myapp.core.orientation.OrientationManager
import com.veryshinnam.myapp.core.session.ReviewToken
import com.veryshinnam.myapp.core.session.SessionManager
import com.veryshinnam.myapp.core.speech.tts.TtsManager
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.LocalDateTime
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

        setContent {
            MyTheme {
                val navController = rememberNavController()

                // 전역에서 401 감지
                val requireLogin by sessionManager.requireLogin.collectAsStateWithLifecycle()

                LaunchedEffect(requireLogin) {
                    val now = LocalDateTime.now()
                    val reviewExpireAt = LocalDateTime.parse(ReviewToken.REVIEW_EXPIRE_AT)

                    if (requireLogin && now.isAfter(reviewExpireAt)) {
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

    override fun onStop() {
        super.onStop()
        ttsManager.stop()
    }
}
