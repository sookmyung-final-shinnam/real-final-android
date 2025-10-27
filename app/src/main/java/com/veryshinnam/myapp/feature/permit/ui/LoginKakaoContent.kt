package com.veryshinnam.myapp.feature.permit.ui

import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.veryshinnam.myapp.core.network.BaseUrls

/**
 * 카카오 로그인
 * : 웹뷰로 카카오 로그인 창 로드 후, 카카오 로그인 진행 및 스웨거 주소로 리다이렉트
 *
 * - onTempCodeReceived: 스웨거 주소에서 로그인 임시 코드와 신규 유저 여부를 추출하여 로그인 화면(LoginScreen)으로 전달
 */
@Composable
fun LoginKakaoContent(
    onTempCodeReceived: (String, Boolean) -> Unit,
    modifier: Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                settings.javaScriptEnabled = true

                webViewClient = object : WebViewClient() { // 리다이렉트 감지 (2)
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        // 불러올 웹뷰
                        val uri = request?.url ?: return false

                        // 리다이렉트 주소에서 정보 추출
                        if (uri.toString().contains(BaseUrls.REDIRECT_PATH)) {
                            val tempCode = uri.getQueryParameter("tempCode") // 임시 코드
                            val isNewUser = uri.getQueryParameter("isNewUser").toBoolean() // 기존 유저 여부
                            if (tempCode != null) {
                                onTempCodeReceived(tempCode, isNewUser)
                            }
                            return true
                        }
                        return false
                    }
                }

                loadUrl(BaseUrls.KAKAO_URL) // 카카오 로그인 창 로드 (1)
            }
        },
        modifier = modifier
    )
}