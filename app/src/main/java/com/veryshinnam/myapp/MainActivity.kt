package com.veryshinnam.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.veryshinnam.myapp.feature.character.ui.CharacterScreen
import com.veryshinnam.myapp.feature.creation.route.SelectRoutes
import com.veryshinnam.myapp.feature.creation.route.selectNavGraph
import com.veryshinnam.myapp.feature.home.ui.HomeScreen
import com.veryshinnam.myapp.feature.settings.ui.SettingsScreen
import com.veryshinnam.myapp.feature.storage.ui.StorageScreen
import com.veryshinnam.myapp.feature.storage.ui.component.StorageInfo
import com.veryshinnam.myapp.feature.storage.ui.component.StorageItemCard
import com.veryshinnam.myapp.feature.story.ui.StoryPrologueScreen
import com.veryshinnam.myapp.feature.story.ui.components.CharacterFrame
import com.veryshinnam.myapp.feature.story.ui.components.StoryInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

//            StorageInfo(Modifier.fillMaxWidth().fillMaxHeight(0.25f))
            StorageItemCard(
                1, "미니",
                "https://jangshinnam-s3.s3.ap-northeast-2.amazonaws.com/stories/2/page_2.png",
                true,
                onFavoriteClick = { id ->
                    // 클릭 시 처리 로직
                    println("즐찾 클릭: $id")
                },
                Modifier.fillMaxWidth(0.3f).fillMaxHeight(0.3f)
            )
//            val mainNavController = rememberNavController()
//
//            NavHost(
//                navController = mainNavController,
//                startDestination = "home"
//            ) {
//                // 홈 화면
//                composable("home") {
//                    HomeScreen(
//                        onSettingsClick = {
//                            // 환경 설정
//                            mainNavController.navigate("settings")
//                        },
//                        onCreationClick = {
//                            // 캐릭터 생성
//                            mainNavController.navigate(SelectRoutes.ROOT)
//                        },
//                        onStorageClick = {
//                            // 보관함
//                            mainNavController.navigate("storage")
//                        },
//                        onCharacterClick = { charId ->
//                            mainNavController.navigate("character/$charId")
//                        }
//                    )
//                }
//
//                // 설정 화면
//                composable("settings") {
//                    SettingsScreen(
//                        onBack = { mainNavController.popBackStack() },
//                        onClickLogout = { /* 로그아웃 로직 */ },
//                        onClickDelete = { /* 회원탈퇴 로직 */ }
//                    )
//                }
//
//                // 생성 플로우
//                selectNavGraph(mainNavController)
//
//                // 보관함
//                composable("storage") {
//                    StorageScreen(
//                        onBack = { mainNavController.popBackStack() },
//                        onItemClick = { id -> mainNavController.navigate("character/$id") }
//                    )
//                }
//
//                // 캐릭터 상세 화면
//                composable("character/{id}") { backStackEntry ->
//                    val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: return@composable
//                    CharacterScreen(
//                        id = id,
//                        onBack = { mainNavController.popBackStack() }
//                    )
//                }
//            }
        }
    }
}
