package com.veryshinnam.myapp.common.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
fun WarningSimpleSheet(
    warningText: String,
    warningTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(color = Color.White, textAlign = TextAlign.Center, lineHeight = 1.2.em),
    verticalPadding: Dp = 16.dp,
    horizontalPadding: Dp = 20.dp,
    onDismiss: () -> Unit,
    dismissible: Boolean = true // 기본 값
) {
//    val configuration = LocalConfiguration.current // 가로-세로 모드 구분
//    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
//
//    val windowInfo = LocalWindowInfo.current        // 현재 창
//    val density = LocalDensity.current              // 현재 기기 밀도 값
//    val heightPx = windowInfo.containerSize.height  // 현재 창의 컨테이너 높이 (px)
//    val heightDp = with(density) { heightPx.toDp() } // px > dp

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { it != SheetValue.Expanded } // 전체 높이 허용 x
    )

    ModalBottomSheet(
        onDismissRequest = {  onDismiss() },
        sheetState = sheetState,
        dragHandle = null, // 손잡이 제거
        containerColor = colorResource(R.color.main_orange)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .fillMaxHeight(.35f)
//                .then(
//                    if (isPortrait) Modifier.height(heightDp * 0.3f) //  화면 높이 30%
//                    else Modifier.fillMaxSize()
//                )
                .padding(vertical = verticalPadding, horizontal = horizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.fillMaxWidth(.12f)
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = verticalPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(verticalPadding/2)
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_speak_on),
                        contentDescription = "경고 이미지",
                        modifier = Modifier.fillMaxWidth(0.2f),
                        contentScale = ContentScale.Fit
                    )

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
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                }
            }
        }
    }
}