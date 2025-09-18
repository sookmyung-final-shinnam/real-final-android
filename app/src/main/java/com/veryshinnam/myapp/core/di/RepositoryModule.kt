package com.veryshinnam.myapp.core.di

import com.veryshinnam.myapp.feature.permit.data.repository.PermitRepository
import com.veryshinnam.myapp.feature.permit.data.repository.PermitRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPermitRepository(
        impl: PermitRepositoryImpl
    ): PermitRepository
}