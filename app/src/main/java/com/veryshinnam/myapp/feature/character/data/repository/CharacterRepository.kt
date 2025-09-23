package com.veryshinnam.myapp.feature.character.data.repository

import com.veryshinnam.myapp.feature.character.model.CharacterData
import com.veryshinnam.myapp.feature.character.model.StoriesData
import com.veryshinnam.myapp.feature.collection.model.CollectionData
import retrofit2.http.GET

interface CharacterRepository {

    // 캐릭터 전체 조회
    suspend fun getCharacters(gender: String? = null): List<CollectionData>

    // 캐릭터 상세 조회
    suspend fun getCharacterDetail(id: Long): CharacterData

    // 캐릭터 스토리 조회
    suspend fun getStories(characterId: Long): StoriesData

    // 관심 캐릭터 등록
    suspend fun addFavorite(id: Long): Boolean

    // 관심 캐릭터 취소
    suspend fun removeFavorite(id: Long): Boolean

    // 페이지별 동영상 생성
    suspend fun generateVideo(id: Long): Boolean
}