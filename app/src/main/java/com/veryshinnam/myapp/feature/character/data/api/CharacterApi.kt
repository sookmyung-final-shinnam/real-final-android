package com.veryshinnam.myapp.feature.character.data.api

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.character.data.dto.CharactersResponse
import com.veryshinnam.myapp.feature.home.data.dto.HomeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    // 캐릭터 전체 조회
    @GET("/api/characters")
    suspend fun getCharacters(
        @Query("gender") gender: String? = null
    ): BaseResponse<CharactersResponse>

    // 캐릭터 상세 조회
    // 관심 캐릭터 등록
    // 관심 캐릭터 취소
}