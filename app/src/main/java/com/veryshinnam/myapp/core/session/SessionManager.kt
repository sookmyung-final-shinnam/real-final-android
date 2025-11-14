package com.veryshinnam.myapp.core.session

import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebStorage
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    // SessionManager 키 정의
    private val accessToken = stringPreferencesKey("access_token")
    private val refreshToken = stringPreferencesKey("refresh_token")
    private val expiredAt = stringPreferencesKey("expired_at")
    private val isNewUser = booleanPreferencesKey("is_new_user")

    // 로그인 필요 여부
    private val _requireLogin = MutableStateFlow(false)
    val requireLogin: StateFlow<Boolean> = _requireLogin

    // 토큰 저장
    suspend fun saveToken(access: String, refresh: String, expired: String) {
        dataStore.edit { pref ->
            pref[accessToken] = access
            pref[refreshToken] = refresh
            pref[expiredAt] = expired
        }
        _requireLogin.value = false
        Log.d("Session", "saveToken access=$access refresh=$refresh expiredAt=$expiredAt")
    }

    // 액세스 토큰 조회 (비동기)
    suspend fun getToken(): String? = dataStore.data.first()[accessToken]

    // 액세스 토큰 조회 (동기, Interceptor용)
    fun getTokenBlocking(): String? = runBlocking { getToken() }


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
        dataStore.edit { it.clear() } // DataStore 비우기
        _requireLogin.value = true
    }

    // 토큰 삭제 (동기, Interceptor용)
    fun clearTokenBlocking() {
        runBlocking { dataStore.edit { it.clear() } } // DataStore 비우기
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
}