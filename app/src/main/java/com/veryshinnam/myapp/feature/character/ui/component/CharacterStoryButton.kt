package com.veryshinnam.myapp.feature.character.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun CharacterStoryButton(
    storyId: Long?,    // 동화 아이디
    storyType: StoryType, // 동화 타입
    storyUrl: String?, // 동화 또는 영상 표지 이미지
    type: String,    // 동화 타입 텍스트
    onStoryClick: (Long, StoryType) -> Unit,
    modifier: Modifier = Modifier
) {
    val isExist = !storyUrl.isNullOrBlank()

    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { if (isExist && storyId != null) onStoryClick(storyId, storyType) },  // storyId 전달
            modifier = Modifier
                .weight(0.75f)
                .aspectRatio(1f),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp) // 패딩 제거
        ) {
            if (isExist) {
                // 존재하면 이미지
                AsyncImage(
                    model = storyUrl,
                    contentDescription = "${type} 버튼",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // 없으면 잠금 이미지
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.main_orange_shade)),
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_locker),
                        contentDescription = "${type} 잠금",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = "${type} 보러 가기",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold,
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}