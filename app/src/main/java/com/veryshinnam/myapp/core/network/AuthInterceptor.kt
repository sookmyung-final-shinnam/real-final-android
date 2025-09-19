package com.veryshinnam.myapp.core.network

import com.veryshinnam.myapp.core.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 로그인 api 헤더 제외
        if (request.url.encodedPath.contains("/api/permit/login")) {
            return chain.proceed(request)
        }

        val requestBuilder = request.newBuilder()
        sessionManager.getCachedToken()?.let { token ->
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val response = chain.proceed(requestBuilder.build())

        if (response.code == 401) {
            sessionManager.clearToken()
        }

        return response
    }
}