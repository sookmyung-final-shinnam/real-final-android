package com.veryshinnam.myapp.feature.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.veryshinnam.myapp.R
import com.veryshinnam.myapp.common.component.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onClickLogout: () -> Unit,
    onClickDelete: () -> Unit
) {
    var showLogoutConfirm by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = colorResource(id = R.color.background_yellow),
        topBar = { AppTopBar() }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 타이틀
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "환경설정",
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "환경설정",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            // 1. 로그아웃 버튼
            Button(
                onClick = { showLogoutConfirm = true },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.main_orange),
                    contentColor = Color.White
                )
            ) { Text("로그아웃") }

            // 2. 회원탈퇴 버튼
            Button(
                onClick = { onClickDelete() },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.main_orange),
                    contentColor = Color.White
                )
            ) { Text("회원탈퇴") }

            // 3. 뒤로 이동
            Button(
                onClick = { onBack() },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.main_orange),
                    contentColor = Color.White
                )
            ) { Text("← 뒤로") }
        }
    }

    // 로그아웃 확인
    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            title = { Text("로그아웃") },
            text = { Text("정말 로그아웃 하시겠어요?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutConfirm = false
                    onClickLogout()
                }) { Text("확인") }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) { Text("취소") }
            }
        )
    }

    // 회원탈퇴 확인
    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("회원탈퇴") },
            text = { Text("정말 탈퇴하시겠어요? 이 작업은 되돌릴 수 없어요.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteConfirm = false
                    onClickDelete()
                }) { Text("탈퇴") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text("취소") }
            }
        )
    }
}