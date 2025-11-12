package com.veryshinnam.myapp.feature.attendance.model

import com.veryshinnam.myapp.feature.attendance.data.dto.AttendanceResult
import org.threeten.bp.LocalDate

fun AttendanceResult.toAttendanceData(): AttendanceData =
    AttendanceData(
        stamps = totalStamps,
        isTodayAttendance = todayAttendance,
        attendanceCounts = attendances.size,
        attendanceDates = attendances.map { LocalDate.parse(it) },
        lastExchangeDate = lastExchangeDate?.let { LocalDate.parse(it) }
    )