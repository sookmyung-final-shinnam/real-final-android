package com.veryshinnam.myapp.feature.creation.componenet.selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R

@Composable
fun SelectionPaletteButton(
    color: Color,
    labelT: String,
    selected: Boolean,
    onClick: () -> Unit,
    spacer: Dp = 2.dp,  // 원 테두리, 원-텍스트 간격
    labelTextStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = Bold),
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                role = Role.RadioButton
                contentDescription = labelT
                stateDescription = if (selected) "선택됨" else "선택 안 됨"
            }
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(spacer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier.weight(1f))  {
            Button(
                onClick = onClick,
                shape = CircleShape,
                border = BorderStroke(spacer, colorResource(R.color.main_orange)),
                colors = ButtonDefaults.buttonColors(containerColor = color),
                modifier = Modifier.aspectRatio(1f)
                    .clearAndSetSemantics { }
            ) {}

            if (selected) {
                Image(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Text(labelT, style = labelTextStyle, modifier = Modifier.clearAndSetSemantics { })
    }
}