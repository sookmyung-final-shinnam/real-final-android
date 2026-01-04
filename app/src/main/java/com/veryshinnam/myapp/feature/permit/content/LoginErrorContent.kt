package com.veryshinnam.myapp.feature.permit.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.LogoBar

@Composable
fun LoginErrorContent(
    modifier: Modifier,
    onBack: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize().padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        LogoBar(
            modifier = Modifier.statusBarsPadding(),
            onLogoClick = onBack
        )

        Column(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(
                    width = 4.dp,
                    color = colorResource(R.color.main_orange),
                    shape = RoundedCornerShape(16.dp)
                )
                .align(Alignment.Center)
                .fillMaxWidth()
                .fillMaxHeight(.5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "인터넷 연결이 필요해요.\n연결 후 다시 시도해주세요.",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = Bold),
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.main_orange),
                    contentColor = Color.White,
                ),
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("뒤로 가기",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = Bold),
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
        }

        Image(
            painter = painterResource(R.drawable.img_llm_feedback_negative),
            contentDescription = "네트워크 실패 이미지",
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = 24.dp, end = 24.dp)
                .graphicsLayer {
                    scaleX = 1.4f
                    scaleY = 1.4f
                }
                .align(Alignment.BottomEnd)
        )
    }
}