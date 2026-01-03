package com.veryshinnam.myapp.core.network

import android.util.Log
import com.veryshinnam.myapp.core.session.ReviewToken
import com.veryshinnam.myapp.core.session.SessionManager
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

        if (response.code != 401) return response

        if (token == ReviewToken.REVIEW_ACCESS_TOKEN) {
            Log.w("AuthInterceptor", "401 ignored (review token)")
            return response
        }

        sessionManager.clearTokenBlocking()
        return response
    }
}