package com.veryshinnam.myapp.feature.creation.select.componenet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.feature.creation.model.Gender

@Composable
fun SelectGenderButton(
    gender: Gender,
    isSelected: Boolean,
    onButtonClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = { onButtonClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) colorResource(R.color.main_orange) else colorResource(R.color.lemon_yellow),
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = CircleShape,
        border =  BorderStroke(2.dp, colorResource(R.color.main_orange)),
        modifier = modifier.aspectRatio(1f) // 1:1 원형
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val selectColor = if (isSelected) Color.White else Color.Black

            if (gender == Gender.FEMALE) {
                Icon(
                    imageVector = Icons.Filled.Female,
                    contentDescription = "여자 버튼",
                    modifier = Modifier.fillMaxSize(0.4f),
                    tint = selectColor
                )
                Text(
                    text = "여자",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = selectColor
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Male,
                    contentDescription = "Male",
                    modifier = Modifier.fillMaxSize(0.4f),
                    tint = selectColor
                )
                Text(
                    text = "남자",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = selectColor
                )
            }
        }

    }
}