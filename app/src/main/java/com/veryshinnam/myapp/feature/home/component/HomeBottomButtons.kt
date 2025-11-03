package com.veryshinnam.myapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun HomeBottomButtons(
    onDashboardClick: () -> Unit, // 대시보드
    onCreationClick: () -> Unit,  // 캐릭터 생성
    onCollectionClick: () -> Unit,   // 보관함
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.img_bottom_dashboard),
            contentDescription = "대시보드",
            modifier = Modifier
                .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                .clickable(
                    indication = LocalIndication.current,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onDashboardClick() }
        )
        Image(
            painter = painterResource(R.drawable.img_bottom_creation),
            contentDescription = "캐릭터 생성",
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .clickable(
                    indication = LocalIndication.current,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onCreationClick() }
        )
        Image(
            painter = painterResource(R.drawable.img_bottom_collection),
            contentDescription = "보관함",
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .clickable(
                    indication = LocalIndication.current,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onCollectionClick() }
        )
    }
}