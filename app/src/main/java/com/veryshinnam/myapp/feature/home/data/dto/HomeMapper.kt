package com.veryshinnam.myapp.feature.home.data.dto

import com.veryshinnam.myapp.feature.home.model.FavoriteData
import com.veryshinnam.myapp.feature.home.model.HomeData

fun HomeResult.toHomeData(): HomeData =
    HomeData(
        username = username,
//        username = "짱신남",
        points = 5, // 기본값
        favorites = favorites.map { it.toFavoriteData() }
    )

fun FavoriteResult.toFavoriteData(): FavoriteData =
    FavoriteData(
        id = id,
        name = name,
        image = imageUrl
    )