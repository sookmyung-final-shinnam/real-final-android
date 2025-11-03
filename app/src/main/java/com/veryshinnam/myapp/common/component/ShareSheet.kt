package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.veryshinnam.myapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareSheet(
    urlText: String,
    urlTextStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(color = Color.White, fontWeight = FontWeight.SemiBold),
    shareText: String = "동화를 친구들에게도 보여줄까요?",
    shareTextStyle: TextStyle =  MaterialTheme.typography.titleSmall.copy(color = Color.White, textAlign = TextAlign.Center, lineHeight = 1.1.em),
    confirmText: String,
    confirmTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black, fontWeight = FontWeight.Bold),
    verticalPadding: Dp = 16.dp,
    horizontalPadding: Dp = 20.dp,
    onDismiss: () -> Unit,
    onShare: () -> Unit,
    onCopy: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Expanded } // 전체 높이 허용 x
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        dragHandle = null, // 손잡이 제거
        containerColor = colorResource(R.color.main_orange),
    ) {
        // --- 닫기 버튼 + 공유 내용 + 공유하기 버튼
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalPadding, horizontal = horizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                // --- 닫기 버튼
                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.fillMaxWidth(.06f)
                        .align(Alignment.TopEnd)
                        .aspectRatio(1f)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "닫기",
                        tint = colorResource(R.color.main_orange_50),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // --- 공유 내용 (공유 이미지 + 문구 + 공유 url)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = verticalPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(verticalPadding / 2)
                ) {
                    // 이미지
                    Image(
                        painter = painterResource(R.drawable.img_speak_on),
                        contentDescription = "공유 안내 이미지",
                        modifier = Modifier.fillMaxWidth(0.1f),
                        contentScale = ContentScale.Fit
                    )

                    // 문구
                    Text(
                        text = shareText,
                        style = shareTextStyle,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    // --- url (url + 복사 버튼)
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        // url
                        Text(
                            // 길이 알아서 자르기
                            text = urlText,
                            style = urlTextStyle,
                            modifier = Modifier.wrapContentWidth(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(Modifier.width(20.dp))

                        // 복사 버튼
                        Button(
                            onClick = { onCopy() },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.main_orange_50)),
                            shape = RoundedCornerShape(16.dp),
                        ) {
                            Text(
                                text = "복사",
                                style = urlTextStyle.copy(color = Color.Black),
                            )
                        }
                    }

                    // --- 공유하기 버튼
                    Button(
                        onClick = {
                            onShare()
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.main_orange_50)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text(
                            text = confirmText,
                            style = confirmTextStyle,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}