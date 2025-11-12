package com.veryshinnam.myapp.feature.attendance.model

import org.threeten.bp.LocalDate

data class AttendanceData(
    val stamps: Int,         // 모은 도장 수
    val isTodayAttendance: Boolean = false, // 오늘 출석 여부
    val attendanceCounts: Int,              // 조회 달 출석 수
    val attendanceDates: List<LocalDate>,   // 조회 달 출석한 날짜들
    val lastExchangeDate: LocalDate? = null   // 마지막 보상
)