package com.veryshinnam.myapp.feature.character.component

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.VideoPlayer
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun CharacterStoryButton(
    storyId: Long,       // 동화 아이디
    storyType: StoryType, // 동화 타입
    storyUrl: String?,    // 동화 또는 영상 표지 이미지
    typeText: String,     // 동화 타입 텍스트
    onStoryClick: (Long, StoryType) -> Unit,
    onLockerClick: (Long) -> Unit = {},
    onShareClick: (String) -> Unit,
    modifier: Modifier
) {
    val isExist = !storyUrl.isNullOrBlank()

    // 동화 버튼
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(0.3f)) {
            Button(
                onClick = {
                    if (isExist) {
                        onStoryClick(storyId, storyType)
                    } else {
                        onLockerClick(storyId) // 자물쇠 > 동영상 제작
                    }
                },
                modifier = Modifier
                    .aspectRatio(1f),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                contentPadding = PaddingValues(0.dp) // 패딩 제거
            ) {
                if (storyType == StoryType.IMAGE) { // 동화가 이미지일 경우
                    AsyncImage(
                        model = storyUrl,
                        contentDescription = "${typeText} 버튼",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else { // 동화가 비디오일 경우 > 버튼 또는 잠금
                    if (!storyUrl.isNullOrBlank()) {
                        VideoPlayer(
                            videoUrl = storyUrl,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else { // 비디오 링크 없으면 잠금 이미지
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(colorResource(R.color.main_orange_shade)),
                        ) {
                            Image(
                                painter = painterResource(R.drawable.img_locker),
                                contentDescription = "${typeText} 잠금",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
            }

            if (isExist) {
                Button(
                    onClick = { onShareClick(storyUrl!!) },
                    modifier = Modifier
                        .align(Alignment.TopEnd) // 버튼 내부 우상단
                        .size(60.dp)
                        .zIndex(1f),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_kakao),
                        contentDescription = "카카오 버튼",
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
        Spacer(Modifier.height(4.dp))

        Text(
            text = "${typeText} 보러 가기",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold,
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
