package com.veryshinnam.myapp.feature.attendance.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.attendance.data.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.YearMonth
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    private val _attendanceUiState = MutableStateFlow<AttendanceUiState>(AttendanceUiState.Idle)
    val attendanceUiState: StateFlow<AttendanceUiState> = _attendanceUiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        // 첫 화면 진입 시 현재 연도, 현재 월 출첵 조회
        val now = YearMonth.now()
        fetchAttendance(yearMonth = now)
    }

    // 출첵 조회
    fun fetchAttendance(yearMonth: YearMonth) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val res = repository.getAttendance(year= yearMonth.year, month = yearMonth.monthValue)
                _attendanceUiState.value = AttendanceUiState.Success(
                    yearMonth = yearMonth,
                    attendanceData = res,
                )
            } catch (e: Exception) {
                _attendanceUiState.value = AttendanceUiState.Error(e.message ?: "Unknown error")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 이전 달 출첵 조회
    fun fetchPreviousMonth() {
        val current = _attendanceUiState.value
        if (current is AttendanceUiState.Success) {
            fetchAttendance(current.yearMonth.minusMonths(1))
        }
    }

    // 다음 달 출첵 조회
    fun fetchNextMonth() {
        val current = _attendanceUiState.value
        if (current is AttendanceUiState.Success) {
            fetchAttendance(current.yearMonth.plusMonths(1))
        }
    }

    // 오늘 출첵 등록
    fun addAttendance() {
        val current = _attendanceUiState.value
        if (current is AttendanceUiState.Success) {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val res = repository.addAttendance()
                    _attendanceUiState.value = AttendanceUiState.Success(
                        yearMonth = current.yearMonth,
                        attendanceData = res,
                    )
                } catch (e: Exception) {
                    _attendanceUiState.value = AttendanceUiState.Error(e.message ?: "Unknown error")
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    // 출첵 보상 교환
    fun exchangeAttendance(onError: (String) -> Unit = {}) {
        val current = _attendanceUiState.value
        if (current is AttendanceUiState.Success) {
            if (current.attendanceData.stamps < 10) {
                // 조건 불충족 시: 에러 상태로 변경 or 무시
                onError("도장이 10개 이상일 때만 교환할 수 있습니다.")
                return
            }

            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val res = repository.exchangeAttendance()
                    _attendanceUiState.value = AttendanceUiState.Success(
                        yearMonth = current.yearMonth,
                        attendanceData = res,
                    )
                } catch (e: Exception) {
                    // 에러 처리
//                    onError(e)
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }
}