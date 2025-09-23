package com.veryshinnam.myapp.core.di

import com.veryshinnam.myapp.core.speech.stt.SttManager
import com.veryshinnam.myapp.core.speech.stt.SttManagerImpl
import com.veryshinnam.myapp.core.speech.tts.TtsManager
import com.veryshinnam.myapp.core.speech.tts.TtsManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SpeechModule {

    @Binds
    @Singleton
    abstract fun bindTts(impl: TtsManagerImpl): TtsManager

    @Binds
    @Singleton
    abstract fun bindStt(impl: SttManagerImpl): SttManager
}