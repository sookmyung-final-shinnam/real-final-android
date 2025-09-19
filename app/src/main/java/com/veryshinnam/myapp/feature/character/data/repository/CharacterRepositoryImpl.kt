package com.veryshinnam.myapp.feature.character.data.repository

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.character.data.api.CharacterApi
import com.veryshinnam.myapp.feature.character.data.dto.CharacterDetailResponse
import com.veryshinnam.myapp.feature.character.data.dto.CharactersResponse
import com.veryshinnam.myapp.feature.character.data.dto.toCharacterData
import com.veryshinnam.myapp.feature.character.data.dto.toCollectionData
import com.veryshinnam.myapp.feature.character.model.CharacterData
import com.veryshinnam.myapp.feature.collection.model.CollectionData
import retrofit2.http.GET
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi
) : CharacterRepository {

    // 캐릭터 전체 조회 api 호출
    override suspend fun getCharacters(gender: String?): List<CollectionData> {
        val response: BaseResponse<CharactersResponse> = api.getCharacters(gender)

        if (!response.isSuccess || response.result == null) {
            throw Exception("캐릭터 전체 조회 실패: ${response.message}")
        }

        return response.result.characters.map { it.toCollectionData() }
    }

    // 캐릭터 상세 조회 api 호출
    override suspend fun getCharacterDetail(id: Long): CharacterData {
        val response: BaseResponse<CharacterDetailResponse> = api.getCharacterDetail(id)

        if (!response.isSuccess || response.result == null) {
            throw Exception("캐릭터 상세 조회 실패: ${response.message}")
        }

        return response.result.toCharacterData()
    }

    // 관심 캐릭터 등록 api 호출
    override suspend fun addFavorite(id: Long): Boolean {
        val res = api.addFavorite(id)

        if (!res.isSuccess) {
            throw Exception("관심 캐릭터 등록 실패: ${res.message}")
        }
        return true
    }

    // 관심 캐릭터 취소 api 호출
    override suspend fun removeFavorite(id: Long): Boolean {
        val res = api.deleteFavorite(id)

        if (!res.isSuccess) {
            throw Exception("관심 캐릭터 취소 실패: ${res.message}")
        }
        return res.isSuccess
    }
}