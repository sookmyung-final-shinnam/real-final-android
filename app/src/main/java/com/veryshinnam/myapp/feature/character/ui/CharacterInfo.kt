package com.veryshinnam.myapp.feature.character.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.veryshinnam.myapp.R

@Composable
fun CharacterInfo(
    name: String,
    gender: String,
    isFavorite: Boolean,
    createdAt: String
) {
    val density = LocalDensity.current
    var infoHeightDp by remember { mutableStateOf(0.dp) }
    val fontSize = with(density) { (infoHeightDp * 0.1f).toSp() }

    val textMeasurer = rememberTextMeasurer()
    val outlineColor = colorResource(id = R.color.main_orange)

     Box(
         modifier = Modifier
             .fillMaxWidth(0.9f),
         contentAlignment = Alignment.BottomCenter
     ) {

         // 정보 액자 이미지
         Image(
             painter = painterResource(R.drawable.img_character_info),
             contentDescription = "정보 액자",
             modifier = Modifier
                 .align(Alignment.BottomCenter)
                 .fillMaxWidth()
                 .aspectRatio(2f)
                 .onGloballyPositioned { it ->
                     // 현재 기기 화면에서의 해당 이미지 객체
                     infoHeightDp = with(density) { it.size.height.toDp() }
                 },
             contentScale = ContentScale.Fit
         )

         // 캐릭터 정보
         Row(
             modifier = Modifier
                 .fillMaxWidth(0.4f)
                 .align(Alignment.BottomCenter)
                 .offset(y = -(infoHeightDp / 4)) // 액자 높이의 절반 위
                 .zIndex(1f),
             verticalAlignment = Alignment.CenterVertically,
             horizontalArrangement = Arrangement.SpaceEvenly
         ) {
             IconButton(
                 onClick = { /* TODO: 즐겨찾기 토글 상태 저장 */ }
             ) {
                 Image(
                     modifier = Modifier.size(infoHeightDp * 0.2f),

                     painter = painterResource(
                         if (isFavorite) R.drawable.img_star_on
                         else R.drawable.img_star_off
                     ),
                     contentDescription = "즐겨찾기",
                     contentScale = ContentScale.Fit
                 )
             }

             Text(
                 text = name,
                 color = Color.White,
                 fontWeight = FontWeight.Bold,
                 fontSize = 30.sp,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis
             )

             Text(
                 text = if (gender == "FEMALE") "여자" else "남자"
             )
         }

         // 생성일 텍스트 (액자 맨 아래)
         Text(
             text = createdAt,
             color = Color.White,
             fontSize = fontSize,
             fontWeight = FontWeight.Bold,
             modifier = Modifier
                 .drawWithContent {

                 // 현재 Text와 동일한 스타일로 측정
                 val layout = textMeasurer.measure(
                     AnnotatedString(createdAt),
                     style = TextStyle(
                         fontSize = fontSize,
                         fontWeight = FontWeight.Bold
                     )
                 )

                 // 윤곽선 그린 후
                 drawText(
                     textLayoutResult = layout,
                     color = outlineColor,
                     drawStyle = Stroke(width = 10f) // 두께
                 )
                 // 윤곽선 안을 채움
                 drawContent()
             }
         )
     }
}