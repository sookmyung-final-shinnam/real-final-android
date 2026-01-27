package com.veryshinnam.myapp.core.network

import android.util.Log
import com.veryshinnam.myapp.core.session.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        // 로그인 api는 헤더 제외
        val request = chain.request()
        if (request.url.encodedPath.contains("/api/permit/login")) {
            return chain.proceed(request)
        }

        val token = sessionManager.getTokenBlocking()

        val requestWithAuth = request.newBuilder().apply {
            token?.let {
                addHeader("Authorization", "Bearer $it")
                Log.d("AuthInterceptor", "Authorization=Bearer $it")
            }
        }.build()

        val response = chain.proceed(requestWithAuth)
        Log.d("AuthInterceptor", "ResponseCode=${response.code} for ${request.url}")

        if (response.code == 401) {
            runBlocking {
                val isUsingReview = sessionManager.isUsingReviewToken()

                if (isUsingReview) {
                    // 리뷰 토큰이 401 받음 -> 기록
                    sessionManager.markReviewTokenRejected()
                    Log.d("AuthInterceptor", "Review token got 401 - marked as rejected")
                }

                // 리뷰든 일반이든 무조건 전부 삭제
                sessionManager.clearToken()
                Log.d("AuthInterceptor", "401 detected - all tokens cleared")
            }
        }

        return response
    }
}