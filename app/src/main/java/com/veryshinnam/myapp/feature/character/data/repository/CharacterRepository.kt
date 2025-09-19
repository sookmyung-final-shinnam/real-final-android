package com.veryshinnam.myapp.feature.character.data.repository

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.character.data.dto.CharactersResponse
import com.veryshinnam.myapp.feature.collection.model.CollectionData
import retrofit2.http.GET

interface CharacterRepository {

    // 캐릭터 전체 조회 api
    suspend fun getCharacters(gender: String? = null): List<CollectionData>

    // 캐릭터 상세 조회 api
    // 관심 캐릭터 등록 api
    // 관심 캐릭터 취소 api
}