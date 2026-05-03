package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun EmptyView(
    emptyText: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    emptyTextStyle: TextStyle = MaterialTheme.typography.titleSmall,
    spacerPadding: Dp = 10.dp,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InstructionText(
            text = emptyText,
            textStyle = emptyTextStyle,
            modifier = Modifier
        )

        Spacer(Modifier.height(spacerPadding * 2))

        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            shape = CircleShape,
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(R.color.main_orange),
                            colorResource(R.color.dark_orange),
                        )
                    ),
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    color =colorResource(id = R.color.main_orange),
                    shape = CircleShape
                )
        ) {
            Text(
                text = buttonText,
                style = emptyTextStyle.copy(
                    fontSize = emptyTextStyle.fontSize * 1.2f,
                    fontWeight = Bold
                ),
                modifier = Modifier
                    .padding(horizontal = spacerPadding, vertical = spacerPadding / 2)
            )
        }
    }
}