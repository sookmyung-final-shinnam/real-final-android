package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.veryshinnam.myapp.R

@Composable
fun BackButton(
    modifier: Modifier,
    onBackClick: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    // 뒤로 가기 버튼
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .semantics {
                    contentDescription = "이전 화면으로 돌아가기"
                    role = Role.Button
                }
                .zIndex(20f)
                .clickable(onClick = onBackClick)
                .padding(8.dp)
                .onSizeChanged { size = it },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = null,
                tint = colorResource(R.color.main_orange)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "뒤로",
                style = textStyle.copy(
                    color = colorResource(id = R.color.main_orange),
                    fontWeight = SemiBold
                ),
                modifier = Modifier.clearAndSetSemantics {}
            )
        }

        if (size != IntSize.Zero) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                colorResource(R.color.background_yellow).copy(0.4f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}