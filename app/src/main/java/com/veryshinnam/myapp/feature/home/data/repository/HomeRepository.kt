package com.veryshinnam.myapp.feature.home.data.repository

import com.veryshinnam.myapp.feature.home.model.HomeData

interface HomeRepository {

    // 홈 화면 조회
    suspend fun getHome(): HomeData
}