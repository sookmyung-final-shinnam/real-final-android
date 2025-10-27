package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareSheet(
    shareUrl: String,
    shareText: String,
    onDismiss: () -> Unit,
    onShare: () -> Unit,
    onCopy: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { value ->
            value != SheetValue.Expanded // 전체 높이 허용 x
        }
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        dragHandle = null, // 손잡이 제거
        containerColor = colorResource(R.color.main_orange),
        contentWindowInsets = { WindowInsets(0.dp) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(10.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "닫기",
                        tint = colorResource(R.color.main_orange_50),
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.img_speak_on),
                contentDescription = "공유 안내 이미지",
                modifier = Modifier.fillMaxWidth(0.2f),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "동화를 친구들에게도 보여줄까요?",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    // 길이 알아서 자르기
                    text = shareUrl,
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.wrapContentWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.width(20.dp))

                Button(
                    onClick = { onCopy() },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.main_orange_50)),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                        text = "복사",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.Black,
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

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
                    text = shareText,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.Black,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}