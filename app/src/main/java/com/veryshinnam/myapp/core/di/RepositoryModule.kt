package com.veryshinnam.myapp.core.di

import com.veryshinnam.myapp.feature.character.data.api.CharacterApi
import com.veryshinnam.myapp.feature.character.data.repository.CharacterRepository
import com.veryshinnam.myapp.feature.character.data.repository.CharacterRepositoryImpl
import com.veryshinnam.myapp.feature.home.data.repository.HomeRepository
import com.veryshinnam.myapp.feature.home.data.repository.HomeRepositoryImpl
import com.veryshinnam.myapp.feature.permit.data.repository.PermitRepository
import com.veryshinnam.myapp.feature.permit.data.repository.PermitRepositoryImpl
import com.veryshinnam.myapp.feature.story.data.repository.StoryRepository
import com.veryshinnam.myapp.feature.story.data.repository.StoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPermitRepository(impl: PermitRepositoryImpl): PermitRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    @Singleton
    abstract fun bindStoryRepository(impl: StoryRepositoryImpl): StoryRepository
}