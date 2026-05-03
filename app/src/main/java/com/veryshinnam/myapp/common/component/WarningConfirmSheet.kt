package com.veryshinnam.myapp.common.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.veryshinnam.myapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarningConfirmSheet(
    warningText: String,
    confirmText: String,
    confirmTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black, fontWeight = FontWeight.Bold),
    verticalPadding: Dp = 16.dp,
    horizontalPadding: Dp = 20.dp,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val configuration = LocalConfiguration.current // 가로-세로 모드 구분
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val iconSize = if (isPortrait) 0.12f else 0.06f
    val imageSize = if (isPortrait) 0.2f else 0.1f // 세로 0.2, 가로 0.1
    val warningTextStyle = if (isPortrait) {
        MaterialTheme.typography.titleSmall.copy(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 1.2.em)
    } else {
        MaterialTheme.typography.bodyLarge.copy(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 1.2.em
        )
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Expanded } // 전체 높이 허용 x
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        dragHandle = null, // 손잡이 제거
        containerColor = colorResource(R.color.main_orange)
    ) {
        // --- 닫기 버튼 + 경고 내용 + 확인 버튼
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalPadding, horizontal = horizontalPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { isTraversalGroup = true },
            ) {
                // --- 닫기 버튼
                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.fillMaxWidth(iconSize)
                        .align(Alignment.TopEnd)
                        .aspectRatio(1f)
                        .semantics { traversalIndex = 1f }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "시트 닫기",
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // --- 경고 내용 (경고 이미지 + 문구)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = verticalPadding)
                        .semantics { traversalIndex = 0f },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(verticalPadding/2)
                ) {
                    // 이미지
                    Image(
                        painter = painterResource(R.drawable.img_speak_on),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(imageSize),
                        contentScale = ContentScale.Fit
                    )

                    // 문구
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = warningTextStyle.toSpanStyle().copy(
                                    fontSize = warningTextStyle.fontSize * 1.2f,
                                    fontWeight = FontWeight.Bold,
                                )
                            ) { append("앗!\n\n") }
                            // 경고 문구
                            withStyle(
                                style = warningTextStyle.toSpanStyle()
                            ) { append(warningText) }
                        },
                        style = warningTextStyle,
                        modifier = Modifier.fillMaxWidth()
                            .clearAndSetSemantics {
                                contentDescription = "경고. $warningText"
                            }
                    )

                    Spacer(Modifier.height(4.dp))

                    // --- 확인 버튼
                    Button(
                        onClick = {
                            onConfirm()
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.main_orange_50)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(16.dp),
                                clip = false
                            )
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