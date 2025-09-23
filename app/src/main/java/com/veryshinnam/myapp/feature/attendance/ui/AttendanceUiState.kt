package com.veryshinnam.myapp.feature.attendance.ui

import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

sealed interface AttendanceUiState {
    data object Loading : AttendanceUiState
    data class Error(val message: String) : AttendanceUiState

    data class Success(
        val month: YearMonth,    // 달
        val stamps: Int,         // 유저 도장 수
        val isTodayAttendance: Boolean = false, // 오늘 출석 여부
        val attendances: Int,         // 이번 달 출석 수
        val attendanceDates: Set<LocalDate>, // 출석한 날짜들
        val usedDate: LocalDate? ,// 사용된 도장 날짜
        val rewardDate: LocalDate? = null // 추가
    ) : AttendanceUiState
}