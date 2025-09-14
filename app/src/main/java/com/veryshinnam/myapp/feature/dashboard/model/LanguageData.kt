package com.veryshinnam.myapp.feature.dashboard.model

data class LanguageData (
    val recentSpokenWord: String, // 최근 가장 많이 말한 단어
    val recentNewWord: String, // 최근 새롭게 사용한 단어
    val recentSlangLevel: String, // 최근 사용한 비속어 정도
    val contextUnderstanding: String, // 언어 맥락 이해도
)