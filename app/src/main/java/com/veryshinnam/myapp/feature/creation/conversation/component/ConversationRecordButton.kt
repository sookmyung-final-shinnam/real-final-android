package com.veryshinnam.myapp.feature.creation.conversation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun ConversationRecordButton(
    onRecordClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = { onRecordClick() },
        modifier = modifier.fillMaxHeight().aspectRatio(1f),
        border = BorderStroke(2.dp, colorResource(R.color.main_orange)),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.lemon_yellow),
        ),
        shape = CircleShape
    ) {
        Image(
            painter = painterResource(R.drawable.img_mike_on),
            contentDescription = "Record",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}