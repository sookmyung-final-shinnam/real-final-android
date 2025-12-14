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
    // 생성 전 선택 화면 사용 매뉴얼
    val manuals = listOf(
        ManualData("동화가 완성되면 여기 보관함에서 확인할 수 있어요.", ManualTarget.NONE),
        ManualData("이건 지금까지 만든 동화 수이자 캐릭터 수에요!", ManualTarget.NONE),
        ManualData("같이 만들었던 동화가 여기 있네요. 해당 캐릭터를 눌러 같이 한번 확인해 볼까요?", ManualTarget.NONE),
    )

    fun startManual() {
        _manualStep.value = 0
        manualManager.update(manuals[0].message)

        val today = LocalDate.now()

        val attendanceDummy = AttendanceData(
            stamps = 5,
            isTodayAttendance = true,
            attendanceCounts = 5,
            attendanceDates = listOf(
                LocalDate.of(today.year, today.month, 1),
                LocalDate.of(today.year, today.month, 2),
                LocalDate.of(today.year, today.month, 5),
                LocalDate.of(today.year, today.month, 9),
                LocalDate.of(today.year, today.month, 13),
                LocalDate.of(today.year, today.month, 14),
                LocalDate.of(today.year, today.month, 17),
                LocalDate.of(today.year, today.month, 27),
            ),
            lastExchangeDate = LocalDate.of(today.year, today.month, 5)
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

    fun hideManual() = manualManager.clear()
}