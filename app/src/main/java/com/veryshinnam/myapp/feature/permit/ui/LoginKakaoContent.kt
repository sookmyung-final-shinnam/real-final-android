package com.veryshinnam.myapp.feature.permit.ui

import android.net.http.SslError
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    onTempCodeReceived: (String, Boolean, Boolean) -> Unit,
    modifier: Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true       // crash 방지
                    mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
                    allowFileAccess = false
                    allowContentAccess = false
                }

                webChromeClient = WebChromeClient()
                webViewClient = object : WebViewClient() { // 리다이렉트 감지 (2)

                    override fun onReceivedSslError(
                        view: WebView?,
                        handler: SslErrorHandler?,
                        error: SslError?
                    ) {
                        // 인증서 오류
                        handler?.cancel()
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        // 불러올 웹뷰
                        val uri = request?.url ?: return false

                        // https만 인정
                        if (uri.scheme != "https") return true

                        // 리다이렉트 주소에서 정보 추출
                        if (uri.toString().contains(BaseUrls.REDIRECT_PATH)) {
                            val tempCode = uri.getQueryParameter("tempCode") // 임시 코드
                            val isNewUser = uri.getQueryParameter("isNewUser").toBoolean() // 기존 유저 여부
                            val isAgreedToTerms = uri.getQueryParameter("isAgreedToTerms").toBoolean() // 약관 동의 여부

                            if (tempCode != null) {
                                onTempCodeReceived(tempCode, isNewUser, isAgreedToTerms)
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