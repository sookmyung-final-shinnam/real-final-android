package com.veryshinnam.myapp.feature.creation.content.conversation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import kotlinx.coroutines.delay

@Composable
fun ConversationEndContent(
    onHomeClick: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = Bold, textAlign = TextAlign.Center),
    verticalPadding: Dp = 24.dp,
) {
    // ui
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Box(modifier = Modifier.fillMaxHeight(0.5f)){
            Image(
                painter = painterResource(R.drawable.img_login),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(verticalPadding))

            // 완료 텍스트
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(32.dp),
                border = BorderStroke(4.dp, colorResource(R.color.main_orange)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(verticalPadding, verticalPadding),
                    contentAlignment = Alignment.Center
                ) {
                    // 질문 텍스트
                    Text(
                        text = "다 끝났어요!\n\n" +
                                "이제 제가 동화를 재미있게 만들어 드릴게요.\n" +
                                "잠시 뒤에 보관함에서 만나요!",
                        style = textStyle.copy(
                            lineHeight = textStyle.lineHeight * 1.2f
                        )

                    )
                }
            }
            Spacer(Modifier.height(verticalPadding))
            // 홈 버튼
            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = { onHomeClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.main_orange)
                ),
                shape = CircleShape,
            ) {
                Text(
                    text = "홈으로",
                    modifier = Modifier.padding(8.dp)
                        .semantics {
                            contentDescription = "홈 화면으로 이동"
                        },
                    style =textStyle.copy(
                        color = Color.White,
                        fontSize = textStyle.fontSize * 1.4f
                    )
                )
            }
        }
    }
}