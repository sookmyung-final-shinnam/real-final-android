package com.veryshinnam.myapp.feature.attendance.ui

import com.veryshinnam.myapp.feature.attendance.model.AttendanceData
import org.threeten.bp.YearMonth

sealed interface AttendanceUiState {
    data object Idle : AttendanceUiState
    data class Error(val message: String) : AttendanceUiState

    data class Success(
        val yearMonth: YearMonth,
        val attendanceData: AttendanceData,
    ) : AttendanceUiState
}