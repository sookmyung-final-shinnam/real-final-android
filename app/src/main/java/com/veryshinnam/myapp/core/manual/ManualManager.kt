package com.veryshinnam.myapp.core.manual

import androidx.navigation.NavController
import com.veryshinnam.myapp.common.model.ManualData
import com.veryshinnam.myapp.common.model.ManualTarget
import com.veryshinnam.myapp.core.navigation.MainRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ManualManager @Inject constructor() {

    // 매뉴얼 진행 결정 여부
    private val _isManual = MutableStateFlow(false)
    val isManual = _isManual.asStateFlow()

    // 매뉴얼 진행 단계
    private val _manualStep = MutableStateFlow(0)
    val manualStep = _manualStep.asStateFlow()

    val manuals = listOf(
        ManualData("여긴 메시지 박스예요!", ManualTarget.NONE, MainRoutes.HOME),
        ManualData("여긴 캐러셀이예요!", ManualTarget.NONE, MainRoutes.HOME),
        ManualData("출석!", ManualTarget.NONE, MainRoutes.HOME),
        ManualData("캐릭터 생성!", ManualTarget.NONE, MainRoutes.HOME),

        ManualData("나이 선택!", ManualTarget.NONE, "selection"),
        ManualData("이름 입력!", ManualTarget.NONE, "selection"),
        ManualData("다음 단계!", ManualTarget.NONE, "selection"),

        ManualData("대화 시작!", ManualTarget.NONE, "conversation"),
        ManualData("마이크 버튼!", ManualTarget.NONE, "conversation"),

        ManualData("동화 내용!", ManualTarget.NONE, "story/1/IMAGE")
    )

    fun startManual() {
        _isManual.value = true
        _manualStep.value = 0
    }

    fun nextManual(navController: NavController) {
        val next = _manualStep.value + 1

        if (next >= manuals.size) {
            finishManual()
            return
        }

        _manualStep.value = next

        val screen = manuals[next].screen

        navController.navigate(screen) {
            launchSingleTop = true // 이미 해당 screen 있으묜 재사용
        }
    }

    fun finishManual() {
        _isManual.value = false
        _manualStep.value = 0
    }
}