package com.veryshinnam.myapp.feature.creation.ui.selection

import com.veryshinnam.myapp.feature.creation.model.SelectionData
import com.veryshinnam.myapp.feature.creation.model.SelectionStep

data class SelectUiState (
    val selectionStep: SelectionStep = SelectionStep.THEME, // 화면 분기 처리용
    val currentStep: Int = 1,        // 현재 선택 단계
    val selectionData: SelectionData // 선택된 정보들
)