package com.veryshinnam.myapp.feature.attendance.data.repository

import com.veryshinnam.myapp.core.network.BaseResponse
import com.veryshinnam.myapp.feature.attendance.data.api.AttendanceApi
import com.veryshinnam.myapp.feature.attendance.data.dto.AttendanceResult
import com.veryshinnam.myapp.feature.attendance.model.AttendanceData
import com.veryshinnam.myapp.feature.attendance.model.toAttendanceData
import javax.inject.Inject

class AttendanceRepositoryImpl@Inject constructor(
    private val attendanceApi: AttendanceApi
): AttendanceRepository {

    // 출석 체크 조회
    override suspend fun getAttendance(year: Int?, month: Int?): AttendanceData {
        val response: BaseResponse<AttendanceResult> = attendanceApi.getAttendance(year, month)

        if (!response.isSuccess || response.result == null) {
            throw Exception("출석 체크 조회 실패: ${response.message}")
        }

        return response.result.toAttendanceData()
    }

    // 오늘의 출석 체크 등록
    override suspend fun addAttendance(): AttendanceData {
        val response: BaseResponse<AttendanceResult> = attendanceApi.addAttendance()

        if (!response.isSuccess || response.result == null) {
            throw Exception("오늘의 출석 체크 등록 실패: ${response.message}")
        }

        return response.result.toAttendanceData()
    }

    // 출석 체크 보상 교환
    override suspend fun exchangeAttendance(): AttendanceData {
        val response: BaseResponse<AttendanceResult> = attendanceApi.exchangeAttendance()

        if (!response.isSuccess || response.result == null) {
            throw Exception("출석 체크 보상 교환 실패: ${response.message}")
        }

        return response.result.toAttendanceData()
    }
}