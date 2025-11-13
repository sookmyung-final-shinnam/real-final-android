package com.veryshinnam.myapp.core.network

import android.util.Log
import com.veryshinnam.myapp.core.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sessionManager.getTokenBlocking()

        // 로그인 api 헤더 제외
        val request = chain.request()
        if (request.url.encodedPath.contains("/api/permit/login")) {
            return chain.proceed(request)
        }

        val requestBuilder = request.newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $token")
            Log.d("AccessToken", "Authorization=Bearer $token")
        }

        val response = chain.proceed(requestBuilder.build())
        Log.d("AccessToken", "ResponseCode=${response.code} for ${request.url}")
        if (response.code == 401) {
            Log.w("AccessToken", "401 Unauthorized. Clearing token.")
            sessionManager.clearTokenBlocking()
        }

        return response
    }
}