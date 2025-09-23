package com.veryshinnam.myapp.feature.dashboard.model

data class PlayData (
    val recentPlayTime: String, // 최근 앱 사용 시간
    val mostSelectedTheme: String, // 가장 많이 고른 테마
    val mostSelectedBackground: String, // 가장 많이 고른 배경
    val storyReplayCount: String, // 생성한 동화 다시 읽은 횟수
)