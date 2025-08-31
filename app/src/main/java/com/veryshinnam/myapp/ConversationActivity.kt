package com.veryshinnam.myapp;

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.veryshinnam.myapp.feature.creation.data.dto.StartConversationRequest
import com.veryshinnam.myapp.feature.creation.ui.conversation.ConversationStartScreen
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
class ConversationActivity: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 캐릭터 요청 가져옴
        val req = intent.getParcelableExtra<StartConversationRequest>("request")

        setContent {
            ConversationStartScreen(
                onBack = { finish() }, // ← 뒤로 버튼 Activity 종료
                req = req
            )
        }
    }
}
