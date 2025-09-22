package com.veryshinnam.myapp.feature.creation.data.repository

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.creation.data.api.ConversationApi
import com.veryshinnam.myapp.feature.creation.data.dto.FeedbackResult
import com.veryshinnam.myapp.feature.creation.data.dto.NextStepResult
import com.veryshinnam.myapp.feature.creation.data.dto.StartRequest
import com.veryshinnam.myapp.feature.creation.data.dto.StartResult
import com.veryshinnam.myapp.feature.creation.data.dto.toFeedbackData
import com.veryshinnam.myapp.feature.creation.model.FeedbackData
import javax.inject.Inject

class ConversationRepositoryImpl @Inject constructor(
    private val api: ConversationApi
) : ConversationRepository {

    // 대화 세션 시작
    override suspend fun startConversation(request: StartRequest): StartResult {
        val response: BaseResponse<StartResult> = api.startConversation(request)

        if (!response.isSuccess || response.result == null) {
            throw Exception("대화 세션 시작 실패: ${response.message}")
        }

        return response.result
    }

    // 다음 스텝에서 메시지 조회
    override suspend fun getNextStep(
        sessionId: Long,
        currentStep: String
    ): NextStepResult {
        val response: BaseResponse<NextStepResult> = api.getNextStep(sessionId,currentStep)

        if (!response.isSuccess || response.result == null) {
            throw Exception("다음 스텝에서 메시지 조회 실패: ${response.message}")
        }

        return response.result
    }

    // 사용자 답변 피드백
    override suspend fun feedbackConversation(
        messageId: Long,
        userAnswer: String
    ): FeedbackData {
        val response: BaseResponse<FeedbackResult> = api.feedbackConversation(messageId, userAnswer)

        if (!response.isSuccess || response.result == null) {
            throw Exception("사용자 답변 피드백 실패: ${response.message}")
        }

        return response.result.toFeedbackData()
    }

    // 동화 생성 완성
    override suspend fun completeConversation(sessionId: Long): Boolean {
        val response: BaseResponse<String> = api.completeConversation(sessionId)

        if (!response.isSuccess || response.result == null) {
            throw Exception("동화 생성 완성 실패: ${response.result}")
        }

        return true
    }
}