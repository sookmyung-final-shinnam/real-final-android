package com.veryshinnam.myapp.common.model

enum class ManualState {
    NONE,      // 매뉴얼 아님
    REQUEST,   // 매뉴얼 요청 (신규 유저)
    START,     // 매뉴얼 진행
    FINISH,    // 매뉴얼 종료
    STOP       // 강제 종료 안내
}