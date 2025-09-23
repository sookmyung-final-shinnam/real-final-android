package com.veryshinnam.myapp.common.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarningSimpleSheet(
    warningText: String,
    onDismiss: () -> Unit,
    dismissible: Boolean = true // 기본 값
) {
    val configuration = LocalConfiguration.current // 가로-세로 모드 구분
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val windowInfo = LocalWindowInfo.current        // 현재 창
    val density = LocalDensity.current              // 현재 기기 밀도 값
    val heightPx = windowInfo.containerSize.height  // 현재 창의 컨테이너 높이 (px)
    val heightDp = with(density) { heightPx.toDp() } // px > dp

    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { value ->
            if (!dismissible && value == SheetValue.Hidden) false
            else value != SheetValue.Expanded
        }
    )

    ModalBottomSheet(
        onDismissRequest = {  onDismiss() },
        sheetState = sheetState,
        dragHandle = null, // 손잡이 제거
        containerColor = colorResource(R.color.main_orange),
        windowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (isPortrait) Modifier.height(heightDp * 0.3f) //  화면 높이 30%
                    else Modifier.fillMaxSize()
                )
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
                contentDescription = "경고 이미지",
                modifier = Modifier.fillMaxWidth(0.2f),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "앗!\n$warningText",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }
    }
}