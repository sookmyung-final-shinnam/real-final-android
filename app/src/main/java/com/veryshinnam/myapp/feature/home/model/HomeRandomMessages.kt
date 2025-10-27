package com.veryshinnam.myapp.feature.home.model

object HomeRandomMessages {
    val messages = listOf( // 랜덤 메시지 나열
        "오늘도 즐거운 하루 되세요!",
        "새로운 동화를 만들어볼까요?",
        "캐릭터와 함께 성장해봐요!",
        "스탬프를 모아보세요!"
    )

    fun getRandomMessage(): String {
        return messages.random()
    }
}