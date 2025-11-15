package com.veryshinnam.myapp.feature.dashboard.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.dashboard.data.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    var uiState by mutableStateOf(DashboardUiState())
        private set

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            try {
                val response = repository.fetchDashboard()

                if (response.isSuccess && response.result != null) {
                    uiState = DashboardUiState(
                        isLoading = false,
                        data = response.result,
                        errorMessage = null
                    )
                } else {
                    uiState = DashboardUiState(
                        isLoading = false,
                        data = null,
                        errorMessage = response.message ?: "알 수 없는 오류"
                    )
                }
            } catch (e: Exception) {
                uiState = DashboardUiState(
                    isLoading = false,
                    data = null,
                    errorMessage = e.message ?: "네트워크 오류"
                )
            }
        }
    }

}