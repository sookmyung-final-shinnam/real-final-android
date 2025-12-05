package com.veryshinnam.myapp.feature.character.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.VideoPlayer
import com.veryshinnam.myapp.common.model.ImageType
import com.veryshinnam.myapp.feature.character.model.StoryStatus
import com.veryshinnam.myapp.feature.story.model.StoryType

@Composable
fun CharacterStoryButton(
    storyId: Long,          // 동화 아이디
    storyType: StoryType,   // 동화 타입
    storyTypeText: String,  // 동화 타입 텍스트
    storyUrl: ImageType? = null, // 종이 동화 표지
    videoUrl: String? = null,    // 영상 동화 표지
    storyYLink: String?,    // 동화 유튜브 링크
    storyStatus: StoryStatus,
    buttonSize: Float = 0.7f,
    buttonRadius: Dp = 16.dp,
    kakaoSize: Float = 0.35f,
    kakaoRadius: Dp = 12.dp,
    onStoryClick: (Long, StoryType) -> Unit,
    onLockerClick: (Long) -> Unit = {},
    onMakingClick: () -> Unit = {},
    onShareClick: (String?) -> Unit,
    modifier: Modifier
) {
    val isShareEnabled = when (storyType) {
        StoryType.IMAGE -> storyUrl != null      // 이미지 표지 있으면 공유 가능
        StoryType.VIDEO -> !videoUrl.isNullOrBlank()   // mp4 표지 있으면 공유 가능
    }

    // 동화 버튼
    Box(
        modifier = modifier.fillMaxHeight(buttonSize),
        contentAlignment = Alignment.TopEnd
    ) {
        Button(
            onClick = {
                when (storyStatus) {
                    StoryStatus.NONE -> onLockerClick(storyId)  // 잠금 > 제작
                    StoryStatus.MAKING  -> onMakingClick()      // 제작 중 > 시트 오픈
                    StoryStatus.COMPLETED -> {
                        if (storyType == StoryType.VIDEO && videoUrl.isNullOrBlank()) onMakingClick() // 서버 문의
                        else onStoryClick(storyId, storyType)   // IMAGE는 항상 이동, VIDEO는 videoUrl 있으면 이동
                    }
                }
            },
            modifier = Modifier.aspectRatio(1f),
            shape = RoundedCornerShape(buttonRadius),
            contentPadding = PaddingValues(0.dp) // 패딩 제거
        ) {
            when (storyType) {
                StoryType.IMAGE -> {
                    when (storyUrl) {
                        is ImageType.Url -> {
                            AsyncImage(
                                model = storyUrl.url,
                                contentDescription = "$storyTypeText 버튼",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        is ImageType.Resource -> {
                            Image(
                                painter = painterResource(id = storyUrl.resId),
                                contentDescription = "$storyTypeText 버튼",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        else -> {
                            Image(
                                painter = painterResource(R.drawable.img_locker),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }

                // 동화가 비디오일 경우
                StoryType.VIDEO -> {
                    when (storyStatus) {
                        StoryStatus.NONE -> {
                            Image(
                                painter = painterResource(R.drawable.img_locker),
                                contentDescription = "$storyTypeText 잠금",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }

                        StoryStatus.MAKING -> {
                            Image(
                                painter = painterResource(R.drawable.img_unlocker),
                                contentDescription = "$storyTypeText 제작 중",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }

                        StoryStatus.COMPLETED -> {
                            if (videoUrl != null)
                                VideoPlayer(
                                    videoUrl = videoUrl,
                                    modifier = Modifier.fillMaxSize()
                                )
                            else {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = colorResource(id = R.color.main_orange),
                                        trackColor = Color.White.copy(alpha = 0.5f),
                                        strokeWidth = 6.dp,
                                        modifier = Modifier
                                            .zIndex(1f)
                                            .fillMaxSize(0.7f)
                                    )

                                    Image(
                                        painter = painterResource(R.drawable.img_unlocker),
                                        contentDescription = "$storyTypeText 제작 중",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (isShareEnabled) {
            Button(
                onClick = { onShareClick(storyYLink) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxHeight(kakaoSize)
                    .aspectRatio(1f)
                    .zIndex(1f),
                shape = RoundedCornerShape(kakaoRadius),
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
}
