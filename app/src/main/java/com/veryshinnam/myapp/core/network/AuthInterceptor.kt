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

        if (response.code == 401) {
            if (sessionManager.isReviewPeriod()) {
                // 리뷰 기간 > 401 무시
                Log.w("AuthInterceptor", "401 ignored (review period)")
            } else {
                // 리뷰 기간 종료
                sessionManager.clearTokenOnce()  // 리뷰 토큰 가지고 있을때 > 토큰 비우기 (스플래시로)
                sessionManager.setRequireLogin() // 전역 401 감지
            }
        }

        return response
    }
}