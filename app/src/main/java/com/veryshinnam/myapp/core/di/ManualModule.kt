package com.veryshinnam.myapp.core.di

import com.veryshinnam.myapp.core.manual.ManualManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManualModule {

    @Provides
    @Singleton
    fun provideManualManager(): ManualManager {
        return ManualManager()
    }
}