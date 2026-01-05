package com.veryshinnam.myapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

/**
 * 홈 바텀 버튼
 * : 1. 대시보드, 2. 생성, 3. 보관함 순으로 구성 + 이미지를 버튼으로 사용
 *
 * - onDashboardClick: 버튼 클릭 시, 대시보드 화면으로 이동
 * - onCreationClick: 버튼 클릭 시, 캐릭터 및 동화 생성 플로우 진행
 * - onCollectionClick: 버튼 클릭 시, 보관함 화면으로 이동
 * - onDashboardRect: 대시보드 버튼 위치 정보 저장
 * - onCreationRect: 생성 버튼 위치 정보 저장
 * - onCollectionRect: 보관함 버튼 위치 정보 저장
 */
@Composable
fun HomeBottomButtons(
    onDashboardClick: () -> Unit,
    onCreationClick: () -> Unit,
    onCollectionClick: () -> Unit,
    onDashboardRect: (Rect) -> Unit = {},
    onCreationRect: (Rect) -> Unit = {},
    onCollectionRect: (Rect) -> Unit = {},
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 대시보드
        Image(
            painter = painterResource(R.drawable.img_bottom_dashboard),
            contentDescription = null, // 이미지 설명 제거
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .onGloballyPositioned {
                    onDashboardRect(it.boundsInRoot()) }
                .clickable { onDashboardClick() }
                .semantics {            // 새 대체 텍스트 추가
                    contentDescription = "대시보드"
                    role = Role.Button  // 버튼으로 인식
                }
        )

        // 생성
        Image(
            painter = painterResource(R.drawable.img_bottom_creation),
            contentDescription = null, // 이미지 설명 제거
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .onGloballyPositioned { onCreationRect(it.boundsInRoot()) }
                .clickable { onCreationClick() }
                .semantics {            // 새 대체 텍스트 추가
                    contentDescription = "캐릭터와 동화 생성"
                    role = Role.Button  // 버튼으로 인식
                }
        )

        // 보관함
        Image(
            painter = painterResource(R.drawable.img_bottom_collection),
            contentDescription = null, // 이미지 설명 제거
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .onGloballyPositioned { onCollectionRect(it.boundsInRoot()) }
                .clickable { onCollectionClick() }
                .semantics {            // 새 대체 텍스트 추가
                    contentDescription = "캐릭터와 동화 보관함"
                    role = Role.Button  // 버튼으로 인식
                }
        )
    }
}