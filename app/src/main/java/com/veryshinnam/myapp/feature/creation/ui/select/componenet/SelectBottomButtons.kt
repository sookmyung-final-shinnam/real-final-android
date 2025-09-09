package com.veryshinnam.myapp.feature.creation.ui.select.componenet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

// 있으면 버튼, 없으면 빈공간 차지
@Composable
fun SelectTripleButtons(
    isLeft: Boolean,
    isCenter: Boolean,
    isRight: Boolean,
    onLeftClick: () -> Unit = {},
    onCenterClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isLeft) {
            // 이전 버튼
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = {onLeftClick()}),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "이전",
                    tint = colorResource(R.color.main_orange)
                )
                Spacer(Modifier.width(4.dp))
                Text("이전",
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.main_orange),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold)
                )
            }
        } else {
            Spacer(Modifier.weight(1f))
        }

        if (isCenter) {
            SelectCustomButton(
                onButtonClick = onCenterClick,
                modifier = Modifier.weight(1f)
            )
        } else {
            Spacer(Modifier.weight(1f))
        }

        if (isRight) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = {onRightClick()}),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("다음",
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.main_orange),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold)
                )

                Spacer(Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "다음",
                    tint = colorResource(R.color.main_orange)
                )
            }
        } else {
            Spacer(Modifier.weight(1f))
        }
    }
}