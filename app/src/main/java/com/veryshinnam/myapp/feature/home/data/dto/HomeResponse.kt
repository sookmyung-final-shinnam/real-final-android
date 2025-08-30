package com.veryshinnam.myapplication.feature.home.data.dto

// 홈 화면 응답 구조
data class HomeResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: HomeResult
)

// 홈 화면 상세 응답
data class HomeResult(
    val userId: Long,
    val nickname: String,
    val userPoint: Int,
    val userFairyNum: Int,
    val maxFairyNum: Int,
    val userFairyTaleNum: Int,
    val maxFairyTaleNum: Int,
    val favoriteFairies: List<CharacterShortResult>
)