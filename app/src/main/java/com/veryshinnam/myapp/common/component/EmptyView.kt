package com.veryshinnam.myapp.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    buttonColor: Color = colorResource(R.color.main_orange),
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
        Text(
            text = emptyText,
            style = emptyTextStyle.copy(
                color = buttonColor,
                fontWeight = SemiBold,
                textAlign = Center
            )
        )

        Spacer(Modifier.height(spacerPadding * 2))

        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = Color.White
            ),
            shape = CircleShape,
        ) {
            Text(
                text = buttonText,
                style = emptyTextStyle.copy(
                    fontSize = emptyTextStyle.fontSize * 1.2f,
                    fontWeight = Bold
                ),
                modifier = Modifier.padding(vertical = spacerPadding / 2)
            )
        }
    }
}