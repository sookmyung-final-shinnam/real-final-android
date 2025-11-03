package com.veryshinnam.myapp.feature.attendance.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.Year
import org.threeten.bp.YearMonth
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
//    private val repository: CalendarRepository // API/로컬 데이터
) : ViewModel() {

    private val _attendanceUiState = MutableStateFlow<AttendanceUiState>(AttendanceUiState.Loading)
    val attendanceUiState: StateFlow<AttendanceUiState> = _attendanceUiState.asStateFlow()

    init {
        // 처음 화면 진입 시 현재 달로 로드
        fetchMonth(YearMonth.now())
    }

    fun fetchMonth(month: YearMonth) {
        viewModelScope.launch {
            try {
                // repository.getStampedDates(month) 호출
                val res = getAttendancesDummy(month)
                _attendanceUiState.value = AttendanceUiState.Success(
                    month = month,
                    stamps = res.stamps,
                    attendances = res.attendances,
                    attendanceDates = res.attendanceDates,
                    usedDate = res.usedDate,
                    isTodayAttendance = res.isTodayAttendance
                )
            } catch (e: Exception) {
                _attendanceUiState.value = AttendanceUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchPreviousMonth() {
        val current = _attendanceUiState.value
        if (current is AttendanceUiState.Success) {
            fetchMonth(current.month.minusMonths(1))
        }
    }

    fun fetchNextMonth() {
        val current = _attendanceUiState.value
        if (current is AttendanceUiState.Success) {
            fetchMonth(current.month.plusMonths(1))
        }
    }

    private fun getAttendancesDummy(month: YearMonth): AttendanceUiState.Success {
        val currentYear = Year.now().value
        val attendanceDays = setOf(1, 2, 5, 6, 8, 14)

        val attendanceDates = when {
            month.year == currentYear && month.monthValue == 7 ->
                setOf(1, 2, 3, 4, 5, 9, 21, 22).map { month.atDay(it) }.toSet()

            month.year == currentYear && month.monthValue == 8 ->
                setOf(10, 15, 16, 23, 27).map { month.atDay(it) }.toSet()

            month.year == currentYear && month.monthValue == 9 ->
                setOf(12, 19, 21, 24, 27, 29, 30).map { month.atDay(it) }.toSet()

            month.year == currentYear && month.monthValue == 10 ->
                attendanceDays.map { month.atDay(it) }.toSet()

            else -> emptySet()
        }

        val usedDate = YearMonth.of(2025, 8).atDay(15)
        val stamps = 9

        return AttendanceUiState.Success(
            month = month,
            stamps = stamps,
            attendances = attendanceDates.size,
            attendanceDates = attendanceDates,
            usedDate = usedDate,
            isTodayAttendance = false // 기본값
        )
    }

    fun fetchAttendance() {
        val current = _attendanceUiState.value
        if (current is AttendanceUiState.Success) {
            val today = LocalDate.now()
            val isNew = !current.attendanceDates.contains(today)
            val updatedDates = if (isNew) current.attendanceDates + today else current.attendanceDates

            val newStamps = if (isNew) current.stamps + 1 else current.stamps

            _attendanceUiState.value = current.copy(
                attendanceDates = updatedDates,
                attendances = updatedDates.size,
                stamps = newStamps,
                isTodayAttendance = true,
                rewardDate = if (newStamps >= 10) today else null // rewardDate 지정
            )
        }
    }

    fun fetchAttendanceReward() {
        viewModelScope.launch {
            try {
                val current = _attendanceUiState.value
                if (current is AttendanceUiState.Success && current.stamps >= 10) {
                    val remainder = current.stamps % 10
                    val today = LocalDate.now()

                    _attendanceUiState.value = current.copy(
                        stamps = remainder,
                        usedDate = today //
                    )
                }
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }
}