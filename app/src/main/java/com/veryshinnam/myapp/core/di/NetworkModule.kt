package com.veryshinnam.myapp.core.di

import com.veryshinnam.myapp.core.network.BaseUrls
import com.veryshinnam.myapp.feature.permit.data.api.PermitApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// api 기본 요청
// Retrofit + OkHttpClient
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule  {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // 서버 연결 최대 30초 대기
            .readTimeout(30, TimeUnit.SECONDS)    // 응답 수신 최대 30초 대기
            .writeTimeout(30, TimeUnit.SECONDS)   // 요청 전송 최대 30초 대기
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BaseUrls.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환
            .build()

    // PermitApi 구현체 생성
    // permit.login() > POST /api/permit/login
    @Provides
    @Singleton
    fun providePermitApi(retrofit: Retrofit): PermitApi =
        retrofit.create(PermitApi::class.java)
}