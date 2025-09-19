package com.veryshinnam.myapp.feature.character.data.api

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.character.data.dto.CharacterDetailDto
import com.veryshinnam.myapp.feature.character.data.dto.CharactersDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    // 캐릭터 전체 조회
    @GET("api/characters")
    suspend fun getCharacters(
        @Query("gender") gender: String? = null
    ): BaseResponse<CharactersDto>

    // 캐릭터 상세 조회
    @GET("api/characters/{characterId}")
    suspend fun getCharacterDetail(
        @Path("characterId") characterId: Long
    ): BaseResponse<CharacterDetailDto>

    // 관심 캐릭터 등록
    @POST("api/characters/{characterId}/favorite")
    suspend fun addFavorite(
        @Path("characterId") characterId: Long
    ): BaseResponse<String>

    // 관심 캐릭터 취소
    @DELETE("api/characters/{characterId}/favorite")
    suspend fun deleteFavorite(
        @Path("characterId") characterId: Long
    ): BaseResponse<String>
}