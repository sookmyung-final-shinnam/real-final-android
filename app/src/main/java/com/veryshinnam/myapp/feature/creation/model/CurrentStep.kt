package com.veryshinnam.myapp.feature.creation.model

enum class CurrentStep {
    START,            // 처음 시작
    STORY,            // nextStory 보여줌
    QUESTION,         // 질문 표시
    ANSWER,     // 사용자 대답 입력
    FEEDBACK,         // 피드백 표시
    END               // 종료
}