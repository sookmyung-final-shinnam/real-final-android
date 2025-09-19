package com.veryshinnam.myapp.feature.home.data.dto

import com.veryshinnam.myapp.feature.home.model.FavoriteData
import com.veryshinnam.myapp.feature.home.model.HomeData

fun HomeResponse.toHomeData(): HomeData =
    HomeData(
        username = username,
        points = 5, // 기본값
        favorites = favorites.map { it.toFavoriteData() }
    )

fun FavoriteDto.toFavoriteData(): FavoriteData =
    FavoriteData(
        id = id,
        name = name,
        image = imageUrl
    )