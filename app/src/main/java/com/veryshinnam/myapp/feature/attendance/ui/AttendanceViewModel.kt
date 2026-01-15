package com.veryshinnam.myapp.feature.attendance.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.common.model.ManualData
import com.veryshinnam.myapp.common.model.ManualTarget
import com.veryshinnam.myapp.core.manual.ManualManager
import com.veryshinnam.myapp.feature.attendance.data.repository.AttendanceRepository
import com.veryshinnam.myapp.feature.attendance.model.AttendanceData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val repository: AttendanceRepository,
    private val manualManager: ManualManager

) : ViewModel() {

    private val _attendanceUiState = MutableStateFlow<AttendanceUiState>(AttendanceUiState.Idle)
    val attendanceUiState: StateFlow<AttendanceUiState> = _attendanceUiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ManualManager 구독
    val manualState = manualManager.state
    val manualMessage = manualManager.message

    // 매뉴얼 진행 단계 상태
    private val _manualStep = MutableStateFlow(0)
    val manualStep = _manualStep.asStateFlow()

    // 첫 화면 진입 시 현재 연도, 현재 월 출첵 조회
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


    // --- 매뉴얼 관련 ---
    // 출석 체크 화면 사용 매뉴얼
    val manuals = listOf(
        ManualData("이제 다시 화면을 세로로 돌려주세요!", ManualTarget.NONE),
        ManualData("출석체크 화면에서는 하루에 1번 출석체크가 가능하고 지금까지 출석 날짜를 볼 수 있어요.", ManualTarget.NONE),
        ManualData("출석 체크를 하면 그 날짜에 도장이 하나씩 찍혀요.", ManualTarget.ITEM),
        ManualData("지금까지 모은 도장 수이고,\n도장 10개를 모으면 도토리 1개로 바꿀 수 있어요!", ManualTarget.ITEM),
        ManualData("도토리는 새로운 동화를 만들고,\n만든 동화를 움직이는 영상으로 만들 때 필요해요.", ManualTarget.NONE),
        ManualData("출석 체크를 하면서 도토리를 차곡차곡 모아 보세요!", ManualTarget.NONE),
    )

    val today: LocalDate = LocalDate.now()
    val manualAttendances = listOf(
        LocalDate.of(today.year, today.month, 1),
        LocalDate.of(today.year, today.month, 2),
        LocalDate.of(today.year, today.month, 5),
        LocalDate.of(today.year, today.month, 9),
        LocalDate.of(today.year, today.month, 13),
        LocalDate.of(today.year, today.month, 14),
        LocalDate.of(today.year, today.month, 17),
        LocalDate.of(today.year, today.month, 27),
    )

    val manualDate: LocalDate = LocalDate.of(today.year, today.month, 5)

    fun startManual() {
        _manualStep.value = 0
        manualManager.update(manuals[0].message)

        val attendanceDummy = AttendanceData(
            stamps = 5,
            isTodayAttendance = true,
            attendanceCounts = 5,
            attendanceDates = manualAttendances,
            lastExchangeDate = manualDate
        )

        _attendanceUiState.value = AttendanceUiState.Success(
            yearMonth = YearMonth.from(today),
            attendanceData = attendanceDummy,
        )
    }

    fun nextManual() {
        val current = _manualStep.value

        if (current < manuals.lastIndex) {
            val next = current + 1
            _manualStep.value = next
            manualManager.update(manuals[next].message)
        } else if (current == manuals.lastIndex) {
            _manualStep.value = manuals.size
        }
    }

    fun stopManual() = manualManager.stop()

    fun clearManual() = manualManager.clear()
}