package com.veryshinnam.myapp.core.session

import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebStorage
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.veryshinnam.myapp.core.session.ReviewToken.REVIEW_ACCESS_TOKEN
import com.veryshinnam.myapp.core.session.ReviewToken.REVIEW_EXPIRE_AT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    // SessionManager 키 정의
    private val accessToken = stringPreferencesKey("access_token")
    private val refreshToken = stringPreferencesKey("refresh_token")
    private val expiredAt = stringPreferencesKey("expired_at")
    private val isNewUser = booleanPreferencesKey("is_new_user") // 일회성
    private val isUsingReviewToken = booleanPreferencesKey("is_using_review_token") // 리뷰 토큰 사용 중
    private val reviewTokenRejected = booleanPreferencesKey("review_token_rejected") // 리뷰 토큰 401 받음


    // 로그인 필요 여부
    private val _requireLogin = MutableStateFlow(false)
    val requireLogin: StateFlow<Boolean> = _requireLogin

    // 토큰 저장
    suspend fun saveToken(access: String, refresh: String, expired: String) {
        dataStore.edit { pref ->
            pref[accessToken] = access
            pref[refreshToken] = refresh
            pref[expiredAt] = expired
            pref[isUsingReviewToken] = false // 일반 토큰
            // 새 로그인 시 리뷰 토큰 거부 플래그 제거
            pref.remove(reviewTokenRejected)
        }
        _requireLogin.value = false
        Log.d("Session", "saveToken access=$access refresh=$refresh expiredAt=$expiredAt")
    }


    // 리뷰 토큰 강제 설정
    suspend fun forceReviewToken() {
        dataStore.edit { pref ->
            pref.remove(refreshToken) // 리뷰 토큰은 refresh 없음
            pref[accessToken] = REVIEW_ACCESS_TOKEN
            pref[expiredAt] = REVIEW_EXPIRE_AT
            pref[isUsingReviewToken] = true // 리뷰 토큰 사용
        }
        _requireLogin.value = false
        Log.d("Session", "forceReviewToken set")
    }

    // 리뷰 토큰 사용 중인지 확인
    suspend fun isUsingReviewToken(): Boolean {
        return dataStore.data.first()[isUsingReviewToken] ?: false
    }

    // 액세스 토큰 조회 (비동기)
    suspend fun getToken(): String? = dataStore.data.first()[accessToken]

    // 액세스 토큰 조회 (동기, Interceptor용)
    fun getTokenBlocking(): String? = runBlocking { getToken() }

    fun getTokenFlow(): Flow<String?> = dataStore.data.map { it[accessToken] }

    // 액세스 토큰 만료 여부
    suspend fun isTokenExpired(): Boolean {
        val expiredAtStr = dataStore.data.first()[expiredAt] ?: return true // 없으면 토큰 만료

        val expiredAtSub = expiredAtStr.substring(0, 19) // 마이크로초 무시
        val expiredAt = LocalDateTime.parse(expiredAtSub, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        // expiredAt이 현재보다 과거면 만료
        return expiredAt.isBefore(LocalDateTime.now())
    }

    // 토큰 삭제 (비동기)
    suspend fun clearToken() {

        dataStore.edit { pref ->
            // 1. 플래그 백업
            val wasRejected = pref[reviewTokenRejected] == true
            // 2. 전부 삭제
            pref.clear()
            // 3. 플래그만 복구
            if (wasRejected) {
                pref[reviewTokenRejected] = true
            }
        }

        _requireLogin.value = true
    }

    // 웹뷰 세션 삭제
    fun clearKakaoWebViewSession() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies(null)
        cookieManager.flush()
        WebStorage.getInstance().deleteAllData()
    }

    // 신규 유저 플래그 저장
    suspend fun saveNewUser(value: Boolean) {
        dataStore.edit { pref -> pref[isNewUser] = value }
    }

    // 신규 유저 여부 조회 (비동기)
    suspend fun isNewUser(): Boolean {
        return dataStore.data.first()[isNewUser] ?: false
    }

    // 신규 유저 플래그 삭제
    suspend fun removeNewUser() {
        dataStore.edit { pref -> pref.remove(isNewUser) }
    }

    // 리뷰 기간 확인
    fun isReviewPeriod(): Boolean {
        val now = LocalDateTime.now()
        val reviewExpireAt = LocalDateTime.parse(REVIEW_EXPIRE_AT)
        return now.isBefore(reviewExpireAt)
    }

    // 401 감지
    // 리뷰 토큰 401 받음
    suspend fun markReviewTokenRejected() {
        if (isUsingReviewToken()) {
            dataStore.edit { pref ->
                pref[reviewTokenRejected] = true
                Log.d("Session", "Review token rejected - marked")
            }
        }
    }

    // 리뷰 토큰이 401 받았는지 확인
    suspend fun isReviewTokenRejected(): Boolean {
        return dataStore.data.first()[reviewTokenRejected] ?: false
    }

    // 리뷰 관련 상태 정리 (리뷰 기간 종료 시)
    suspend fun clearReviewState() {
        dataStore.edit { pref ->
            pref.remove(reviewTokenRejected)
            Log.d("Session", "Review state cleared")
        }
    }
}