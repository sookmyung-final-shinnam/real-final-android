//package com.veryshinnam.myapp.feature.storage.ui.component
//
//import androidx.compose.animation.core.animateDpAsState
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import com.veryshinnam.myapp.feature.storage.enums.Tab
//
//@Composable
//fun TabSwitcher(
//    tabs: List<String> = listOf("캐릭터 보기", "동화 보기"),
//    selectedIndex: Tab,
//    onSelect: (Int) -> Unit
//) {
////    var selectedIndex by remember { mutableStateOf(0) } // 0: 위 버튼, 1: 아래 버튼
////
////    val animationDuration = 300
////
////    val topWidth by animateDpAsState(
////        targetValue = if (selectedIndex == 0) 180.dp else 200.dp, // 선택 여부에 따라 크기 조정
////        animationSpec = tween(durationMillis = animationDuration)
////    )
////    val bottomWidth by animateDpAsState(
////        targetValue = if (selectedIndex == 1) 200.dp else 180.dp,
////        animationSpec = tween(durationMillis = animationDuration)
////    )
////
////    val topOffset by animateDpAsState(
////        targetValue = if (selectedIndex == 0) 0.dp else 20.dp,
////        animationSpec = tween(durationMillis = animationDuration)
////    )
////    val bottomOffset by animateDpAsState(
////        targetValue = if (selectedIndex == 1) 0.dp else (-20).dp,
////        animationSpec = tween(durationMillis = animationDuration)
////    )
////
////    val topHeight by animateDpAsState(
////        targetValue = if (selectedIndex == 0) 90.dp else 100.dp,
////        animationSpec = tween(durationMillis = animationDuration)
////    )
////    val bottomHeight by animateDpAsState(
////        targetValue = if (selectedIndex == 1) 100.dp else 90.dp,
////        animationSpec = tween(durationMillis = animationDuration)
////    )
////
////    Column(
////        verticalArrangement = Arrangement.Center,
////        horizontalAlignment = Alignment.CenterHorizontally,
////        modifier = Modifier.fillMaxSize()
////    ) {
////        Button(
////            onClick = { selectedIndex = 0 },
////            modifier = Modifier
////                .offset(y = topOffset)
////                .width(topWidth)
////                .height(topHeight),
////            shape = RoundedCornerShape(12.dp),
////            colors = ButtonDefaults.buttonColors(
////                containerColor = if (selectedIndex == 0) Color.Blue else Color.Gray
////            )
////        ) {
////            Text("위 버튼", color = Color.White)
////        }
////
////        Spacer(modifier = Modifier.height(16.dp))
////
////        Button(
////            onClick = { selectedIndex = 1 },
////            modifier = Modifier
////                .offset(y = bottomOffset)
////                .width(bottomWidth)
////                .height(bottomHeight),
////            shape = RoundedCornerShape(12.dp),
////            colors = ButtonDefaults.buttonColors(
////                containerColor = if (selectedIndex == 1) Color.Blue else Color.Gray
////            )
////        ) {
////            Text("아래 버튼", color = Color.White)
////        }
//    }
//}