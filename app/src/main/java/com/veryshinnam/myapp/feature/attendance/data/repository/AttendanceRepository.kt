package com.veryshinnam.myapp.feature.attendance.data.repository

import com.veryshinnam.myapp.feature.attendance.model.AttendanceData

interface AttendanceRepository {

    // 출석 체크 조회
    suspend fun getAttendance(year: Int? = null, month: Int? = null): AttendanceData

    // 오늘의 출석 체크 등록
    suspend fun addAttendance(): AttendanceData

    // 출석 체크 보상 교환
    suspend fun exchangeAttendance(): AttendanceData
}