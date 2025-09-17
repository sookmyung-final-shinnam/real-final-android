package com.veryshinnam.myapp.feature.attendance.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.component.common.StrokeText

@Composable
fun AttendanceStamp(
    onReceiveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)), // 배경색: 검정 알파 50,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.img_stamp_shining_blue),
            contentDescription = "오늘 출석 도장"
        )

        StrokeText(
            text = "출석 완료",
            tColor = Color.White,
            oColor = colorResource(R.color.clear_blue),
            oWidth = 4f,
            fStyle = MaterialTheme.typography.displayLarge,
            fWeight = FontWeight.Bold,
            Modifier.weight(0.2f).align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = onReceiveClick,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.clear_blue),
                contentColor = Color.White
            )
        ) {
            Text(text = "받기")
        }
    }
}