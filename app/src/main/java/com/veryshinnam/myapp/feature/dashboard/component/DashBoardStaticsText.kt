package com.veryshinnam.myapp.feature.dashboard.component

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.feature.dashboard.model.StatData

@Composable
fun DashBoardStaticsText(
    stat: StatData,
    @ColorRes colorRes: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = colorResource(id = colorRes),
                        shape = CircleShape
                    )
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = stat.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            text = stat.count.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}