package com.veryshinnam.myapp.feature.attendance.data.dto

data class AttendanceResult(
    val totalStamps: Int,         // 모은 도장 수
    val todayAttendance: Boolean = false, // 오늘 출석 여부
    val attendances: List<String>,   // 조회 달 출석한 날짜들
    val lastExchangeDate: String? = null   // 마지막 보상
)