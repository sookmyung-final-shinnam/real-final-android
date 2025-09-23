package com.veryshinnam.myapp.feature.settings.data.repository

interface UserRepository {

    // 로그아웃
    suspend fun logout(): Boolean

    // 회원탈퇴
    suspend fun withdraw(): Boolean
}