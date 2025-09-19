package com.veryshinnam.myapp.feature.home.data.dto

import com.veryshinnam.myapp.feature.home.model.FavoriteData
import com.veryshinnam.myapp.feature.home.model.HomeData

fun HomeResponse.toDomain(): HomeData {
    return HomeData(
        username = username,
        points = 5, // 기본값
        favorites = favorites.map { it.toDomain() }
    )
}

fun FavoritesDto.toDomain(): FavoriteData {
    return FavoriteData(
        id = id,
        name = name,
        image = imageUrl
    )
}