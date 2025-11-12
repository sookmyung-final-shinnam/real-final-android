package com.veryshinnam.myapp.feature.admin.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryshinnam.myapp.feature.admin.data.repository.AdminStoryRepository
import com.veryshinnam.myapp.feature.admin.model.AdminStory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminStoryViewModel @Inject constructor(
    private val repository: AdminStoryRepository
) : ViewModel() {

    private val TAG = "AdminStoryVM"

    private val _stories = MutableStateFlow<List<AdminStory>>(emptyList())
    val stories: StateFlow<List<AdminStory>> = _stories

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    fun loadStories() {
        viewModelScope.launch {
            try {
                val result = repository.getIncompleteStories()
                Log.d(TAG, "API Response (loadStories): $result")

                if (result.result != null) {
                    _stories.value = result.result
                } else {
                    Log.w(TAG, "Warning: result.result is null")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading stories", e)
            }
        }
    }

    fun uploadImageLink(id: Long, link: String) {
        viewModelScope.launch {
            try {
                val result = repository.uploadImageYoutubeLink(id, link)
                Log.d(TAG, "API Response (uploadImageLink): $result")

                _toastMessage.value = result.result
                loadStories()
            } catch (e: Exception) {
                Log.e(TAG, "Error uploading image link", e)
            }
        }
    }

    fun uploadVideoLink(id: Long, link: String) {
        viewModelScope.launch {
            try {
                val result = repository.uploadVideoYoutubeLink(id, link)
                Log.d(TAG, "API Response (uploadVideoLink): $result")

                _toastMessage.value = result.result
                loadStories()
            } catch (e: Exception) {
                Log.e(TAG, "Error uploading video link", e)
            }
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }

}